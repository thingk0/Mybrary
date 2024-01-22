package com.mybrary.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor //모든 필드 대상 생성자
@NoArgsConstructor  //기본 생성자
//@ToString(of = {"id", "username", "age"})
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private long paperId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Thread threadId;

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
