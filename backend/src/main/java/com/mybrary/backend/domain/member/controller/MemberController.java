package com.mybrary.backend.domain.member.controller;

import com.mybrary.backend.domain.member.dto.FollowerDto;
import com.mybrary.backend.domain.member.dto.FollowingDto;
import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.MemberUpdateDto;
import com.mybrary.backend.domain.member.dto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.MyFollowingDto;
import com.mybrary.backend.domain.member.dto.PasswordUpdateDto;
import com.mybrary.backend.domain.member.dto.SecessionRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.dto.email.EmailCheckRequestDto;
import com.mybrary.backend.domain.member.dto.email.EmailValidationRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MailService;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.annotation.AccessToken;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import com.mybrary.backend.global.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Member 컨트롤러", description = "Member Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final ApiResponse response;
    private final TokenService tokenService;
    private final MailService mailService;
    private final MemberService memberService;

    @Operation(summary = "토큰 갱신", description = "리프레쉬 토큰을 통해 액세스 토큰 재발급 요청")
    @PutMapping
    public ResponseEntity<?> token(@AccessToken @RequestBody String accessToken) {
        return response.success(tokenService.reIssueAccessToken(accessToken));
    }

    @Operation(summary = "일반 회원가입", description = "일반 회원가입")
    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return response.fail(bindingResult);
        }

        Long savedId = memberService.create(requestDto);
        return response.success(ResponseCode.MEMBER_SIGNUP_SUCCESS.getMessage(), savedId);
    }

    @Operation(summary = "소셜 회원가입", description = "소셜 회원가입")
    @PostMapping("/social")
    public ResponseEntity<?> signupBySocial(@Valid @RequestBody SignupRequestDto requestDto,
                                            BindingResult bindingResult) {
        return response.success(ResponseCode.MEMBER_SIGNUP_SUCCESS.getMessage());
    }

    @Operation(summary = "이메일 인증 요청", description = "이메일 주소 보내고 인증코드를 메일로 보내는 요청")
    @PostMapping("/email/verification")
    public ResponseEntity<?> emailVerification(@Valid @RequestBody EmailValidationRequestDto requestDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return response.fail(bindingResult);
        }

        mailService.sendEmailVerification(requestDto.getEmail());
        return response.success(ResponseCode.EMAIL_VERIFICATION_SENT.getMessage());
    }

    @Operation(summary = "인증코드 인증 요청", description = "메일로 받은 인증코드를 입력해서 인증 요청")
    @PostMapping("/email/verify")
    public ResponseEntity<?> emailVerify(@Valid @RequestBody EmailCheckRequestDto requestDto,
                                         BindingResult bindingResult,
                                         HttpServletResponse servletResponse) {

        if (bindingResult.hasErrors()) {
            return response.fail(bindingResult);
        }

        return response.success(ResponseCode.EMAIL_VERIFIED_SUCCESS.getMessage(),
                                mailService.confirmAuthCode(requestDto.getEmail(), requestDto.getAuthNum(), servletResponse));
    }

    @Operation(summary = "닉네임 검사", description = "닉네임 유효성 및 중복 검사")
    @GetMapping("/nickname")
    public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "일반 로그인", description = "일반 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto,
                                   BindingResult bindingResult,
                                   HttpServletResponse httpServletResponse) {

        if (bindingResult.hasErrors()) {
            return response.fail(bindingResult);
        }

        return response.success(ResponseCode.LOGIN_SUCCESS.getMessage(), memberService.login(requestDto, httpServletResponse));
    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인")
    @PostMapping("/login/social")
    public ResponseEntity<?> loginBySocial(@RequestBody LoginRequestDto member) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 재설정(비번찾기)", description = "비밀번호를 잃어버렸을 때 비밀번호 재설정")
    @PutMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordUpdateDto password) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "나의 팔로잉 리스트", description = "나의 팔로잉 리스트")
    @GetMapping("/me/followings")
    public ResponseEntity<?> getAllMyFollowing(@Parameter(hidden = true) Authentication authentication) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        List<MyFollowingDto> result = memberService.getAllMyFollowing(myId);
        return response.success(ResponseCode.FOLLOWINGS_FETCH_SUCCESS.getMessage(), result);
    }

    @Operation(summary = "나의 팔로워 리스트", description = "나의 팔로워 리스트")
    @GetMapping("/me/followers")
    public ResponseEntity<?> getAllMyFollower(@Parameter(hidden = true) Authentication authentication) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        List<MyFollowerDto> result = memberService.getAllMyFollower(myId);
        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), result);
    }

    @Operation(summary = "특정회원의 팔로잉 리스트", description = "특정회원의 팔로잉 리스트")
    @GetMapping("/{id}/followings")
    public ResponseEntity<?> getAllFollowing(@Parameter(hidden = true) Authentication authentication,
                                             @PathVariable(name = "id") Long memberId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        List<FollowingDto> result = memberService.getAllFollowing(myId, memberId);
        return response.success(ResponseCode.FOLLOWINGS_FETCH_SUCCESS.getMessage(), result);
    }

    @Operation(summary = "특정회원의 팔로워 리스트", description = "특정회원의 팔로워 리스트")
    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getAllFollower(@Parameter(hidden = true) Authentication authentication,
                                            @PathVariable(name = "id") Long memberId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        List<FollowerDto> result = memberService.getAllFollower(myId, memberId);
        return response.success(ResponseCode.FOLLOWERS_FETCH_SUCCESS.getMessage(), result);
    }

    @Operation(summary = "팔로우하기", description = "특정회원을 팔로우하기")
    @PostMapping("/{id}/follow")
    public ResponseEntity<?> follow(@Parameter(hidden = true) Authentication authentication, @PathVariable(name = "id") Long memberId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        memberService.follow(myId, memberId);
        return response.success(ResponseCode.FOLLOW_SUCCESS.getMessage());
    }

    @Operation(summary = "언팔로우하기", description = "특정회원을 언팔로우하기")
    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollow(@Parameter(hidden = true) Authentication authentication, @PathVariable(name = "id") Long memberId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        memberService.unfollow(myId, memberId);
        return response.success(ResponseCode.UNFOLLOW_SUCCESS.getMessage());
    }

    @Operation(summary = "팔로워끊기", description = "특정회원이 나를 팔로우한 것을 끊기")
    @DeleteMapping("/{id}/follower")
    public ResponseEntity<?> deleteFollower(@Parameter(hidden = true) Authentication authentication, @PathVariable(name = "id") Long memberId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        memberService.deleteFollower(myId, memberId);
        return response.success(ResponseCode.FOLLOWER_DELETE_SUCCESS.getMessage());
    }

    /* 설정페이지에 필요한 API */

    @Operation(summary = "회원 정보 수정", description = "닉네임, 프로필이미지, 소개,  수정")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Parameter(hidden = true) Authentication authentication, @RequestBody MemberUpdateDto member, @RequestParam
    MultipartFile multipartFile) {
        memberService.updateProfile(member);
        return response.success(ResponseCode.MEMBER_INFO_UPDATE_SUCCESS.getMessage());
    }

    @Operation(summary = "비밀번호 재설정(로그인후)", description = "로그인 후 비밀번호 재설정")
    @PutMapping("/password-update")
    public ResponseEntity<?> updatePassword(@Parameter(hidden = true) Authentication authentication, @RequestBody PasswordUpdateDto password) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        memberService.updatePassword(myId, password);
        return response.success(ResponseCode.PASSWORD_UPDATE_SUCCESS.getMessage());
    }

    @Operation(summary = "계정 탈퇴", description = "계정 탈퇴 (삭제처리)")
    @DeleteMapping("/secession")
    public ResponseEntity<?> secession(@Parameter(hidden = true) Authentication authentication, @RequestBody SecessionRequestDto secession) {
        memberService.secession(secession);
        return response.success(ResponseCode.ACCOUNT_SECESSION_SUCCESS.getMessage());
    }

}

