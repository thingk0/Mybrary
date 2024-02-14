
package com.mybrary.backend.domain.mybrary.repository.custom;

import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import java.util.Optional;

public interface MybraryRepositoryCustom {

    Mybrary findByMybraryId(Long mybraryId);

    Optional<MybraryGetDto> getMybrary(Long myId);

    Optional<MybraryOtherGetDto> getOtherMybrary(Long otherId);

    Optional<Mybrary> findMybraryByEmail(String email);
}
