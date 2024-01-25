package com.mybrary.backend.domain.contents.thread.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperGetDto;
import com.mybrary.backend.domain.member.dto.MemberInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadGetDto {

    /**
     *  내 스레드 조회
     *  내 페이퍼 조회 DTO를 포함
     */

    private Long threadId;
    private MemberInfoDto member;
    private List<PaperGetDto> paperList;

}
