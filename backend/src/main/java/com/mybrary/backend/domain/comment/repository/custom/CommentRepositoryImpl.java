package com.mybrary.backend.domain.comment.repository.custom;

import static com.mybrary.backend.domain.comment.entity.QComment.comment;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.member.entity.QMember.member;

import com.mybrary.backend.domain.comment.dto.responseDto.CommentGetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory query;
    public Optional<List<CommentGetDto>> getCommentGetDtoListByPaperId(Long paperId) {

        return Optional.ofNullable(query.select(
                Projections.fields(CommentGetDto.class,
                    comment.id.as("commentId"),
                    member.id.as("ownerId"),
                    member.nickname.as("ownerNickname"),
                    comment.content.as("content"),
                    comment.colorCode.as("colorCode"),
                    comment.createdAt.as("createdAt")
                ))
            .from(comment)
            .leftJoin(member).on(comment.member.id.eq(member.id))
                                        .where(comment.paper.id.eq(paperId).and(comment.parentComment.isNull()))
            .fetch());
    }

    @Override
    public Optional<Integer> getCommentCount(Long paperId) {
        return Optional.ofNullable(query.select(comment.count().intValue())
                                        .from(comment)
                                        .where(comment.paper.id.eq(paperId))
                                        .fetchOne()
        );
    }

    public Optional<Integer> getThreadCommentCount(Long threadId) {
        return Optional.ofNullable(query.select(comment.count()
                                                       .intValue())
                                        .from(comment)
                                        .leftJoin(paper)
                                        .on(comment.paper.id.eq(paper.id))
                                        .where(paper.thread.id.eq(threadId))
                                        .fetchOne()
        );
    }

    /* 댓글의 대댓글 개수 반환하기*/
    @Override
    public Integer getChildCommentCount(Long commentId) {
        Integer count = query.select(comment.count().intValue())
            .from(comment)
            .where(comment.parentComment.id.eq(commentId))
            .fetchOne();
        return Optional.ofNullable(count).orElse(0);
    }

    @Override
        public Optional<List<CommentGetDto>> getChildCommentGetDtoList (Long commentId){
            return Optional.ofNullable(query.select(
                                                Projections.fields(CommentGetDto.class,
                                                    comment.id.as("commentId"),
                                                    member.id.as("ownerId"),
                                                    member.nickname.as("ownerNickname"),
                                                    comment.content.as("content"),
                                                    comment.colorCode.as("colorCode"),
                                                    comment.createdAt.as("createdAt")
                                                ))
                                            .from(comment)
                                            .leftJoin(member)
                                            .on(comment.member.id.eq(member.id))
                                            .where(comment.parentComment.id.eq(commentId))
                                            .fetch());

        }

}
