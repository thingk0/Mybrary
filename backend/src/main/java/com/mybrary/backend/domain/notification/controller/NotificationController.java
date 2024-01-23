package com.mybrary.backend.domain.notification.controller;

import com.mybrary.backend.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Operation(summary = "나의 알림 조회", description = "알림 조회")
    @GetMapping("/notification")
    public ResponseEntity<?> getAllNotification() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "알림 삭제", description = "알림 삭제")
    @DeleteMapping("/notification")
    public ResponseEntity<?> deleteNotification() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
