package com.mybrary.backend.domain.book.dto;

import com.mybrary.backend.domain.member.dto.MemberInfoDto;
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
public class BookGetDto {

    /**
     *  책의 표지만 있는 DTO
     *  (내부 페이퍼에 대한 데이터는 없음)
     */

    private Long bookId;
    private MemberInfoDto writer;
    private String coverTitle;
    private String url;
    private int coverLayout;
    private int coverColorCode;
    private int bookMarkerIndex;



}
