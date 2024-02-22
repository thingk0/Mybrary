package com.mybrary.backend.domain.mybrary.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "photo_frame_image_id")
    private Image photoFrameImage;

    @Setter
    @Column(name = "background_color")
    private int backgroundColor;
    @Setter
    @Column(name = "desk_color")
    private int deskColor;
    @Setter
    @Column(name = "bookshelf_color")
    private int bookshelfColor;
    @Setter
    @Column(name = "easel_color")
    private int easelColor;

    @OneToOne(mappedBy = "mybrary", cascade = {CascadeType.REMOVE})
    private Bookshelf bookshelf;

    @OneToOne(mappedBy = "mybrary", cascade = {CascadeType.REMOVE})
    private RollingPaper rollingPaper;

    @OneToMany(mappedBy = "mybrary", cascade = {CascadeType.REMOVE})
    private List<Thread> threadList = new ArrayList<>();
}
