package com.mybrary.backend.domain.notification.controller;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.notification.dto.NotificationGetDto;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.global.format.ApiResponse;
import com.mybrary.backend.global.format.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification 컨트롤러", description = "Notification Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationControllerV1 {

    private final ApiResponse response;
    private final NotificationService notificationService;
    private final MemberService memberService;

    @Operation(summary = "나의 알림 조회", description = "알림 조회")
    @GetMapping
    public ResponseEntity<?> getAllNotification(@Parameter(hidden = true) Authentication authentication,
        @PageableDefault(page = 0, size = 10) Pageable page) {

        Member member = memberService.findMember(authentication.getName());
        Long myId = member.getId();

        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");

        NotificationGetDto notify1 = new NotificationGetDto(1L, member1, 3, null, null, 3L, 4L, 5L, null);
        NotificationGetDto notify2 = new NotificationGetDto(2L, member2, 9, null, null, 3L, 4L, null, null);
        NotificationGetDto notify3 = new NotificationGetDto(3L, member3, 12, null, null, null, null, null, null);
        NotificationGetDto notify4 = new NotificationGetDto(4L, member4, 9, 1L, "여행책", null, null, null, null);


        List<NotificationGetDto> list = new ArrayList<>();
        list.add(notify1);
        list.add(notify2);
        list.add(notify3);
        list.add(notify4);

        List<NotificationGetDto> result = notificationService.getAllNotification(myId, page);

        HashMap<String, Object> map = new HashMap<>();
        map.put("notificationList", result);
        map.put("page", page);

        return response.success(ResponseCode.NOTIFICATION_FETCHED.getMessage(), map);
    }

    @Operation(summary = "알림 단건 삭제", description = "알림 삭제 버튼 X 클릭했을 때")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable(name = "id") Long notifyId) {

        notificationService.deleteNotification(notifyId);
        return response.success(ResponseCode.NOTIFICATION_DELETED.getMessage());
    }

    @Operation(summary = "알림 전체 삭제", description = "알림 전체 삭제 클릭했을 때")
    @DeleteMapping
    public ResponseEntity<?> deleteAllNotification(@Parameter(hidden = true) Authentication authentication) {

        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();

        notificationService.deleteAllNotification(myId);
        return response.success(ResponseCode.NOTIFICATION_DELETED.getMessage());
    }

}
