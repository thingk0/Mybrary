
package com.mybrary.backend.domain.notification.repository.custom;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.notification.entity.Notification;

public interface NotificationRepositoryCustom {

    Notification getForFollowCancel(Long myId, Long memberId);
}
