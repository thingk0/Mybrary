package com.mybrary.backend.domain.mybrary.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE mybrary SET is_deleted = TRUE WHERE mybrary_id = ?")
public class Mybrary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mybrary_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_frame_image_id")
    private Image photoFrameImage;

    @Column(name = "background_color")
    private int backgroundColor;

    @Column(name = "desk_color")
    private int deskColor;

    @Column(name = "bookshelf_color")
    private int bookshelfColor;

    @Column(name = "easel_color")
    private int easelColor;

}
