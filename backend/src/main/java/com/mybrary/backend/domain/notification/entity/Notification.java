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
@SQLDelete(sql = "UPDATE notification SET is_deleted = TRUE WHERE notification_id = ?")
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

    @Column(name = "notify_type")
    private int notifyType;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "thread_id")
    private Long threadId;

    @Column(name = "paper_id")
    private Long paperId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "reply_comment_id")
    private Long replyCommentId;

}
