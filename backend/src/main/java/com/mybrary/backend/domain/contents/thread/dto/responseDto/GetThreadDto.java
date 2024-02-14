package com.mybrary.backend.domain.contents.thread.dto.responseDto;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.GetFollowingPaperDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetThreadDto {

    /* 쓰레드 관련 정보 */
    private Long threadId;

    private LocalDateTime threadCreatedAt;

    /* 작성자 member 관련 정보 */
    private Long memberId;
    private String memberName;
    private String nickname;
    private String profileUrl;
    private Long profileId;

    private boolean isFollowed = true;
    private boolean isPaperPublic;
    private boolean isScrapEnable;


    /* 해당 쓰레드에 포함된 paperId 목록, 이건 따로 조인해서 바인딩해야함 */
    private List<GetFollowingPaperDto> paperList;

    public GetThreadDto(Long threadId, LocalDateTime threadCreatedAt,
                        Long memberId, String memberName, String nickname, String profileUrl, Long profileId) {
        this.threadId = threadId;
        this.threadCreatedAt = threadCreatedAt;
        this.memberId = memberId;
        this.memberName = memberName;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.profileId = profileId;

    }

    public GetThreadDto(Long threadId, LocalDateTime threadCreatedAt, Long memberId, String memberName, String nickname,
                        String profileUrl, Long profileId, boolean isPaperPublic, boolean isScrapEnable) {
        this.threadId = threadId;
        this.threadCreatedAt = threadCreatedAt;
        this.memberId = memberId;
        this.memberName = memberName;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.profileId = profileId;
        this.isPaperPublic = isPaperPublic;
        this.isScrapEnable = isScrapEnable;
    }
}
