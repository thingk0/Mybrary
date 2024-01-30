package com.mybrary.backend.domain.member.controller;

import com.mybrary.backend.domain.member.dto.*;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.format.ApiResponse;
import com.mybrary.backend.global.format.ErrorCode;
import com.mybrary.backend.global.format.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member 컨트롤러", description = "Member Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberControllerV1 {

    private final ApiResponse response;
    private final MemberService memberService;

    @Operation(summary = "일반 회원가입", description = "일반 회원가입")
    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto member,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return response.fail(bindingResult);
        }

        Long savedId = memberService.create(member);
        return response.success(ResponseCode.MEMBER_SIGNUP_SUCCESS.getMessage(), savedId);

    }

    @Operation(summary = "소셜 회원가입", description = "소셜 회원가입")
    @PostMapping("/social")
    public ResponseEntity<?> signupBySocial(@Valid @RequestBody SignupRequestDto member,
                                            BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return response.fail(ErrorCode.MEMBER_SIGNUP_FAILED.getMessage(), bindingResult);
        }

        return response.success(ResponseCode.MEMBER_SIGNUP_SUCCESS.getMessage(), member.getName());
    }

    @Operation(summary = "이메일 인증 요청", description = "이메일 주소 보내고 인증코드를 메일로 보내는 요청")
    @PostMapping("/email/verification")
    public ResponseEntity<?> emailVerification(@RequestParam String email) {

        return response.success(ResponseCode.EMAIL_VERIFICATION_SENT.getMessage(), "123");
    }

    @Operation(summary = "인증코드 인증 요청", description = "메일로 받은 인증코드를 입력해서 인증 요청")
    @PostMapping("/email/verify")
    public ResponseEntity<?> emailVerify(@RequestParam String verificationCode) {

        return response.success(ResponseCode.EMAIL_VERIFIED_SUCCESS.getMessage(), verificationCode);
    }

    @Operation(summary = "닉네임 검사", description = "닉네임 유효성 및 중복 검사")
    @GetMapping("/nickname")
    public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {

        return response.success(ResponseCode.NICKNAME_CHECK_SUCCESS.getMessage());
    }

    @Operation(summary = "일반 로그인", description = "일반 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto member) {

        if(member.getEmail().equals("mybrary@ssafy.com")){
            return response.success(ResponseCode.LOGIN_SUCCESS.getMessage(), member.getEmail());
        }

        return response.fail(ErrorCode.MEMBER_PASSWORD_MISMATCH.getMessage());

    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인")
    @PostMapping("/login/social")
    public ResponseEntity<?> loginBySocial(@RequestBody LoginRequestDto member) {
        if(member.getEmail().equals("mybrary@ssafy.com")){
            return response.success(ResponseCode.SOCIAL_LOGIN_SUCCESS.getMessage(), member.getEmail());
        }

        return response.fail(ErrorCode.MEMBER_PASSWORD_MISMATCH.getMessage());
    }

    @Operation(summary = "비밀번호 재설정(비번찾기)", description = "비밀번호를 잃어버렸을 때 비밀번호 재설정")
    @PutMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordUpdateDto password) {

        return response.success(ResponseCode.PASSWORD_RESET_SUCCESS.getMessage());
    }

    @Operation(summary = "비밀번호 재설정(로그인후)", description = "로그인 후 비밀번호 재설정")
    @PutMapping("/password-update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDto password) {

        return response.success(ResponseCode.PASSWORD_RESET_SUCCESS.getMessage());
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return response.success(ResponseCode.LOGOUT_SUCCESS.getMessage());
    }

    @Operation(summary = "프로필 수정", description = "닉네임, 프로필이미지, 소개 수정")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody MemberUpdateDto member) {

        return response.success(ResponseCode.MEMBER_INFO_UPDATE_SUCCESS.getMessage(), member.getMemberId());
    }

    @Operation(summary = "나의 팔로잉 리스트", description = "나의 팔로잉 리스트")
    @GetMapping("/me/followings")
    public ResponseEntity<?> getAllMyFollowing() {

        List<MyFollowingDto> list = new ArrayList<>();
        list.add(new MyFollowingDto(1L, "준호", "wnsgh", "123123"));
        list.add(new MyFollowingDto(2L, "혜선", "gPtjs", "999999"));
        list.add(new MyFollowingDto(3L, "명성", "audtjd", "536839"));

        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), list);
    }

    @Operation(summary = "나의 팔로워 리스트", description = "나의 팔로워 리스트")
    @GetMapping("/me/followers")
    public ResponseEntity<?> getAllMyFollower() {
        List<MyFollowerDto> list = new ArrayList<>();
        list.add(new MyFollowerDto(1L, "준호", "wnsgh", "123123", true));
        list.add(new MyFollowerDto(2L, "혜선", "gPtjs", "999999", false));
        list.add(new MyFollowerDto(3L, "명성", "audtjd", "536839", true));

        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), list);
    }

    @Operation(summary = "특정회원의 팔로잉 리스트", description = "특정회원의 팔로잉 리스트")
    @GetMapping("/{id}/followings")
    public ResponseEntity<?> getAllFollowing(@PathVariable(name = "id") Long memberId) {
        List<FollowingDto> list = new ArrayList<>();
        list.add(new FollowingDto(1L, "준호", "wnsgh", "123123", true));
        list.add(new FollowingDto(2L, "혜선", "gPtjs", "999999", false));
        list.add(new FollowingDto(3L, "명성", "audtjd", "536839", true));

        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), list);
    }

    @Operation(summary = "특정회원의 팔로워 리스트", description = "특정회원의 팔로워 리스트")
    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getAllFollower(@PathVariable(name = "id") Long memberId) {
        List<FollowerDto> list = new ArrayList<>();
        list.add(new FollowerDto(1L, "준호", "wnsgh", "123123", true));
        list.add(new FollowerDto(2L, "혜선", "gPtjs", "999999", false));
        list.add(new FollowerDto(3L, "명성", "audtjd", "536839", true));

        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), list);
    }

    @Operation(summary = "팔로우하기", description = "특정회원을 팔로우하기")
    @PostMapping("/{id}/follow")
    public ResponseEntity<?> follow(@PathVariable(name = "id") Long memberId) {

        return response.success(ResponseCode.FOLLOW_SUCCESS.getMessage(), memberId);
    }

    @Operation(summary = "언팔로우하기", description = "특정회원을 언팔로우하기")
    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@PathVariable(name = "id") Long memberId) {

        return response.success(ResponseCode.UNFOLLOW_SUCCESS.getMessage(), memberId);
    }

    @Operation(summary = "팔로워끊기", description = "특정회원이 나를 팔로우한 것을 끊기")
    @DeleteMapping("/{id}/follower")
    public ResponseEntity<?> deleteFollower(@PathVariable(name = "id") Long memberId) {

        return response.success(ResponseCode.FOLLOWER_DELETE_SUCCESS.getMessage(), memberId);
    }

    @Operation(summary = "계정 탈퇴", description = "계정 탈퇴 (삭제처리)")
    @DeleteMapping("/secession")
    public ResponseEntity<?> secession(@RequestBody SecessionRequestDto secession) {

        return response.success(ResponseCode.ACCOUNT_SECESSION_SUCCESS.getMessage(), secession.getMemberId());
    }

    @Operation(summary = "계정 공개여부 설정", description = "계정 공개/비공개")
    @PutMapping("/privacy")
    public ResponseEntity<?> accountPrivacy() {

        return response.success(ResponseCode.PRIVACY_SETTING_UPDATED.getMessage());
    }

    @Operation(summary = "계정 알림 설정", description = "계정 알림 설정")
    @PutMapping("/notify")
    public ResponseEntity<?> accountNotify() {

        return response.success(ResponseCode.NOTIFICATION_SETTING_UPDATED.getMessage());
    }

}

