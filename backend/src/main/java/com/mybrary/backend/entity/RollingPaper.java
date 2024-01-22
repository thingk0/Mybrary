package com.mybrary.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RollingPaper extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rollingpaper_id")
  private Long id;

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "mybrary_id")
//  private Mybrary mybrary;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image rollingpaperImage;

}
