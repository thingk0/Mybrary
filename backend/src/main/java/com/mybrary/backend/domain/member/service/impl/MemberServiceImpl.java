package com.mybrary.backend.domain.member.service.impl;

import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.bookshelf.repository.BookShelfRepository;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.dto.FollowerDto;
import com.mybrary.backend.domain.member.dto.FollowingDto;
import com.mybrary.backend.domain.member.dto.LoginRequestDto;
import com.mybrary.backend.domain.member.dto.MemberUpdateDto;
import com.mybrary.backend.domain.member.dto.MyFollowerDto;
import com.mybrary.backend.domain.member.dto.MyFollowingDto;
import com.mybrary.backend.domain.member.dto.PasswordUpdateDto;
import com.mybrary.backend.domain.member.dto.SecessionRequestDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.member.dto.login.LoginResponseDto;
import com.mybrary.backend.domain.member.dto.login.MemberInfo;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.notification.dto.NotificationPostDto;
import com.mybrary.backend.domain.notification.entity.Notification;
import com.mybrary.backend.domain.notification.repository.NotificationRepository;
import com.mybrary.backend.domain.notification.service.NotificationService;
import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import com.mybrary.backend.domain.rollingpaper.repository.RollingPaperRepository;
import com.mybrary.backend.global.exception.ImageNotFoundException;
import com.mybrary.backend.global.exception.member.DuplicateEmailException;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import com.mybrary.backend.global.exception.member.InvalidLoginAttemptException;
import com.mybrary.backend.global.exception.member.PasswordMismatchException;
import com.mybrary.backend.global.exception.member.ProfileUpdateException;
import com.mybrary.backend.global.jwt.TokenInfo;
import com.mybrary.backend.global.jwt.provider.TokenProvider;
import com.mybrary.backend.global.jwt.repository.RefreshTokenRepository;
import com.mybrary.backend.global.jwt.service.TokenService;
import com.mybrary.backend.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final CookieUtil cookieUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MybraryRepository mybraryRepository;
    private final BookShelfRepository bookShelfRepository;
    private final CategoryRepository categoryRepository;
    private final RollingPaperRepository rollingPaperRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public Long create(SignupRequestDto requestDto) {

        /* 비밀번호 불일치 */
        checkPasswordConfirmation(requestDto.getPassword(), requestDto.getPasswordConfirm());

        /* 이메일 중복 검증 */
        memberRepository.searchByEmail(requestDto.getEmail())
                        .ifPresent(this::throwDuplicateEmailException);

        /* 회원 생성 */
        Member member = Member.of(requestDto, passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);

        /* 마이브러리 생성 */
        Mybrary mybrary = Mybrary.builder()
                                 .member(member)
                                 .backgroundColor(1)
                                 .deskColor(1)
                                 .bookshelfColor(1)
                                 .deskColor(1)
                                 .build();
        mybraryRepository.save(mybrary);

        /* 책장 생성 */
        Bookshelf bookshelf = Bookshelf.builder()
                                       .mybrary(mybrary)
                                       .build();
        bookShelfRepository.save(bookshelf);

        /* 기본 카테고리 3개 생성 */
        for (int i = 1; i <= 3; i++) {
            Category category = Category.builder()
                                        .bookshelf(bookshelf)
                                        .categoryName("기본" + i)
                                        .categorySeq(i)
                                        .build();
            categoryRepository.save(category);
        }

        /* 롤링페이퍼 생성 */
        RollingPaper rollingPaper = RollingPaper.builder()
                                                .mybrary(mybrary)
                                                .build();
        // 이건 롤링페이퍼 이미지 바로 참조해야할 것 같다
        // 수정 꼭 하자
        rollingPaperRepository.save(rollingPaper);

        return member.getId();
    }

    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        log.info("event=LoginAttempt, email={}", requestDto.getEmail());

        Member member = searchMemberByEmail(requestDto.getEmail());
        isPasswordMatchingWithEncoded(requestDto.getPassword(), member.getPassword());
        removeOldRefreshToken(requestDto, member);

        TokenInfo tokenInfo = tokenProvider.generateTokenInfo(member.getEmail());
        tokenService.saveToken(tokenInfo);
        cookieUtil.addCookie("RefreshToken", tokenInfo.getRefreshToken(), tokenProvider.getREFRESH_TOKEN_TIME(), response);

        return LoginResponseDto.builder()
                               .token(tokenInfo.getAccessToken())
                               .memberInfo(MemberInfo.builder()
                                                     .memberId(member.getId())
                                                     .email(member.getEmail())
                                                     .nickname(member.getNickname())
                                                     .profileImageUrl(member.getProfileImage() == null ? ""
                                                                          : member.getProfileImage().getUrl())
                                                     .build())
                               .build();
    }

    @Override
    public Member findMember(String email) {
        return memberRepository.searchByEmail(email).orElseThrow(EmailNotFoundException::new);
    }


    @Override
    public List<MyFollowingDto> getAllMyFollowing(Long myId) {
        List<Member> myFollowing = memberRepository.getAllFollowing(myId);

        List<MyFollowingDto> list = new ArrayList<>();
        for (Member member : myFollowing) {
            list.add(new MyFollowingDto(member.getId(), member.getName(), member.getNickname(), null));
        }

        return list;
    }

    @Override
    public List<MyFollowerDto> getAllMyFollower(Long myId) {

        List<Member> myFollower = memberRepository.getAllFollower(myId);

        List<MyFollowerDto> list = new ArrayList<>();
        for (Member member : myFollower) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new MyFollowerDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;
    }

    @Override
    public List<FollowingDto> getAllFollowing(Long myId, Long memberId) {

        List<Member> Following = memberRepository.getAllFollowing(memberId);

        List<FollowingDto> list = new ArrayList<>();
        for (Member member : Following) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new FollowingDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;

    }

    @Override
    public List<FollowerDto> getAllFollower(Long myId, Long memberId) {
        List<Member> myFollower = memberRepository.getAllFollower(memberId);

        List<FollowerDto> list = new ArrayList<>();
        for (Member member : myFollower) {
            Follow follow = memberRepository.isFollowed(myId, member.getId());
            boolean isFollowed = false;
            if (follow != null) {
                isFollowed = true;
            }
            list.add(new FollowerDto(member.getId(), member.getName(), member.getNickname(), null, isFollowed));
        }

        return list;
    }

    @Transactional
    @Override
    public void follow(String email, Long memberId, boolean accept) {

        Member me = findMember(email);
        Member you = memberRepository.findById(memberId).get();

        // 팔로우를 하려는 상대방의 계정이 공개일 때 -> 바로 팔로우
        // or 요청을 수락했을 때 -> 팔로우
        if (you.isProfilePublic() || accept) {
            Follow follow = Follow.builder()
                                  .following(you)
                                  .follower(me)
                                  .build();
            followRepository.save(follow);
        } else {

            /*    웹소켓 코드    */

            // 비공개일 때 -> 팔로우 요청을 보내기
            // 팔로우 요청이 곧 알림이어서 type1 알림 보내기
            NotificationPostDto notification = NotificationPostDto.builder()
                                                                  .senderId(me.getId())
                                                                  .receiverId(you.getId())
                                                                  .notifyType(1)
                                                                  .build();
            notificationService.saveNotification(notification);

        }

        /*    웹소켓 코드    */

        // 공개여서 바로 팔로우 했을 때 type2 알림 보내기
        if (you.isProfilePublic()) {
            NotificationPostDto notification = NotificationPostDto.builder()
                                                                  .senderId(me.getId())
                                                                  .receiverId(you.getId())
                                                                  .notifyType(2)
                                                                  .build();
            notificationService.saveNotification(notification);
        }

    }

    @Transactional
    @Override
    public void unfollow(Long myId, Long memberId) {
        Follow follow = followRepository.findFollow(myId, memberId);
        followRepository.delete(follow);
    }

    @Transactional
    @Override
    public void deleteFollower(Long myId, Long memberId) {
        Follow follow = followRepository.findFollow(memberId, myId);
        followRepository.delete(follow);
    }

    @Transactional
    @Override
    public void followCancel(String email, Long memberId) {

        Member me = findMember(email);
        Notification notification = notificationRepository.getForFollowCancel(me.getId(), memberId);
        notificationRepository.delete(notification);

    }

    @Transactional
    @Override
    public void updateProfile(String email, MemberUpdateDto member) {

        Member me = findMember(email);

        if(!me.getId().equals(member.getMemberId())){
            throw new ProfileUpdateException();
        }

        Image image = imageRepository.findById(member.getProfileImageId()).orElseThrow(ImageNotFoundException::new);

        me.uploadProfileImage(image);
        me.updateNickname(member.getNickname());
        me.updateIntro(member.getIntro());
        me.updateIsProfilePublic(member.isProfilePublic());
        me.updateIsNotifyEnable(member.isNotifyEnable());

    }

    @Transactional
    @Override
    public void updatePassword(String email, PasswordUpdateDto password) {

        /* 비밀번호 불일치 */
        if (!password.getPassword().equals(password.getPasswordConfirm())) {
            throw new PasswordMismatchException();
        }

        Member me = findMember(email);
        me.updatePassword(passwordEncoder.encode(password.getPassword()));

    }

    @Transactional
    @Override
    public void secession(SecessionRequestDto secession) {

        Member member = memberRepository.searchByEmail(secession.getEmail())
                                        .orElseThrow(InvalidLoginAttemptException::new);

        isPasswordMatchingWithEncoded(secession.getPassword(), member.getPassword());
        memberRepository.delete(member);

    }

    @Override
    public boolean checkNicknameDuplication(String nickname) {
        return memberRepository.isNicknameDuplicate(nickname);
    }

    @Override
    @Transactional
    public String logout(String email, HttpServletResponse servletResponse) {
        cookieUtil.removeCookie("RefreshToken", servletResponse);
        refreshTokenRepository.findById(email)
                              .ifPresent(refreshTokenRepository::delete);
        return email;
    }

    private Member searchMemberByEmail(String email) {
        Member member = memberRepository.searchByEmail(email)
                                        .orElseThrow(EmailNotFoundException::new);
        log.info("event=MemberSearchByEmail, email={}", email);
        return member;
    }

    private void removeOldRefreshToken(LoginRequestDto requestDto, Member member) {
        refreshTokenRepository.findById(member.getEmail())
                              .ifPresent(refreshTokenRepository::delete);
        log.info("event=DeleteExistingRefreshToken, email={}", requestDto.getEmail());
    }

    private void throwDuplicateEmailException(Member member) {
        throw new DuplicateEmailException();
    }

    private void isPasswordMatchingWithEncoded(String input, String encoded) {
        if (!passwordEncoder.matches(input, encoded)) {
            throw new InvalidLoginAttemptException();
        }
    }

    private void checkPasswordConfirmation(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new PasswordMismatchException();
        }
    }
}
