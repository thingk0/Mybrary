package com.mybrary.backend.domain.member.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.chat.entity.ChatMessage;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.member.dto.ProfileUpdateDto;
import com.mybrary.backend.domain.member.dto.SignupRequestDto;
import com.mybrary.backend.domain.notification.entity.Notification;
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
    @Index(name = "unique_index_email", columnList = "email")
})
@Where(clause = "is_deleted <> true")
@SQLDelete(sql = "UPDATE member SET is_deleted = TRUE WHERE member_id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", length = 50, updatable = false, unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image profileImage;

    @Column(nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String intro;

    @Builder.Default()
    private boolean isProfilePublic = true;

    @Builder.Default()
    private boolean isNotifyEnabled = true;

    public static Member from(SignupRequestDto requestDto) {
        return Member.builder()
                     .email(requestDto.getEmail())
                     .password(requestDto.getPassword())
                     .name(requestDto.getName())
                     .nickname(requestDto.getNickname())
                     .intro(requestDto.getIntro())
                     .build();
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


    /**
     * 양방향 관계 - 팔로잉/팔로워, 알림 발신자/수신자, 채팅참여, 메시지, 좋아요
     */
    @OneToMany(mappedBy = "following")
    private List<Follow> followingList = new ArrayList<>();
    @OneToMany(mappedBy = "follower")
    private List<Follow> followerList = new ArrayList<>();
    @OneToMany(mappedBy = "sender")
    private List<Notification> sendList = new ArrayList<>();
    @OneToMany(mappedBy = "receiver")
    private List<Notification> receiveList = new ArrayList<>();
    @OneToMany(mappedBy = "joinMember")
    private List<ChatJoin> chatJoinList = new ArrayList<>();
    @OneToMany(mappedBy = "receiver")
    private List<ChatMessage> messageList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Like> likeList = new ArrayList<>();

}

