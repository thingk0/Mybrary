package com.mybrary.backend.domain.book.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.pickbook.entity.PickBook;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE book SET is_deleted = TRUE WHERE book_id = ?")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member member;

    @Setter
    @Column(name = "cover_font")
    private String coverFont;

    @Setter
    @Column(name = "cover_title")
    private String coverTitle;

    @Setter
    @Column(name = "cover_layout")
    private int coverLayout;

    @Setter
    @Column(name = "cover_color")
    private int coverColor;

    /* book - pickbook 양방향 관계 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    private List<PickBook> pickBookList = new ArrayList<>();

    /* book - scrap 양방향 관계 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    private List<Scrap> scrapList = new ArrayList<>();

}

