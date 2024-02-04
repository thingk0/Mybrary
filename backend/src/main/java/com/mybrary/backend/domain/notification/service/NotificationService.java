package com.mybrary.backend.domain.notification.service;

import com.mybrary.backend.domain.notification.dto.NotificationGetDto;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    List<NotificationGetDto> getAllNotification(Long myId, Pageable page);

    void deleteNotification(Long notifyId);

    void deleteAllNotification(Long myId);

    void saveNotification(NotificationPostDto notification);
}
