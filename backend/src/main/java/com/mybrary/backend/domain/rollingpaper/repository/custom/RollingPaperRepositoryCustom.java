
package com.mybrary.backend.domain.rollingpaper.repository.custom;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import java.util.Optional;
public interface RollingPaperRepositoryCustom {

    Optional<Member> findOwner(Long rollingPaperId);
}
