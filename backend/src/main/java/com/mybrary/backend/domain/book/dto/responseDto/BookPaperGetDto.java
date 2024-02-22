package com.mybrary.backend.domain.book.dto.responseDto;

import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperInBookGetDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPaperGetDto {

    /**
     *  책의 표지만 있는 DTO
     *  (내부 페이퍼에 대한 데이터는 없음)
     */

    private Long bookId;
    private boolean isOwner;   //  해당 책을 내가 이미 구독중인지 여부
    private boolean isPicked;   //  해당 책을 내가 이미 구독중인지 여부

    private List<PaperInBookGetDto> paperList;



}
