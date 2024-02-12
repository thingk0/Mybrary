package com.mybrary.backend.domain.contents.paper.service.impl;

import com.mybrary.backend.domain.book.entity.Book;
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
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
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
    private final NotificationService notificationService;

    @Transactional
    @Override
    public Long scrapPaper(Long myId, PaperScrapDto paperScrapDto) {
        /* 스크랩한 페이퍼는 scrap entity만 생성 */
        List<Long> paperIdList = paperScrapDto.getPaperIdList();

        Long bookId = paperScrapDto.getBookId();
        int scrapSeq = scrapRepository.findLastPaperSeq(bookId).orElseThrow(BookNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        List<Paper> paperList = paperRepository.findAllById(paperIdList);

        List<Scrap> scrapList = new ArrayList<>();
        for (Paper paper : paperList) {
            Scrap scrap = Scrap.builder()
                               .paper(paper)
                               .book(book)
                               .paperSeq(++scrapSeq)
                               .build();
            scrapList.add(scrap);
        }
        scrapRepository.saveAll(scrapList);

        /* 알림 처리 로직 */

        Thread thread = scrapList.get(0).getPaper().getThread();
        // 쓰레드(페이퍼) 작성자 id
        Long writerId = scrapList.get(0).getPaper().getMember().getId();

        // 스크랩한 나를 sender, 쓰레드(페이퍼) 작성자를 receiver 로 하는 type10 알림 보내기
        NotificationPostDto notification = NotificationPostDto.builder()
                                                               .senderId(myId)
                                                               .receiverId(writerId)
                                                               .notifyType(10)
                                                               .threadId(thread.getId())
                                                               .build();
        notificationService.saveNotification(notification);

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

            /* 좋아요 알람 보내는 로직 */

            NotificationPostDto likeNotificationPostDto =
                NotificationPostDto.builder()
                                   .notifyType(12)
                                   .senderId(memberId)
                                   .receiverId(paper.getMember().getId())
                                   .threadId(paper.getThread().getId())
                                   .paperId(paper.getId())
                                   .build();
            notificationService.saveNotification(likeNotificationPostDto);

            return new ToggleLikeResult("true", paperId); // 좋아요 결과 반환
        }
    }
}