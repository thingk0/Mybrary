package com.mybrary.backend.domain.search.service.impl;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadSearchGetDto;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.search.service.SearchService;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ElasticsearchOperations elasticsearchOperations;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ThreadRepository threadRepository;

    @Override
    public List<String> listSuggestedTerms(String keyword) {
        return null;
    }

    @Override
    public List<ThreadSearchGetDto> searchThread(String email, String keyword, Pageable page) {
        Member me = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
        // 1. keyword가 포함되어있는 태그 리스트 조회 (페이퍼Id가 중복되면 하나만 가져오기)
        Optional<List<ThreadSearchGetDto>> threadList = threadRepository.searchThreadByKeyword(me.getId(), keyword, page);
        // 2. 태그가 포함된 페이퍼가 포함되어있는 스레드 리스트 조회 (스레드Id가 중복되면 하나만 가져오기)
        //    근데 이때 (좋아요수+스크랩수*5)가 제일 큰 페이퍼Id를 갖고 있어야함

        // 3. (좋아요수+스크랩수*5) 순으로 정렬해서 페이징

        return null;
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
