package com.mybrary.backend.domain.notification.repository;

import com.mybrary.backend.domain.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /* 나의 알림 조회 */
    @Query("select n from Notification n where n.receiver.id = :myId and n.isRead = false")
    List<Notification> getAllNotification(@Param("myId") Long myId);

    /* 알림 전체 삭제 */
    @Query("delete from Notification n where n.receiver.id = :myId and n.isDeleted = false")
    void deleteAllByMemberId(Long myId);
}
