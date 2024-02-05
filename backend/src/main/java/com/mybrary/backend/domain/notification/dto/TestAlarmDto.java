package com.mybrary.backend.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestAlarmDto {

    /**
     *   알림 저장 dto
     *   알림이 발생하는 모든 API에서 이 Dto에 맞춰 데이터를 넣고
     *   알림 서비스의 알림 저장 메서드를 호출해야함
     */

    private String sender;
    private String receiver;

}
