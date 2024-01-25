package com.mybrary.backend.domain.notification.controller;

import com.mybrary.backend.domain.notification.service.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification 컨트롤러", description = "Notification Controller API")
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationControllerV1 {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Operation(summary = "나의 알림 조회", description = "알림 조회")
    @GetMapping
    public ResponseEntity<?> getAllNotification(
        @PageableDefault(page = 0, size = 10) Pageable page) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "알림 삭제", description = "알림 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable(name = "id") Long notifyId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
