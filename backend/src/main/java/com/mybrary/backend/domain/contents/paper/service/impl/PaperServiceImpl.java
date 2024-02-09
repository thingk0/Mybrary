package com.mybrary.backend.domain.contents.paper.service.impl;

import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.like.repository.LikeRepository;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.ToggleLikeResult;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.contents.paper.service.PaperService;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.ScrapRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;
    private final ScrapRepository scrapRepository;
    private final BookRepository bookRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long scrapPaper(PaperScrapDto paperScrapDto) {
        /* 스크랩한 페이퍼는 scrap entity만 생성 */
        List<Long> paperIdList = paperScrapDto.getPaperIdList();
        List<Scrap> scrapList = new ArrayList<>();
        int scrapSeq = 1;
        for (Long paperId : paperIdList) {
            Scrap scrap = Scrap.builder()
                               .paper(paperRepository.getById(paperId))
                               .book(bookRepository.getById(paperScrapDto.getBookId()))
                               .paperSeq(scrapSeq++)
                               .build();
            scrapList.add(scrap);
        }
        scrapRepository.saveAll(scrapList);
        return paperScrapDto.getBookId();
    }

    /* 이건 채팅쪽이라 일단 냅두겠습니다.. */
    @Override
    public Long sharePaper(PaperShareDto share) {
        return null;
    }

    @Override
    public ToggleLikeResult toggleLike(Long memberId, Long paperId) {
        /* like 엔티티에 가서 memberId와 paperId에 해당하는 정보가 있는지 확인,
         * 좋아요 정보가 있다면 like isDeleted true 설정, 정보가 없다면 like 생성
         *  */

        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        Paper paper = paperRepository.findById(paperId).orElseThrow(NullPointerException::new);
        AtomicReference<ToggleLikeResult> toggleLikeResult =  new AtomicReference<>();

        likeRepository.isLikedPaper(memberId, paperId)
                      .ifPresentOrElse(like -> {
                          // 좋아요 정보가 있다면 삭제
                          likeRepository.deleteById(like.getId());
                          toggleLikeResult.set(ToggleLikeResult.builder()
                                                             .likeResult("false")
                                                             .paperId(paperId)
                                                             .build());
                      }, () -> {
                          // 좋아요 정보가 없다면 생성
                          Like like = Like.builder()
                                          .member(member)
                                          .paper(paper)
                                          .build();
                          likeRepository.save(like);
                          toggleLikeResult.set(ToggleLikeResult.builder()
                                                               .likeResult("true")
                                                               .paperId(paperId)
                                                               .build());
                      });

        return toggleLikeResult.get();
    }
}