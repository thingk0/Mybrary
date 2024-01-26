package com.mybrary.backend.domain.contents.paper.entity;

import com.mybrary.backend.domain.contents.thread.entity.Threads;
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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Threads threadId;

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
