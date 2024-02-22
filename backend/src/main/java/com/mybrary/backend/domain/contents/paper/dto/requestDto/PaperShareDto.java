package com.mybrary.backend.domain.contents.paper.dto.requestDto;

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
public class PaperShareDto {

    /**
     *  페이퍼 pipi에 공유 요청
     *  여러명 선택해서 공유할 수 있으니 멤버아이디 리스트 포함
     */

    private Long chatRoomId;
    private Long receiverId;
    private Long threadId;

}
