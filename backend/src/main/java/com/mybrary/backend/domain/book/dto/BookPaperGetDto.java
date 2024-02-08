package com.mybrary.backend.domain.book.dto;

import com.mybrary.backend.domain.contents.paper.dto.PaperGetDto;
import com.mybrary.backend.domain.contents.paper.dto.PaperInBookGetDto;
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
public class BookPaperGetDto {

    /**
     *  책의 표지만 있는 DTO
     *  (내부 페이퍼에 대한 데이터는 없음)
     */

    private Long bookId;
    private List<PaperInBookGetDto> paperList;



}
