package com.mybrary.backend.domain.chat.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE chat_room SET is_deleted = TRUE WHERE chat_room_id = ?")
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatJoin> chatJoinList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessageList = new ArrayList<>();

}
