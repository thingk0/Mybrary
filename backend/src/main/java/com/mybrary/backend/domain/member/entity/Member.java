package com.mybrary.backend.domain.member.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.comment.entity.Comment;
import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.notification.entity.Notification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member", indexes = {
    @Index(name = "unique_index_email", columnList = "email"),
    @Index(name = "unique_index_nickname", columnList = "nickname")
})
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = TRUE WHERE member_id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", length = 50, updatable = false, unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "image_id")
    private Image profileImage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "intro", length = 50)
    private String intro;

    @Builder.Default()
    @Column(name = "is_profile_public")
    private boolean isProfilePublic = true;

    @Builder.Default()
    @Column(name = "is_notify_enabled")
    private boolean isNotifyEnabled = true;

    public static Member of(SignupRequestDto requestDto, String encodedPassword) {
        return Member.builder()
                     .email(requestDto.getEmail())
                     .password(encodedPassword)
                     .name(requestDto.getName())
                     .nickname(requestDto.getNickname())
                     .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void uploadProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateIntro(String intro) {
        this.intro = intro;
    }

    public void updateIsProfilePublic(boolean isProfilePublic) {
        this.isProfilePublic = isProfilePublic;
    }

    public void updateIsNotifyEnable(boolean isNotifyEnabled) {
        this.isNotifyEnabled = isNotifyEnabled;
    }


    /**
     * 양방향 관계 - 팔로잉/팔로워, 알림 발신자/수신자, 채팅참여, 메시지, 좋아요
     */
    @OneToMany(mappedBy = "following", cascade = {CascadeType.REMOVE})
    private List<Follow> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = {CascadeType.REMOVE})
    private List<Follow> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.REMOVE})
    private List<Notification> sendList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = {CascadeType.REMOVE})
    private List<Notification> receiveList = new ArrayList<>();

    @OneToMany(mappedBy = "joinMember", cascade = {CascadeType.REMOVE})
    private List<ChatJoin> chatJoinList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = {CascadeType.REMOVE})
    private List<ChatMessage> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE})
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE})
    private List<Paper> paperList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.REMOVE})
    private Mybrary mybrary;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE})
    private List<Book> bookList = new ArrayList<>();

}

