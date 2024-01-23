package com.mybrary.backend.domain.notification.entity;

import com.mybrary.backend.domain.base.BaseEntity;
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
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notification_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Member sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private Member receiver;

  private int targetType;
  private boolean isRead;

  private Long bookId;
  private Long threadId;
  private Long paperId;
  private Long commentId;
  private Long recommentId;

}
