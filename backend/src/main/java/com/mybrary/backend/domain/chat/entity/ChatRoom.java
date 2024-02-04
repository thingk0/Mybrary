package com.mybrary.backend.domain.chat.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    // ChatJoin
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatJoin> chatJoinList;

    // ChatMessage
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessageList;

}
