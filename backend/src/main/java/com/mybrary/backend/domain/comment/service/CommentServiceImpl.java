package com.mybrary.backend.domain.comment.service;

import com.mybrary.backend.domain.comment.dto.CommentGetAllDto;
import com.mybrary.backend.domain.comment.dto.CommentGetDto;
import com.mybrary.backend.domain.comment.dto.CommentPostDto;
import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.comment.repository.CommentRepository;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PaperRepository paperRepository;
    private final MemberRepository memberRepository;

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

        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public Long deleteComment(Long commentId) {
        paperRepository.deleteById(commentId);
        return commentId;
    }

    @Override
    public CommentGetAllDto getAllComment(Long memberId, Long paperId) {
//        - 페이퍼ID(paperId)
//            - 댓글 리스트(commentList)
//            [
//            - 댓글ID(commentId)
//                - 작성자ID(ownerId)
//                - 작성자닉네임(ownerNickname)
//                - 작성자여부(isOwner)
//                - 댓글내용(comment)
//                - 컬러코드(colorCode)
//                - 시간(time)

        List<CommentGetDto> commentGetDtoList = commentRepository.getCommentGetDtoListByPaperId(paperId).orElseThrow(NullPointerException::new);
        CommentGetAllDto commentGetAllDto = CommentGetAllDto.builder()
            .commentGetDtoList(commentGetDtoList)
            .paperId(paperId)
            .build();

        /* 작성자여부는 따로 구하기 */
        for(CommentGetDto commentGetDto : commentGetDtoList){
            boolean isOwnerTrue =  commentGetDto.getOwnerId().equals(memberId);
            commentGetDto.setOwner(isOwnerTrue);
        }

        return commentGetAllDto;
    }

    @Override
    public Long getChildComments(Long commentId) {
        return null;
    }
}
