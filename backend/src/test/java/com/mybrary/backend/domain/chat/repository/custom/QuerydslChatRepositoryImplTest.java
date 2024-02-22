package com.mybrary.backend.domain.chat.repository.custom;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.member.dto.requestDto.SignupRequestDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class QuerydslChatRepositoryImplTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        Member testMember = Member.of(
            new SignupRequestDto("user1@ssafy.com", "Ssafy123!@", "Ssafy123!@", "홍길동", "ssafy_123"),
            passwordEncoder.encode("Ssafy123!@"));
        memberRepository.save(testMember);

        Image.builder()
             .name("image")
            .url("originUrl")
//            .thumbnailUrl("thumbNailUrl")
//            .originName("originName")

             .build();


    }



}