package com.mybrary.backend.domain.comment.repository.custom;

import static com.mybrary.backend.domain.comment.entity.QComment.comment;
import static com.mybrary.backend.domain.contents.like.entity.QLike.like;
import static com.mybrary.backend.domain.contents.paper.entity.QPaper.paper;
import static com.mybrary.backend.domain.member.entity.QMember.member;
import com.mybrary.backend.domain.comment.dto.CommentGetDto;
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

//        private Long commentId;
//        private Long ownerId;
//        private String ownerNickname;
//        private boolean isOwner;
//        private String content;
//        private int colorCode;
//        private String time;

        return Optional.ofNullable(query.select(
                Projections.fields(CommentGetDto.class,
                    comment.id.as("commentId"),
                    member.id.as("ownerId"),
                    member.nickname.as("ownerNickname"),
                    comment.content.as("content"),
                    comment.colorCode.as("colorCode"),
                    comment.createdAt.as("time")
                ))
            .from(comment)
            .leftJoin(paper).on(comment.paper.id.eq(paperId))
            .leftJoin(member).on(comment.member.id.eq(member.id))
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

}
