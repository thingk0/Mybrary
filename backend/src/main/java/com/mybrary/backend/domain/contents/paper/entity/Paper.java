package com.mybrary.backend.domain.contents.paper.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperUpdateDto;
import com.mybrary.backend.domain.contents.paper.dto.requestDto.PostPaperDto;
import com.mybrary.backend.domain.contents.paper_image.entity.PaperImage;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.tag.entity.Tag;
import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadUpdateDto;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.member.entity.Member;
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
import java.util.ArrayList;
import java.util.List;
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
@SQLDelete(sql = "UPDATE paper SET is_deleted = TRUE WHERE paper_id = ?")
public class Paper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Thread thread;

    @Column(name = "content1")
    private String content1;

    @Column(name = "content2")
    private String content2;

    @Builder.Default()
    @Column(name = "layout_type")
    private int layoutType = 1;

    @Column(name = "scrap_count")
    private int scrapCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "mention_list")
    private String mentionList;

    @Builder.Default()
    @Column(name = "is_scrap_enabled")
    private boolean isScrapEnabled = true;

    @Builder.Default()
    @Column(name = "is_paper_public")
    private boolean isPaperPublic = true;

    /* 스크랩과 양방향 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Scrap> scrapList = new ArrayList<>();

    /* comment 양방향 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Comment> commentList = new ArrayList<>();

    /* tag 양방향 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Tag> tagList = new ArrayList<>();

    /* like 양방향 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Like> likeList = new ArrayList<>();

    /* paperImage 양방향 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "paper", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<PaperImage> paperImageList = new ArrayList<>();

    public static Paper of(Member member, Thread thread, PostPaperDto postPaperDto,
                           String mentionList, ThreadPostDto threadPostDto) {
        return Paper.builder()
                    .member(member)
                    .thread(thread)
                    .layoutType(postPaperDto.getLayoutType())
                    .content1(postPaperDto.getContent1())
                    .content2(postPaperDto.getContent2())
                    .mentionList(mentionList)
                    .isPaperPublic(threadPostDto.isPaperPublic())
                    .isScrapEnabled(threadPostDto.isScrapEnable())
                    .build();
    }

    public void update(PaperUpdateDto paperUpdateDto, ThreadUpdateDto threadUpdateDto) {
        this.layoutType = paperUpdateDto.getLayoutType();
        this.content1 = paperUpdateDto.getContent1();
        this.content2 = paperUpdateDto.getContent2();
        this.isScrapEnabled = threadUpdateDto.isScrapEnable();
        this.isPaperPublic = threadUpdateDto.isPaperPublic();
    }

    public void updateMentionList(String mentionList) {
        this.mentionList = mentionList;
    }

}
