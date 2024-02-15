package com.mybrary.backend.domain.contents.thread.service.impl;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.comment.repository.CommentRepository;
import com.mybrary.backend.domain.contents.like.repository.LikeRepository;
import com.mybrary.backend.domain.contents.like.service.LikeService;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperUpdateDto;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PostPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.GetFollowingPaperDto;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.MentionListDto;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.contents.paper_image.entity.PaperImage;
import com.mybrary.backend.domain.contents.paper_image.repository.PaperImageRepository;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.ScrapRepository;
import com.mybrary.backend.domain.contents.tag.entity.Tag;
import com.mybrary.backend.domain.contents.tag.repository.TagRepository;
import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadUpdateDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.GetThreadDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadInfoGetDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.contents.thread.service.ThreadService;
import com.mybrary.backend.domain.elastic.indices.PaperDocument;
import com.mybrary.backend.domain.elastic.repository.PaperDocumentRepository;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.domain.search.service.SearchService;
import com.mybrary.backend.global.exception.image.ImageNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.exception.mybrary.MybraryNotFoundException;
import com.mybrary.backend.global.exception.paper.PaperListNotFoundException;
import com.mybrary.backend.global.exception.thread.MainThreadListNotFoundException;
import com.mybrary.backend.global.exception.thread.ThreadAccessDeniedException;
import com.mybrary.backend.global.exception.thread.ThreadIdNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;
    private final ImageRepository imageRepository;
    private final PaperRepository paperRepository;
    private final PaperImageRepository paperImageRepository;
    private final MybraryRepository mybraryRepository;
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ScrapRepository scrapRepository;
    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final FollowRepository followRepository;

    private final PaperDocumentRepository paperDocumentRepository;
    private final SearchService searchService;


    @Override
    public Long createThread(String email, ThreadPostDto threadPostDto) {

        CompletableFuture<Optional<Book>> bookAsync = asyncFetchBookById(threadPostDto.getBookId());
        CompletableFuture<Mybrary> mybraryAsync = asyncFetchMybraryByEmail(email);

        Optional<Book> book = bookAsync.join();
        Mybrary mybrary = mybraryAsync.join();

        Thread thread = Thread.create(mybrary, threadPostDto.isPaperPublic(), threadPostDto.isScrapEnable());
        threadRepository.save(thread);

        Member member = mybrary.getMember();
        Map<Long, String> paperTagList = new HashMap<>();

        // bookId가 null이 아닐 때
        // book의 마지막 페이지 번호
        int paperSeq = 0;
        if (threadPostDto.getBookId() != null) {
            paperSeq = scrapRepository.findLastPaperSeq(threadPostDto.getBookId())
                                      .orElse(0);
        }

        /* paper 객체 하나씩 생성하고 저장 */
        List<PostPaperDto> postPaperDtoList = threadPostDto.getPostPaperDto();
        for (PostPaperDto dto : postPaperDtoList) {
            StringBuilder mentionList = new StringBuilder();
            dto.getMentionList().forEach(id -> {
                mentionList.append(id).append(' ');
            });

            /* paper 객체 생성 */
            Paper paper = Paper.of(member, thread, dto, mentionList.toString(), threadPostDto);
            paperRepository.save(paper);
            thread.addPaper(paper);

            /* Image 객체 찾기 */
            CompletableFuture<Optional<Image>> image1Async = asyncFetchImageById(dto.getImageId1());
            CompletableFuture<Optional<Image>> image2Async = asyncFetchImageById(dto.getImageId2());

            Optional<Image> image1 = image1Async.join();
            Optional<Image> image2 = image2Async.join();

            /* paperImage 객체 저장 */
            PaperImage paperImage1 = PaperImage.of(paper, image1.orElse(null), 1);
            PaperImage paperImage2 = PaperImage.of(paper, image2.orElse(null), 2);
            paperImageRepository.save(paperImage1);
            paperImageRepository.save(paperImage2);

            /* scrap 객체 저장 */
            Scrap scrap = Scrap.builder()
                               .paper(paper)
                               .book(book.orElse(null))
                               .paperSeq(++paperSeq)
                               .build();
            scrapRepository.save(scrap);

            /* tag 목록 생성 */
            List<String> tagNameList = dto.getTagList();
            CompletableFuture<Void> tagListSavesAsync = asyncProcessAndSaveAllTag(tagNameList, paperTagList, paper);

            /* 여기서 페이퍼에 대한 멘션 알림 보내는 로직 */
            /* 쓰레드를 생성한 멤버가 sender, 멘션된 회원이 receiver, 알람타입은 11 */
            List<Long> mentionIdList = dto.getMentionList();
            for (Long mentionedId : mentionIdList) {
                notificationService.saveNotification(
                    NotificationPostDto.builder()
                                       .notifyType(11)
                                       .senderId(member.getId())
                                       .receiverId(mentionedId)
                                       .threadId(thread.getId())
                                       .paperId(paper.getId())
                                       .build());
            }
            tagListSavesAsync.join();
        }
        asyncProcessAndSaveAllPaperDocument(thread, paperTagList);
        return thread.getId();
    }

    /* 메인 피드 thread 조회하기 */
    @Override
    public List<GetThreadDto> getMainAllThread(Long myId, int page) {

        /* Pageable 객체 생성 */
        Pageable pageable = PageRequest.of(page, 5);

        /* following중인 멤버(본인 포함) 의 쓰레드 최대 5개와 관련된 정보 dto 생성 */
        List<GetThreadDto> threadDtoList = threadRepository.getFollowingThreadDtoResults(myId, pageable)
                                                           .orElseThrow(MainThreadListNotFoundException::new);

        System.out.println("1");
        System.out.println("크기" + threadDtoList.size());

        for (GetThreadDto aaa : threadDtoList) {
            System.out.print(aaa.getThreadId() + " ");
        }

        System.out.println();

        /* following중이지 않은 멤버의 쓰레드 최대 10개 조회와 관련 정보 dto 생성*/
        int getRandomCount = 10 - threadDtoList.size();
        pageable = PageRequest.of(page, getRandomCount);
        threadDtoList.addAll(threadRepository.getRandomThreadDtoResults(myId, pageable)
                                             .orElseThrow(MainThreadListNotFoundException::new));

        System.out.println("2");
        System.out.println("크기" + threadDtoList.size());

        for (int i = 5; i < threadDtoList.size(); i++) {
            System.out.print(threadDtoList.get(i).getThreadId() + " ");
        }

        System.out.println();

        /* list 내에서 무작위로 순서 배정 */
        Collections.shuffle(threadDtoList);
        /* followingThreadDtos의 각 threadId에 해당하는 paper관련 정보 조회 */
        for (int i = 0; i < threadDtoList.size(); i++) {

            GetThreadDto threadDto = threadDtoList.get(i);
            /* threadId에 해당하는 paper 관련 정보 dto 목록 조회 */
            List<GetFollowingPaperDto> getFollowingPaperDtoList =
                paperRepository.getFollowingPaperDtoResults(threadDto.getThreadId()).orElseThrow(PaperListNotFoundException::new);

            System.out.println("3");
            System.out.println("스레드번호 = " + threadDto.getThreadId());
            System.out.println("스레드순서 = " + i);
            System.out.println("페이퍼개수 = " + getFollowingPaperDtoList.size());

            /* 페이퍼 관련정보 처리 로직 */
            for (int j = 0; j < getFollowingPaperDtoList.size(); j++) {
                GetFollowingPaperDto paperDto = getFollowingPaperDtoList.get(j);
                /* 좋아요 여부 판단, 태그목록 포함 처리, 이미지 url들 포함 처리*/
                List<Long> imageUrls = imageRepository.findPaperImage(paperDto.getId()).orElseThrow(ImageNotFoundException::new);
                System.out.println("4");
                if (imageUrls.size() == 1) {
                    paperDto.setImageId1(imageUrls.get(0));
                    paperDto.setImageUrl1(imageRepository.findById(imageUrls.get(0)).orElse(null).getUrl());
                } else if (imageUrls.size() == 2) {
                    paperDto.setImageId1(imageUrls.get(0));
                    paperDto.setImageUrl1(imageRepository.findById(imageUrls.get(0)).orElse(null).getUrl());
                    paperDto.setImageId2(imageUrls.get(1));
                    paperDto.setImageUrl2(imageRepository.findById(imageUrls.get(1)).orElse(null).getUrl());
                }
                paperDto.setLikesCount(likeRepository.getLikeCount(paperDto.getId()).orElse(0));
                paperDto.setCommentCount(commentRepository.getCommentCount(paperDto.getId()).orElse(0));
                paperDto.setScrapCount(scrapRepository.getScrapCount(paperDto.getId()).orElse(0));
                paperDto.setLiked(likeService.checkIsLiked(paperDto.getId(), myId));
                paperDto.setTagList(tagRepository.getTagList(paperDto.getId()).orElse(new ArrayList<>()));
                List<MentionListDto> mentionList = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(paperDto.getMentionListString());
                while (st.hasMoreTokens()) {
                    Long id = Long.parseLong(st.nextToken());
                    String nickname = memberRepository.findById(id)
                                                      .orElseThrow()
                                                      .getNickname();
                    mentionList.add(new MentionListDto(id, nickname));
                }
                paperDto.setMentionList(mentionList);
                System.out.println("5");
                paperDto.setBookList(
                    bookRepository.getBookForMainThread(threadDto.getMemberId(), paperDto.getId()).orElse(new ArrayList<>()));
            }
            threadDto.setPaperList(getFollowingPaperDtoList);
            threadDto.setPaperPublic(getFollowingPaperDtoList.get(0).isPaperPublic());
            threadDto.setScrapEnable(getFollowingPaperDtoList.get(0).isScrapEnable());
        }
        return threadDtoList;
    }

    /* 나의 모든 thread들만 조회하기 */
    @Override
    public List<ThreadInfoGetDto> getMyAllThread(Long myId, Pageable pageable) {

        List<Thread> threadList = threadRepository.getThreadsByMemberId(myId, pageable)
                                                  .orElse(new ArrayList<>());
        List<ThreadInfoGetDto> threadInfoGetDtoList = new ArrayList<>();
        if (!threadList.isEmpty()) {
            for (Thread thread : threadList) {
                ThreadInfoGetDto threadDto = threadRepository.getSimpleThreadDtoResult(thread.getId());
                threadDto.setLikesCount(likeRepository.getThreadtLikeCount(thread.getId()).orElse(0));
                threadDto.setCommentCount(commentRepository.getThreadCommentCount(thread.getId()).orElse(0));
                threadDto.setScrapCount(scrapRepository.getThreadScrapCount(thread.getId()).orElse(0));
                threadInfoGetDtoList.add(threadDto);
            }
        }
        return threadInfoGetDtoList;
    }

    /* 특정 member의 모든 thread들만 조회하기 */
    @Override
    public List<ThreadInfoGetDto> getOtherAllThread(String email, Long memberId, Pageable pageable) {
        /* 스레드 접근 권한 판단 */
        Long myId = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new).getId();
        Member Owner = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if (!Owner.isProfilePublic()) {
            Follow follow = followRepository.findFollow(myId, Owner.getId()).orElseThrow(ThreadAccessDeniedException::new);
        }
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(NullPointerException::new);
        log.info("memeberId:" + memberId);
        List<Thread> threadList = threadRepository.getThreadsByMemberId(memberId, pageable)
                                                  .orElse(new ArrayList<>());
        List<ThreadInfoGetDto> threadInfoGetDtoList = new ArrayList<>();
        if (!threadList.isEmpty()) {
            for (Thread thread : threadList) {
                ThreadInfoGetDto threadDto = threadRepository.getSimpleThreadDtoResult(thread.getId());
                threadDto.setLikesCount(likeRepository.getThreadtLikeCount(thread.getId()).orElse(0));
                threadDto.setCommentCount(commentRepository.getThreadCommentCount(thread.getId()).orElse(0));
                threadDto.setScrapCount(scrapRepository.getThreadScrapCount(thread.getId()).orElse(0));
                threadInfoGetDtoList.add(threadDto);
            }
        }
        return threadInfoGetDtoList;
    }

    /* 쓰레드 아이디로 쓰레드 단건조회 */
    @Override
    public GetThreadDto getThread(String email, Long memberId, Long threadId) {
        GetThreadDto thread = threadRepository.getOneThread(threadId).orElseThrow(ThreadIdNotFoundException::new);
        /* 스레드 접근 권한 판단 */
        Long myId = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new).getId();
        if (!thread.isPaperPublic()) {
            Follow follow = followRepository.findFollow(myId, thread.getMemberId()).orElseThrow(ThreadAccessDeniedException::new);
        }
        /* threadId에 해당하는 paper 관련 정보 dto 목록 조회 */
        List<GetFollowingPaperDto> getFollowingPaperDtoList =
            paperRepository.getFollowingPaperDtoResults(thread.getThreadId()).orElseThrow(PaperListNotFoundException::new);

        /* 페이퍼 관련정보 처리 로직 */
        for (int j = 0; j < getFollowingPaperDtoList.size(); j++) {
            GetFollowingPaperDto paperDto = getFollowingPaperDtoList.get(j);
            /* 좋아요 여부 판단, 태그목록 포함 처리, 이미지 url들 포함 처리*/
            List<Long> imageUrls = imageRepository.findPaperImage(paperDto.getId()).orElseThrow(ImageNotFoundException::new);
            System.out.println("4");
            if (imageUrls.size() == 1) {
                paperDto.setImageId1(imageUrls.get(0));
                paperDto.setImageUrl1(imageRepository.findById(imageUrls.get(0)).orElse(null).getUrl());
            } else if (imageUrls.size() == 2) {
                paperDto.setImageId1(imageUrls.get(0));
                paperDto.setImageUrl1(imageRepository.findById(imageUrls.get(0)).orElse(null).getUrl());
                paperDto.setImageId2(imageUrls.get(1));
                paperDto.setImageUrl2(imageRepository.findById(imageUrls.get(1)).orElse(null).getUrl());
            }
            paperDto.setLikesCount(likeRepository.getLikeCount(paperDto.getId()).orElse(0));
            paperDto.setCommentCount(commentRepository.getCommentCount(paperDto.getId()).orElse(0));
            paperDto.setScrapCount(scrapRepository.getScrapCount(paperDto.getId()).orElse(0));
            paperDto.setLiked(likeService.checkIsLiked(paperDto.getId(), myId));
            paperDto.setTagList(tagRepository.getTagList(paperDto.getId()).orElse(new ArrayList<>()));
            List<MentionListDto> mentionList = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(paperDto.getMentionListString());
            while (st.hasMoreTokens()) {
                Long id = Long.parseLong(st.nextToken());
                String nickname = memberRepository.findById(id)
                                                  .orElseThrow()
                                                  .getNickname();
                mentionList.add(new MentionListDto(id, nickname));
            }
            paperDto.setMentionList(mentionList);
            System.out.println("5");
            paperDto.setBookList(
                bookRepository.getBookForMainThread(thread.getMemberId(), paperDto.getId()).orElse(new ArrayList<>()));
        }
        thread.setPaperList(getFollowingPaperDtoList);
        thread.setPaperPublic(getFollowingPaperDtoList.get(0).isPaperPublic());
        thread.setScrapEnable(getFollowingPaperDtoList.get(0).isScrapEnable());
        return thread;
//            /* 스레드 접근 권한 판단 */
//            Long myId = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new).getId();
//            Member Owner = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
//            if(!Owner.isProfilePublic()){
//                  Follow follow = followRepository.findFollow(myId, Owner.getId()).orElseThrow(ThreadAccessDeniedException::new);
//            }
//            /* paperGetDtoList 정보 조회 및 생성 */
//            List<PaperGetDto> paperGetDtoList = paperRepository.getPaperGetDto(threadId).orElseThrow(PaperListNotFoundException::new);
//            /**/
//            log.info("2");
//            log.info("size: " + paperGetDtoList.size());
//            for (int i = 0;i<paperGetDtoList.size();i++) {
//                  PaperGetDto paper = paperGetDtoList.get(i);
//                  paper.setLikeCount(likeRepository.getLikeCount(paper.getPaperId()).orElse(0));
//                  paper.setCommentCount(commentRepository.getCommentCount(paper.getPaperId()).orElse(0));
//                  paper.setScrapCount(scrapRepository.getScrapCount(paper.getPaperId()).orElse(0));
//                  log.info("fd" + paper.getPaperId());
//                  paper.updateIsLiked(likeService.checkIsLiked(paper.getPaperId(), memberId));
//                  log.info("checkisliked");
//                  List<Tag> tagList = tagRepository.getTagsByPaperId(paper.getPaperId())
//                                                   .orElse(Collections.emptyList());
//                  /**/
//                  log.info("3");
//                  List<String> tagNameList = new ArrayList<>();
//                  if (!tagList.isEmpty()) {
//                        tagNameList = tagList.stream()
//                                             .map(Tag::getTagName)
//                                             .toList();
//                  }
//                  paper.updateTagList(tagNameList);
//                  /* image id와 url은 따로 */
//                  Image image1 = imageRepository.findImage1ByPaperId(paper.getPaperId()).orElse(null);
//                  Image image2 = imageRepository.findImage2ByPaperId(paper.getPaperId()).orElse(null);
//                  if(image1 != null){
//                        paper.updateImageId1(image1.getId());
//                        paper.updateImageUrl1(image1.getUrl());
//                  }
//                  if(image2 != null){
//                        paper.updateImageId2(image2.getId());
//                        paper.updateImageUrl2(image2.getUrl());
//                  }
//            }
//            /**/
//            log.info("4");
//            /* ThreadGetDto 생성 */
//            Paper firstPaper = paperRepository.findById(paperGetDtoList.get(0).getPaperId())
//                                              .orElseThrow(NullPointerException::new);
//            MemberInfoDto memberInfoDto = memberRepository.getMemberInfo(memberId).orElseThrow(MemberNotFoundException::new);
//            ThreadGetDto threadGetDto = ThreadGetDto.builder()
//                                                    .threadId(threadId)
//                                                    .member(memberInfoDto)
//                                                    .paperList(paperGetDtoList)
//                                                    .build();
//            threadGetDto.updateIsPublicEnable(firstPaper.isPaperPublic());
//            threadGetDto.updateIsScrapEnable(firstPaper.isScrapEnabled());
//            return threadGetDto;
    }

    @Override
    public Long updateThread(Long myId, ThreadUpdateDto threadUpdateDto) {

        Thread thread = getThreadById(threadUpdateDto.getThreadId());

//        for (PaperUpdateDto paperUpdateDto : threadUpdateDto.getPaperList()) {
//            paper.update(paperUpdateDto, threadUpdateDto);
//
//            /* 기존 태그들 삭제 */
//            tagRepository.deleteAllByPaperId(paper.getId());
//
//            List<Tag> tagList = new ArrayList<>();
//            for (String tagNames : paperUpdateDto.getTagList()) {
//                tagList.add(Tag.builder()
//                               .tagName(tagNames)
//                               .paper(paper)
//                               .build());
//            }
//
//            tagRepository.saveAll(tagList);
//            updatePaperContentAsync(paper.getId(), paperUpdateDto);
//            }
//        }

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (PaperUpdateDto paperUpdateDto : threadUpdateDto.getPaperList()) {
            Paper paper = getPaperById(paperUpdateDto.getPaperId());
            futures.add(asyncDeleteAllTagByPaperId(paper.getId()));
            futures.add(asyncSaveAllTag(paperUpdateDto.getTagList(), paper));
            futures.add(asyncUpdatePaperDocument(paperUpdateDto.getPaperId(), paperUpdateDto));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return thread.getId();
    }


    @Override
    public int deleteThread(Long myId, Long threadId) {
        /* 삭제된 페이퍼 개수 반환 */
        Thread thread = threadRepository.findById(threadId)
                                        .orElseThrow(NullPointerException::new);
        int count = thread.getPaperList().size();
        threadRepository.delete(thread);
        asyncDeleteAllPaperDocumentByThreadId(threadId);
        return count;
    }

    @Async
    public CompletableFuture<Void> asyncDeleteAllTagByPaperId(Long paperId) {
        tagRepository.deleteAllByPaperId(paperId);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> asyncSaveAllTag(List<String> tagNames, Paper paper) {
        List<Tag> tagList = new ArrayList<>();
        for (String tag : tagNames) {
            tagList.add(Tag.builder()
                           .tagName(tag)
                           .paper(paper)
                           .build());
        }

        tagRepository.saveAll(tagList);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> asyncUpdatePaperDocument(Long paperId, PaperUpdateDto updateDto) {
        PaperDocument paperDocument = paperDocumentRepository.findById(paperId)
                                                             .orElseThrow(NullPointerException::new);

        paperDocument.update(updateDto);
        paperDocumentRepository.save(paperDocument);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> asyncDeleteAllPaperDocumentByThreadId(Long threadId) {
        paperDocumentRepository.deleteAll(searchService.getPaperDocumentListByThreadId(threadId));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Optional<Image>> asyncFetchImageById(Long imageId) {
        if (imageId == null) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return CompletableFuture.completedFuture(imageRepository.findById(imageId));
    }

    @Async
    public CompletableFuture<Optional<Book>> asyncFetchBookById(Long bookId) {
        if (bookId == null) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return CompletableFuture.completedFuture(bookRepository.findById(bookId));
    }

    @Async
    public CompletableFuture<Mybrary> asyncFetchMybraryByEmail(String email) {
        return CompletableFuture.completedFuture(
            mybraryRepository.findMybraryByEmail(email)
                             .orElseThrow(MybraryNotFoundException::new)
        );
    }

    @Async
    public CompletableFuture<Void> asyncProcessAndSaveAllTag(List<String> tagNameList,
                                                             Map<Long, String> paperTagList,
                                                             Paper paper) {
        StringBuilder tags = new StringBuilder();
        List<Tag> tagList = new ArrayList<>();
        if (!tagNameList.isEmpty()) {
            for (String tagName : tagNameList) {
                tagList.add(Tag.builder()
                               .tagName(tagName)
                               .paper(paper)
                               .build());
                tags.append(tagName).append(' ');
            }
            tagRepository.saveAll(tagList);
        }
        paperTagList.put(paper.getId(), tags.toString());
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> asyncProcessAndSaveAllPaperDocument(Thread savedThread, Map<Long, String> tagList) {
        List<PaperDocument> paperDocuments = new ArrayList<>();
        savedThread.getPaperList().forEach(
            paper -> paperDocuments.add(PaperDocument.of(savedThread.getId(), paper, tagList.get(paper.getId()))));
        paperDocumentRepository.saveAll(paperDocuments);
        return CompletableFuture.completedFuture(null);
    }

    private Paper getPaperById(Long id) {
        return paperRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    private Thread getThreadById(Long id) {
        return threadRepository.findById(id).orElseThrow(ThreadIdNotFoundException::new);
    }
}