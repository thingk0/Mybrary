package com.mybrary.backend.domain.book.dto.responseDto;

import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookForMainThreadDto {

    /**
     *   메인피드 스레드 조회에서 페이퍼가 포함된 책 목록
     *
     */

    private Long bookId;
    private String title;

}
