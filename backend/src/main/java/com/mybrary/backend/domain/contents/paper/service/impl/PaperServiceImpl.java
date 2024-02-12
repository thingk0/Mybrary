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
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;
    private final ScrapRepository scrapRepository;
    private final BookRepository bookRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Transactional
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

    @Transactional
    @Override
    public ToggleLikeResult toggleLike(Long memberId, Long paperId) {
        /* like 엔티티에 가서 memberId와 paperId에 해당하는 정보가 있는지 확인,
         * 좋아요 정보가 있다면 like isDeleted true 설정, 정보가 없다면 like 생성
         *  */
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        Paper paper = paperRepository.findById(paperId)
                                     .orElseThrow(() -> new EntityNotFoundException("Paper not found with id: " + paperId));

        Optional<Like> isLikePresent = likeRepository.isLikedPaper(memberId, paperId);

        if (isLikePresent.isPresent()) {
            /* 좋아요 정보가 있다면 삭제 */
            likeRepository.delete(isLikePresent.get());
            return new ToggleLikeResult("false", paperId); // 좋아요 취소 결과 반환
        } else {
            /* 좋아요 정보가 없다면 생성 */
            Like newLike = Like.builder()
                               .paper(paper)
                               .member(member)
                               .build();
            likeRepository.save(newLike);
            return new ToggleLikeResult("true", paperId); // 좋아요 결과 반환
        }
    }
}