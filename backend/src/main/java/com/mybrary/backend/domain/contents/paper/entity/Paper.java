package com.mybrary.backend.domain.contents.paper.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted <> true")
@SQLDelete(sql = "UPDATE paper SET is_deleted = TRUE WHERE paper_id = ?")
public class Paper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "threads_id")
    private Thread thread;

    private String content1;

    private String content2;

    @Builder.Default()
    private int layoutType = 1;

    private int scrapCount;

    private int commentCount;

    private int likeCount;

    @Builder.Default()
    private boolean isScrapEnabled = true;

    @Builder.Default()
    private boolean isPaperPublic = true;

}
