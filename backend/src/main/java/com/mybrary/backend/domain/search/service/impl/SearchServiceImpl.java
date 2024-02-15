package com.mybrary.backend.domain.search.service.impl;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.elastic.indices.PaperDocument;
import com.mybrary.backend.domain.elastic.indices.SearchTermDocument;
import com.mybrary.backend.domain.elastic.repository.SearchTermDocumentRepository;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.search.dto.SearchPaperResponseDto;
import com.mybrary.backend.domain.search.service.SearchService;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchTermDocumentRepository searchTermDocumentRepository;
    private final ElasticsearchOperations operations;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final PaperRepository paperRepository;

    @Override
    public List<String> getRealTimeSuggestedSearchTerms(String keyword) {
        try {
            // 키워드 기반 검색어 조회 쿼리 생성
            Query query = Query.of(qb -> qb
                .bool(bq -> bq
                    .should(sh -> sh.matchPhrasePrefix(mpq -> mpq
                        .field("searchTerm.autocomplete")
                        .query(keyword)))
                )
            );

            NativeQuery nativeQuery = NativeQuery.builder()
                                                 .withQuery(query)
                                                 .build();

            SearchHits<SearchTermDocument> searchHits = operations.search(nativeQuery, SearchTermDocument.class);

            // 조회된 검색어 추출 및 반환
            return searchHits.getSearchHits().stream()
                             .map(hit -> hit.getContent().getSearchTerm())
                             .distinct()
                             .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error retrieving real-time suggested search terms for keyword: {}", keyword, e);
            return Collections.emptyList();
        }
    }


    @Override
    public List<PaperDocument> getPaperDocumentListByThreadId(Long threadId) {
        SearchHits<PaperDocument> searchHits =
            operations.search(NativeQuery.builder()
                                         .withQuery(Query.of(qb -> qb
                                             .match(mt -> mt
                                                 .field("threadId").query(threadId))))
                                         .build(), PaperDocument.class);

        return searchHits.getSearchHits().stream()
                         .map(hit -> hit.getContent())
                         .collect(Collectors.toList());
    }

    @Async
    public CompletableFuture<Void> asyncSaveSearchTermDocument(String email, String keyword) {
        searchTermDocumentRepository.save(SearchTermDocument.of(email, keyword));
        return CompletableFuture.completedFuture(null);
    }


    @Override
    public Page<SearchPaperResponseDto> searchThread(String email, String keyword, Pageable pageable) {
        asyncSaveSearchTermDocument(email, keyword);
        Query query = Query.of(qb -> qb
            .bool(bq -> bq
                .should(sh -> sh.match(mq -> mq.field("tagList").query(keyword).boost(3.0F)))
                .should(sh -> sh.match(mq -> mq.field("content1").query(keyword).boost(2.0F)))
                .should(sh -> sh.match(mq -> mq.field("content2").query(keyword).boost(1.0F)))
            )
        );

        SearchHits<PaperDocument> searchHits = operations.search(NativeQuery.builder()
                                                                            .withQuery(query)
                                                                            .withPageable(pageable)
                                                                            .build(), PaperDocument.class);

        List<Long> paperIds = searchHits.getSearchHits().stream()
                                        .map(hit -> hit.getContent().getId())
                                        .collect(Collectors.toList());

        return paperRepository.fetchPaperSearchList(paperIds, pageable);
    }

    @Override
    public List<BookGetDto> searchBook(String email, String keyword, Pageable page) {

        Member me = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);

        List<BookGetDto> bookList = bookRepository.searchBookByKeyword(me.getId(), keyword, page).orElseThrow(
            BookNotFoundException::new);

        return bookList;
    }

    @Override
    public List<MemberGetDto> searchAccount(String email, String keyword, Pageable page) {

        Member me = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<MemberGetDto> accountList = new ArrayList<>();

        if (isKorean(keyword)) {
            System.out.println("한글입니다.");
            keyword = extractCompletedKorean(keyword);
            accountList = memberRepository.searchAcoountByKo(me.getId(), keyword, page).orElseThrow(MemberNotFoundException::new);
            for (int i = 0; i < accountList.size(); i++) {
                MemberGetDto member = accountList.get(i);

                if (memberRepository.isFollowed(me.getId(), member.getMemberId()).orElse(null) != null) {
                    accountList.get(i).setFollowStatus(3);
                } else if (memberRepository.isRequested(me.getId(), member.getMemberId()).orElse(null) != null) {
                    accountList.get(i).setFollowStatus(2);
                } else {
                    accountList.get(i).setFollowStatus(1);
                }
            }
        } else if (isEnglish(keyword)) {
            System.out.println("영어입니다.");
            accountList = memberRepository.searchAcoountByEn(me.getId(), keyword, page).orElseThrow(MemberNotFoundException::new);
            for (int i = 0; i < accountList.size(); i++) {
                MemberGetDto member = accountList.get(i);

                if (memberRepository.isFollowed(me.getId(), member.getMemberId()).orElse(null) != null) {
                    accountList.get(i).setFollowStatus(3);
                } else if (memberRepository.isRequested(me.getId(), member.getMemberId()).orElse(null) != null) {
                    accountList.get(i).setFollowStatus(2);
                } else {
                    accountList.get(i).setFollowStatus(1);
                }
            }
        } else {
            System.out.println("한글도 영어도 아닙니다.");
        }

        return accountList;
    }

    @Override
    public List<String> getRecentSearchTerms() {
        SearchHits<SearchTermDocument> searchHits =
            operations.search(NativeQuery.builder()
                                         .withQuery(q -> q.matchAll(
                                             MatchAllQuery.of(mof -> mof.queryName("recentSearchTerms"))))
                                         .withSort(s -> s
                                             .field(f -> f
                                                 .field("timestamp")
                                                 .order(SortOrder.Desc)
                                             ))
                                         .withPageable(PageRequest.of(0, 30))
                                         .build(), SearchTermDocument.class);
        return searchHits.getSearchHits().stream()
                         .map(hit -> hit.getContent().getSearchTerm())
                         .collect(Collectors.toList());
    }


    // 문자열이 한글인지 확인하는 메서드
    public static boolean isKorean(String str) {
        for (char ch : str.toCharArray()) {
            if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_SYLLABLES
                || Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_JAMO
                || Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO) {
                return true;
            }
        }
        return false;
    }

    // 문자열이 영어인지 확인하는 메서드
    public static boolean isEnglish(String str) {
        return str.matches("[a-zA-Z]+");
    }

    // 완성되지 않은 한글을 뺀 문자열 추출
    public static String extractCompletedKorean(String text) {
        StringBuilder completedKoreanBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            // 완성된 한글 유니코드 범위: 0xAC00 ~ 0xD7A3
            if (c >= 0xAC00 && c <= 0xD7A3) {
                completedKoreanBuilder.append(c);
            }
        }
        return completedKoreanBuilder.toString();
    }
}
