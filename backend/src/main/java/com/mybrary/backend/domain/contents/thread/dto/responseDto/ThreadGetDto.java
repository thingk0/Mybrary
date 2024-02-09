package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperGetDto;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    /* 스레드 설정정보 */
    private boolean isPaperPublic;
    private boolean isScrapEnable;

    private List<PaperGetDto> paperList;

    public void updateIsPublicEnable(boolean bool){
        this.isPaperPublic = bool;
    }
    public void updateIsScrapEnable(boolean bool){
        this.isScrapEnable = bool;
    }

}
