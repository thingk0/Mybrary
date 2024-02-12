package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.requestDto.CommentPostDto;
import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetAllDto;
import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetDto;
import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.comment.repository.CommentRepository;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.service.NotificationService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PaperRepository paperRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    @Override
    public CommentGetDto createComment(Long myId, CommentPostDto commentPostDto) {

        Paper paper = paperRepository.findById(commentPostDto.getPaperId())
                                     .orElseThrow(NullPointerException::new);
        Member member = memberRepository.findById(myId)
                                        .orElseThrow(NullPointerException::new);

        Long parentCommentId = commentPostDto.getParentCommentId();
        Comment comment;


        if(parentCommentId == null){
            /* 댓글인경우 */
            comment = Comment.builder()
                                     .paper(paper)
                                     .content(commentPostDto.getContent())
                                     .member(member)
                                     .colorCode(commentPostDto.getColorCode())
                                     .build();
            Comment savedComment = commentRepository.save(comment);

            CommentGetDto sendComment = CommentGetDto.builder()
                .commentId(savedComment.getId())
                .ownerId(member.getId())
                .ownerNickname(member.getNickname())
                .content(savedComment.getContent())
                .colorCode(savedComment.getColorCode())
                .createdAt(savedComment.getCreatedAt())
                .build();

            // 게시글 작성자 Id
            Long paperWriterId = paper.getMember().getId();

            /* 웹소켓 알림 관련 */
            // 댓글 작성자를 sender, 게시글 작성자를 receiver 로 하는 type3 알림 보내기
            NotificationPostDto notification = NotificationPostDto.builder()
                                                                  .senderId(member.getId())
                                                                  .receiverId(paperWriterId)
                                                                  .notifyType(3)
                                                                  .threadId(paper.getThread().getId())
                                                                  .paperId(paper.getId())
                                                                  .commentId(savedComment.getId())
                                                                  .build();
            notificationService.saveNotification(notification);

            return sendComment;

        }else{
            /* 대댓글인경우 */
            Comment parentComment = commentRepository.findById(parentCommentId)
                                                     .orElseThrow(NullPointerException::new);

            comment = Comment.builder()
                                     .paper(paper)
                                     .content(commentPostDto.getContent())
                                     .member(member)
                                     .parentComment(parentComment)
                                     .colorCode(commentPostDto.getColorCode())
                                     .build();
            Comment savedComment = commentRepository.save(comment);

            CommentGetDto sendComment = CommentGetDto.builder()
                                                     .commentId(savedComment.getId())
                                                     .ownerId(member.getId())
                                                     .ownerNickname(member.getNickname())
                                                     .content(savedComment.getContent())
                                                     .colorCode(savedComment.getColorCode())
                                                     .createdAt(savedComment.getCreatedAt())
                                                     .build();

            // 게시글 작성자 Id
            Long paperWirterId = paper.getMember().getId();
            // 댓글 작성자 Id
            Long commentWriterId = parentComment.getMember().getId();

            // 1. 대댓글 작성자를 sender, 부모댓글 작성자를 receiver 로 하는 type4 알림 보내기
            NotificationPostDto notification1 = NotificationPostDto.builder()
                                                                   .senderId(member.getId())
                                                                   .receiverId(commentWriterId)
                                                                   .notifyType(4)
                                                                   .threadId(paper.getThread().getId())
                                                                   .paperId(paper.getId())
                                                                   .commentId(parentComment.getId())
                                                                   .replyCommentId(savedComment.getId())
                                                                   .build();
            notificationService.saveNotification(notification1);

            // 2. 대댓글 작성자를 sender, 게시글 작성자를 receiver 로 하는 type5 알림 보내기
            NotificationPostDto notification2 = NotificationPostDto.builder()
                                                                   .senderId(member.getId())
                                                                   .receiverId(paperWirterId)
                                                                   .notifyType(5)
                                                                   .threadId(paper.getThread().getId())
                                                                   .paperId(paper.getId())
                                                                   .commentId(parentComment.getId())
                                                                   .replyCommentId(savedComment.getId())
                                                                   .build();
            notificationService.saveNotification(notification2);

            return sendComment;
        }


    }

    @Transactional
    @Override
    public Long deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return commentId;
    }

    /* */
    @Transactional
    @Override
    public CommentGetAllDto getAllComment(Long myId, Long paperId) {

        List<CommentGetDto> commentGetDtoList = commentRepository.getCommentGetDtoListByPaperId(paperId)
                                                                 .orElse(new ArrayList<>());

        if(!commentGetDtoList.isEmpty()){
            /* 작성자여부는 따로 구하기 */
            for (CommentGetDto commentGetDto : commentGetDtoList) {
                boolean isOwnerTrue = commentGetDto.getOwnerId().equals(myId);
                commentGetDto.updateIsOwner(isOwnerTrue);
                commentGetDto.updateChildCommentCount(commentRepository.getChildCommentCount(commentGetDto.getCommentId()));
            }
        }
        CommentGetAllDto commentGetAllDto = CommentGetAllDto.builder()
                                                            .commentGetDtoList(commentGetDtoList)
                                                            .paperId(paperId)
                                                            .build();

        return commentGetAllDto;
    }
    @Transactional
    @Override
    public CommentGetAllDto getChildComments(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
        Paper paper = comment.getPaper();
        List<CommentGetDto> commentGetDtoList = commentRepository.getChildCommentGetDtoList(commentId)
                                                                 .orElse(new ArrayList<>());
        CommentGetAllDto commentGetAllDto = CommentGetAllDto.builder()
                                                            .commentGetDtoList(commentGetDtoList)
                                                            .paperId(paper.getId())
                                                            .build();

        return commentGetAllDto;
    }
}
