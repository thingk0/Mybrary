package com.mybrary.backend.domain.member.controller;

import com.mybrary.backend.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member 컨트롤러", description = "Member Controller API")
@RestController
public class MemberController {

    @Operation(summary = "일반 회원가입", description = "일반 회원가입")
    @PostMapping("/member")
    public ResponseEntity<?> singup(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "소셜 회원가입", description = "소셜 회원가입")
    @PostMapping("/member/social")
    public ResponseEntity<?> singupBySocial(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일 인증 요청", description = "이메일 주소 보내고 인증코드를 메일로 보내는 요청")
    @PostMapping("/member/email/verification")
    public ResponseEntity<?> emailVerification(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "인증코드 인증 요청", description = "메일로 받은 인증코드를 입력해서 인증 요청")
    @PostMapping("/member/email/verify")
    public ResponseEntity<?> emailVerify(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "닉네임 검사", description = "닉네임 유효성 및 중복 검사")
    @GetMapping("/member/nickname")
    public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {
        return new ResponseEntity<String>(nickname, HttpStatus.OK);
    }

    @Operation(summary = "일반 로그인", description = "일반 로그인")
    @GetMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인")
    @GetMapping("/member/login/social")
    public ResponseEntity<?> loginBySocial(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 재설정(비번찾기)", description = "비밀번호를 잃어버렸을 때 비밀번호 재설정")
    @PutMapping("/member/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 재설정(로그인후)", description = "로그인 후 비밀번호 재설정")
    @PutMapping("/member/password-update")
    public ResponseEntity<?> updatePassword(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @PostMapping("/member/logout")
    public ResponseEntity<?> logout(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "프로필 수정", description = "닉네임, 프로필이미지, 소개 수정")
    @PutMapping("/member/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "나의 팔로잉 리스트", description = "나의 팔로잉 리스트")
    @GetMapping("/member/me/followings")
    public ResponseEntity<?> getAllMyFollowing(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "나의 팔로워 리스트", description = "나의 팔로워 리스트")
    @GetMapping("/member/me/followers")
    public ResponseEntity<?> getAllMyFollower(@RequestBody Member member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "특정회원의 팔로잉 리스트", description = "특정회원의 팔로잉 리스트")
    @GetMapping("/member/{memberId}/followings")
    public ResponseEntity<?> getAllFollowing(@PathVariable Long memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "특정회원의 팔로워 리스트", description = "특정회원의 팔로워 리스트")
    @GetMapping("/member/{memberId}/followers")
    public ResponseEntity<?> getAllFollower(@PathVariable Long memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "팔로우하기", description = "특정회원을 팔로우하기")
    @PostMapping("/member/{memberId}/follow")
    public ResponseEntity<?> follow(@PathVariable Long memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "언팔로우하기", description = "특정회원을 언팔로우하기")
    @DeleteMapping("/member/{memberId}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable Long memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "팔로워끊기", description = "특정회원이 나를 팔로우한 것을 끊기")
    @DeleteMapping("/member/{memberId}/follower")
    public ResponseEntity<?> deleteFollower(@PathVariable Long memberId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "계정 비활성화", description = "계정 비활성화 (삭제처리)")
    @DeleteMapping("/member/deactivate")
    public ResponseEntity<?> deactivate() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "계정 공개여부 설정", description = "계정 공개/비공개")
    @PutMapping("/member/privacy")
    public ResponseEntity<?> accountPrivacy() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "계정 알림 설정", description = "계정 알림 설정")
    @PutMapping("/member/notify")
    public ResponseEntity<?> accountNotify() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
