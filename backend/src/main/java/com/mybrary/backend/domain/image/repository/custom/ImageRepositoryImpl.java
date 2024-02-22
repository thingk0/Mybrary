package com.mybrary.backend.domain.image.repository.custom;

import static com.mybrary.backend.domain.contents.paper_image.entity.QPaperImage.paperImage;
import static com.mybrary.backend.domain.image.entity.QImage.image;

import com.mybrary.backend.domain.image.entity.Image;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public Optional<List<Long>> findPaperImage(Long paperId) {
        return Optional.ofNullable(query.select(image.id)
                                       .from(image)
                                       .leftJoin(paperImage).on(paperImage.image.id.eq(image.id))
                                       .where(paperImage.paper.id.eq(paperId))
                                       .orderBy(paperImage.imageSeq.asc())
                                       .fetch());
    }

    @Override
    public Optional<Image> findImage1ByPaperId(Long paperId) {
        return Optional.ofNullable(query.select(image)
                                        .from(image)
                                        .leftJoin(paperImage).on(paperImage.image.id.eq(image.id))
                                        .where(paperImage.paper.id.eq(paperId).and(paperImage.imageSeq.eq(1)))
                                        .fetchOne());
    }
    @Override
    public Optional<Image> findImage2ByPaperId(Long paperId) {
        return Optional.ofNullable(query.select(image)
                                        .from(image)
                                        .leftJoin(paperImage).on(paperImage.image.id.eq(image.id))
                                        .where(paperImage.paper.id.eq(paperId).and(paperImage.imageSeq.eq(2)))
                                        .fetchOne());
    }
}
