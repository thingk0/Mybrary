package com.mybrary.backend.domain.notification.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.thread.entity.Threads;
import com.mybrary.backend.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted <> true")
@SQLDelete(sql = "UPDATE member SET is_deleted = TRUE WHERE member_id = ?")
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

    private int notifyType;

    private boolean isRead;

    private Long bookId;
    private Long threadId;
    private Long paperId;
    private Long commentId;
    private Long replyCommentId;

}
