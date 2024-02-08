package com.mybrary.backend.domain.contents.thread.service.impl;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.like.service.LikeService;
import com.mybrary.backend.domain.contents.paper.dto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperGetDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperUpdateDto;
import com.mybrary.backend.domain.contents.paper.dto.PostPaperDto;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.contents.paper_image.entity.PaperImage;
import com.mybrary.backend.domain.contents.paper_image.repository.PaperImageRepository;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.ScrapRepository;
import com.mybrary.backend.domain.contents.tag.entity.Tag;
import com.mybrary.backend.domain.contents.tag.repository.TagRepository;
import com.mybrary.backend.domain.contents.tag.service.TagService;
import com.mybrary.backend.domain.contents.thread.responseDto.DeleteThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadGetDto;
import com.mybrary.backend.domain.contents.thread.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.requestDto.ThreadUpdateDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.contents.thread.service.ThreadService;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.image.service.ImageService;
import com.mybrary.backend.domain.member.dto.MemberGetDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

      private final ThreadRepository threadRepository;
      private final ImageRepository imageRepository;
      private final ImageService imageService;
      private final PaperRepository paperRepository;
      private final PaperImageRepository paperImageRepository;
      private final MybraryRepository mybraryRepository;
      private final TagService tagService;
      private final TagRepository tagRepository;
      private final MemberRepository memberRepository;
      private final BookRepository bookRepository;
      private final ScrapRepository scrapRepository;
      private final LikeService likeService;
      private final NotificationService notificationService;

      /* 예외 처리 상황별로 추후 추가할예정 */

    @Transactional
    @Override
    public Long createThread(Long myId, ThreadPostDto threadPostDto)
        throws IOException {
        Thread thread = Thread.builder()
                              .mybrary(mybraryRepository.findByMemberId(myId))
                              .build();
        threadRepository.save(thread);

            /* paper 객체 하나씩 생성하고 저장 */
            List<PostPaperDto> postPaperDtoList = threadPostDto.getPostPaperDto();

            int paperSeq = 1;   //페이퍼이미지 순서
            int imageSeq = 0;   //이미지리스트에서 얻어올 순서

<<<<<<< PATCH SET (240c18 FIX: (merge 2) getter, thread 관련 문제 수정, thread dto 분리)
        Member member = memberRepository.findById(myId)
                                        .orElseThrow(NullPointerException::new);
=======
            Member member = memberRepository.findById(threadPostDto.getMemberId())
                                            .orElseThrow(NullPointerException::new);
>>>>>>> BASE      (d980ce FIX: (merged) getter, thread 관련 문제 수정, thread dto 분리)

            for (PostPaperDto dto : postPaperDtoList) {
                  /* paper 객체 생성 */
                  Paper paper = Paper.builder()
                                     .member(member)
                                     .thread(thread)
                                     .layoutType(dto.getLayoutType())
                                     .content1(dto.getContent1())
                                     .content2(dto.getContent2())
                                     .isPaperPublic(dto.isPaperPublic())
                                     .isScrapEnabled(dto.isScrapEnable())
                                     .build();
                  paperRepository.save(paper);

                  /* bookid가 있는경우에만 scrap 생성 */
                  Long bookId = dto.getBookId();
                  if (bookId != null) {
                        Book book = bookRepository.findById(dto.getBookId())
                                                  .orElseThrow(NullPointerException::new);
                        Scrap scrap = Scrap.builder()
                                           .paper(paper)
                                           .book(book)
                                           .paperSeq(paperSeq++)
                                           .build();
                        scrapRepository.save(scrap);
                  }

                  /* tag 목록 생성 */
                  List<String> tagNameList = dto.getTagList();
                  List<Tag> tagEntityList = new ArrayList<>();
                  for (String tagNames : tagNameList) {
                        /* paperId, tag명 */
                        Tag tag = Tag.builder()
                                     .tagName(tagNames)
                                     .paper(paper)
                                     .build();
                        tagEntityList.add(tag);
                  }
                  tagRepository.saveAll(tagEntityList);

                  /* 여기서 페이퍼에 대한 멘션 알림 보내는 로직 */
                  /* 쓰레드를 생성한 멤버가 sender, 멘션된 회원이 receiver, 알람타입은  */
//            List<Long> mentionedIdList = dto.getMentionList();
//            for(Long mentiondedId : mentionedIdList) {
//                NotificationPostDto mentionNotificationPostDto =
//                    NotificationPostDto.builder()
//                    .notifyType(2)
//                    .senderId(member.getId())
//                    .receiverId(mentiondedId)
//                    .build();
//                notificationService.saveNotification(mentionNotificationPostDto);
//            }

                  /* image 객체 두장 생성, paperImage 객체도 생성 */
//            Long image1 = imageService.uploadImage(fileList.get(imageSeq));
//            Long image2 = imageService.uploadImage(fileList.get(imageSeq + 1));

                  Long imageId1 = imageService.uploadImage();
                  Long imageId2 = imageService.uploadImage();

                  /* paperImage 객체 생성 */
                  Image image1 = imageRepository.findById(imageId1)
                                                .orElseThrow(NullPointerException::new);
                  Image image2 = imageRepository.findById(imageId2)
                                                .orElseThrow(NullPointerException::new);

                  PaperImage paperImage1 = PaperImage.builder()
                                                     .paper(paper)
                                                     .image(image1)
                                                     .imageSeq(imageSeq)
                                                     .build();

                  PaperImage paperImage2 = PaperImage.builder()
                                                     .paper(paper)
                                                     .image(image2)
                                                     .imageSeq(imageSeq + 1)
                                                     .build();

                  paperImageRepository.save(paperImage1);
                  paperImageRepository.save(paperImage2);

                  imageSeq = imageSeq + 2;
            }

            return thread.getId();
      }

<<<<<<< PATCH SET (240c18 FIX: (merge 2) getter, thread 관련 문제 수정, thread dto 분리)
    /* 메인 피드 thread 조회하기 */
    @Transactional
    @Override
    public List<GetThreadDto> getMainAllThread(Long myId, Pageable pageable) {
        /* following중인 멤버(본인 포함) 의 쓰레드 최대 5개와 관련된 정보 dto 생성 */
        List<GetThreadDto> threadDtoList = threadRepository
            .getFollowingThreadDtoResults(myId, pageable);
        /* following중이지 않은 멤버의 쓰레드 최대 10개 조회와 관련 정보 dto 생성*/
        int getRandomCount = 10 - threadDtoList.size();
        threadDtoList.addAll(threadRepository.getRandomThreadDtoResults(myId, pageable, getRandomCount));
        /* list 내에서 무작위로 순서 배정 */
        Collections.shuffle(threadDtoList);

        /* followingThreadDtos의 각 threadId에 해당하는 paper관련 정보 조회 */
        for (GetThreadDto threadDto : threadDtoList) {
            /* threadId에 해당하는 paper 관련 정보 dto 목록 조회 */
            List<GetFollowingPaperDto> getFollowingPaperDtoList =
                paperRepository.getFollowingPaperDtoResults(threadDto.getThreadId());
            /* 페이퍼 관련정보 처리 로직 */
            for (GetFollowingPaperDto paperDto : getFollowingPaperDtoList) {
                /* 좋아요 여부 판단 */
                boolean isLiked = likeService.checkIsLiked(paperDto.getId(), myId);
                paperDto.setLiked(isLiked);
                /* 태그목록 포함 처리 */
                paperDto.setTagList(tagService.getTagNameList(paperDto.getId()));
                /* 이미지 url들 포함 처리 */
                List<String> imageUrls = imageRepository.findByPaperId(paperDto.getId());
                paperDto.setImageUrl1(imageUrls.get(0));
                paperDto.setImageUrl1(imageUrls.get(1));
=======
      /* 메인 피드 thread 조회하기 */
      @Transactional
      @Override
      public List<GetThreadDto> getMainAllThread(Long memberId, Pageable pageable) {

            /* following중인 멤버(본인 포함) 의 쓰레드 최대 5개와 관련된 정보 dto 생성 */
            List<GetThreadDto> threadDtoList = threadRepository
                .getFollowingThreadDtoResults(memberId, pageable);

            /* following중이지 않은 멤버의 쓰레드 최대 10개 조회와 관련 정보 dto 생성*/
            int getRandomCount = 10 - threadDtoList.size();
            threadDtoList.addAll(threadRepository.getRandomThreadDtoResults(memberId, pageable, getRandomCount));
            /* list 내에서 무작위로 순서 배정 */
            Collections.shuffle(threadDtoList);

            /* followingThreadDtos의 각 threadId에 해당하는 paper관련 정보 조회 */
            for (GetThreadDto threadDto : threadDtoList) {
                  /* threadId에 해당하는 paper 관련 정보 dto 목록 조회 */
                  List<GetFollowingPaperDto> getFollowingPaperDtoList =
                      paperRepository.getFollowingPaperDtoResults(threadDto.getThreadId());
                  /* 페이퍼 관련정보 처리 로직 */
                  for (GetFollowingPaperDto paperDto : getFollowingPaperDtoList) {
                        /* 좋아요 여부 판단 */
                        boolean isLiked = likeService.checkIsLiked(paperDto.getId(), memberId);
                        paperDto.setLiked(isLiked);

                        /* 태그목록 포함 처리 */
                        paperDto.setTagList(tagService.getTagNameList(paperDto.getId()));
                        /* 이미지 url들 포함 처리 */
                        List<String> imageUrls = imageRepository.findByPaperId(paperDto.getId());
                        paperDto.setImageUrl1(imageUrls.get(0));
                        paperDto.setImageUrl1(imageUrls.get(1));
                  }

>>>>>>> BASE      (d980ce FIX: (merged) getter, thread 관련 문제 수정, thread dto 분리)
            }

            return threadDtoList;
      }


    /* 나의 thread들만 조회하기 */
    @Transactional
    @Override
    public List<ThreadInfoGetDto> getMyAllThread(Long myId, Pageable pageable) {
        /* 나의 thread 정보들 가져와 dto 생성 */

        Member member = memberRepository.findById(myId).orElseThrow(NullPointerException::new);

        return threadRepository.getSimpleThreadDtoResults(myId, pageable);
    }


      /* 특정 member의 모든 thread들만 조회하기 */
      @Transactional
      @Override
      public List<ThreadInfoGetDto> getOtherAllThread(Long memberId, Pageable pageable) {

            Member member = memberRepository.findById(memberId)
                                            .orElseThrow(NullPointerException::new);

            return threadRepository.getSimpleThreadDtoResults(memberId, pageable);
      }

    @Transactional
    @Override
    public ThreadGetDto getThread(Long memberId, Long threadId) {

        ThreadGetDto threadGetDto = new ThreadGetDto();

        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        MemberInfoDto memberInfoDto = memberRepository.getMemberInfo(memberId);

        /* isLiked 따로 처리로 */
        List<PaperGetDto> paperGetDtoList = paperRepository.getPaperGetDto(threadId);

        for (PaperGetDto paperGetDto : paperGetDtoList) {
            paperGetDto.setLiked(likeService.checkIsLiked(paperGetDto.getPaperId(), memberId));
            List<Tag> tagList = tagRepository.getTagsByPaperId(paperGetDto.getPaperId()).orElse(Collections.emptyList());
            List<String> tagNameList = tagList.stream() // tagList를 스트림으로 변환
                                              .map(Tag::getTagName) // 각 Tag 객체에서 getName 메소드를 호출하여 이름을 추출
                                              .toList(); // 결과를 새로운 List<String>
            paperGetDto.setTagList(tagNameList);
        }

        threadGetDto.setThreadId(threadId);
        threadGetDto.setMember(memberInfoDto);
        threadGetDto.setPaperList(paperGetDtoList);

        return threadGetDto;
    }



<<<<<<< PATCH SET (240c18 FIX: (merge 2) getter, thread 관련 문제 수정, thread dto 분리)
@Transactional
@Override
public Long updateThread(Long myId, ThreadUpdateDto threadUpdateDto) {
    Thread thread = threadRepository.findById(threadUpdateDto.getThreadId())
                                    .orElseThrow(NullPointerException::new);
    Member member = memberRepository.findById(myId)
                                    .orElseThrow(EmailNotFoundException::new);
    List<PaperUpdateDto> paperUpdateDtoList = threadUpdateDto.getPaperList();
    for (PaperUpdateDto paperDto : paperUpdateDtoList) {
        Paper paper = paperRepository.findById(paperDto.getPaperId())
                                     .orElseThrow(NullPointerException::new);
        paper.updateLayoutType(paperDto.getLayoutType());
        paper.updateContent1(paperDto.getContent1());
        paper.updateContent2(paperDto.getContent2());
        paper.updatePaperPublic(threadUpdateDto.isPaperPublic());
        paper.updateScrapEnabled(threadUpdateDto.isScrapEnable());
        List<String> tagNameList = paperDto.getTagList();

        /* 기존 태그들 삭제 */
        tagRepository.deleteAllByPaperId(paper.getId());

        List<Tag> tagEntityList = new ArrayList<>();
        for (String tagNames : tagNameList) {
            /* paperId, tag명 */
            Tag tag = Tag.builder()
                         .tagName(tagNames)
                         .paper(paper)
                         .build();
            tagEntityList.add(tag);
        }
        tagRepository.saveAll(tagEntityList);
    }
    return thread.getId();
}
=======
      @Transactional
      @Override
      public Long updateThread(ThreadUpdateDto threadUpdateDto) {
>>>>>>> BASE      (d980ce FIX: (merged) getter, thread 관련 문제 수정, thread dto 분리)

<<<<<<< PATCH SET (c90827 FEAT: threadImpl 충돌 해결 2)
    //test
    // test!!!!!!

=======
<<<<<<< PATCH SET (240c18 FIX: (merge 2) getter, thread 관련 문제 수정, thread dto 분리)
>>>>>>> BASE      (db9759 FIX: (merge 2) getter, thread 관련 문제 수정, thread dto 분리)
    @Transactional
    @Override
    public DeleteThreadDto deleteThread(Long myId, Long threadId) {
        /* 삭제된 페이퍼 개수와 threadId 반환 */
        DeleteThreadDto deleteThreadDto = new DeleteThreadDto();
        deleteThreadDto.setThreadId(threadId);
        deleteThreadDto.setPaperCount(paperRepository.deletePaperByThreadsId(threadId));
        return deleteThreadDto;
    }
}
=======
            Thread thread = threadRepository.findById(threadUpdateDto.getThreadId())
                                            .orElseThrow(NullPointerException::new);
            Member member = memberRepository.findById(threadUpdateDto.getMemberId())
                                            .orElseThrow(EmailNotFoundException::new);

            List<PaperUpdateDto> paperUpdateDtoList = threadUpdateDto.getPaperList();

            for (PaperUpdateDto paperDto : paperUpdateDtoList) {
                  Paper paper = paperRepository.findById(paperDto.getPaperId())
                                               .orElseThrow(NullPointerException::new);

                  paper.updateLayoutType(paperDto.getLayoutType());
                  paper.updateContent1(paperDto.getContent1());
                  paper.updateContent2(paperDto.getContent2());
                  paper.updatePaperPublic(threadUpdateDto.isPaperPublic());
                  paper.updateScrapEnabled(threadUpdateDto.isScrapEnable());

                  List<String> tagNameList = paperDto.getTagList();

                  /* 기존 태그들 삭제 */
                  tagRepository.deleteAllByPaperId(paper.getId());

                  List<Tag> tagEntityList = new ArrayList<>();
                  for (String tagNames : tagNameList) {
                        /* paperId, tag명 */
                        Tag tag = Tag.builder()
                                     .tagName(tagNames)
                                     .paper(paper)
                                     .build();

                        tagEntityList.add(tag);
                  }
                  tagRepository.saveAll(tagEntityList);

            }
            return thread.getId();
      }

      @Transactional
      @Override
      public DeleteThreadDto deleteThread(Long threadId) {
            /* 삭제된 페이퍼 개수와 threadId 반환 */
            DeleteThreadDto deleteThreadDto = new DeleteThreadDto();
            deleteThreadDto.setThreadId(threadId);
            deleteThreadDto.setPaperCount(paperRepository.deletePaperByThreadsId(threadId));
            return deleteThreadDto;
      }

>>>>>>> BASE      (d980ce FIX: (merged) getter, thread 관련 문제 수정, thread dto 분리)


