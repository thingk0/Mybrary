
package com.mybrary.backend.domain.mybrary.service;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
public interface MybraryService {
    MybraryGetDto getMybrary(String email);
    MybraryOtherGetDto getOtherMybrary(String myEmail, Long memberId);
    void updateMybrary(String email, MybraryUpdateDto mybrary);
}
