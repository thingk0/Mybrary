package com.mybrary.backend.domain.contents.paper.service.impl;

import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.like.repository.LikeRepository;
import com.mybrary.backend.domain.contents.paper.service.PaperService;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.paper.dto.PaperScrapDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperShareDto;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.contents.paper.service.PaperService;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.ScrapRepository;
import java.util.ArrayList;
import java.util.List;
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
    public boolean toggleLike(Long memberId, Long paperId) {
        /* like 엔티티에 가서 memberId와 paperId에 해당하는 정보가 있는지 확인,
         * 좋아요 정보가 있다면 like isDeleted true 설정, 정보가 없다면 like 생성
         * true / false 반환
         *  */

//        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
//        Paper paper = paperRepository.findById(paperId);
        boolean isLiked = false;
//        Like like = likeRepository.isLikedPaper(memberId, paperId)
//            .orElse(
//                Like like = Like.builder()
//                    .member()
//                    .paper()
//                    .build();
//
//                likeRepository.save()
//            );


        return isLiked;
    }
}