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

    @Override
    public Long createComment(CommentPostDto commentPostDto) {

        Paper paper = paperRepository.findById(commentPostDto.getPaperId())
                                     .orElseThrow(NullPointerException::new);
        Member member = memberRepository.findById(commentPostDto.getMemberid())
                                        .orElseThrow(NullPointerException::new);
        Comment parentComment = commentRepository
            .findById(commentPostDto.getParentCommentId())
            .orElseThrow(NullPointerException::new);

        Comment comment = Comment.builder()
                                 .paper(paper)
                                 .content(commentPostDto.getContent())
                                 .member(member)
                                 .parentComment(parentComment)
                                 .colorCode(commentPostDto.getColorCode())
                                 .build();

        Comment savedComment = commentRepository.save(comment);

        /*    웹소켓 코드    */

        // 게시글 작성자 Id
        Long paperWirterId = paper.getMember().getId();
        // 댓글 작성자 Id
        Long commentWriterId = parentComment.getMember().getId();

        // 댓글이랑 대댓글로 구분
        if (commentPostDto.getParentCommentId() == null) {

            // 댓글일 때
            // 댓글 작성자를 sender, 게시글 작성자를 receiver 로 하는 type3 알림 보내기
            NotificationPostDto notification = NotificationPostDto.builder()
                                                                  .senderId(member.getId())
                                                                  .receiverId(paperWirterId)
                                                                  .notifyType(3)
                                                                  .threadId(paper.getThread().getId())
                                                                  .paperId(paper.getId())
                                                                  .commentId(savedComment.getId())
                                                                  .build();
            notificationService.saveNotification(notification);
        } else {

            // 대댓글 일 때
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

        }

        return comment.getId();
    }

    @Override
    public Long deleteComment(Long commentId) {
        paperRepository.deleteById(commentId);
        return commentId;
    }

    /* */
    @Override
    public CommentGetAllDto getAllComment(Long memberId, Long paperId) {

        List<CommentGetDto> commentGetDtoList = commentRepository.getCommentGetDtoListByPaperId(paperId)
                                                                 .orElseThrow(NullPointerException::new);
        CommentGetAllDto commentGetAllDto = CommentGetAllDto.builder()
                                                            .commentGetDtoList(commentGetDtoList)
                                                            .paperId(paperId)
                                                            .build();

        /* 작성자여부는 따로 구하기 */
        for (CommentGetDto commentGetDto : commentGetDtoList) {
            boolean isOwnerTrue = commentGetDto.getOwnerId().equals(memberId);
            commentGetDto.updateIsOwner(isOwnerTrue);
        }

        return commentGetAllDto;
    }

    @Override
    public Long getChildComments(Long commentId) {
        return null;
    }
}
