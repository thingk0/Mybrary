package com.mybrary.backend.domain.contents.thread.responseDto;

import com.mybrary.backend.domain.contents.paper.dto.HomePaperGetDto;
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
public class HomeThreadGetDto {

    /**
     *  홈에서 스레드 조회
     *  홈에서 페이퍼 조회 DTO를 포함
     */

    private Long threadId;
    private MemberInfoDto member;
    private boolean isOwner;
    private boolean isFollowed;
    private List<HomePaperGetDto> paperList;

}
