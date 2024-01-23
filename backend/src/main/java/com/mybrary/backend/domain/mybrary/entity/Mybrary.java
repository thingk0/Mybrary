package com.mybrary.backend.domain.mybrary.entity;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor //모든 필드 대상 생성자
@NoArgsConstructor  //기본 생성자
public class Mybrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Image photoFrameImage;


    private int backgoundColor;

    private int deskColor;

    private int booshelfColor;

    private int easelColor;



}
