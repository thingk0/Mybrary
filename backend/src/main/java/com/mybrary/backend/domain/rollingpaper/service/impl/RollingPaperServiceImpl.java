
package com.mybrary.backend.domain.rollingpaper.service.impl;

import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.contents.thread.repository.ThreadRepository;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryUpdateDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperGetDto;
import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperPostDto;
import com.mybrary.backend.domain.rollingpaper.entity.RollingPaper;
import com.mybrary.backend.domain.rollingpaper.repository.RollingPaperRepository;
import com.mybrary.backend.domain.rollingpaper.service.RollingPaperService;
import com.mybrary.backend.global.exception.RollingPaperNotFoundException;
import com.mybrary.backend.global.exception.image.ImageNotFoundException;
import com.mybrary.backend.global.exception.member.FollowNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.exception.mybrary.MybraryAccessDeniedException;
import com.mybrary.backend.global.exception.mybrary.MybraryNotFoundException;
import com.mybrary.backend.global.exception.mybrary.NotMybraryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@RequiredArgsConstructor
@Service
public class RollingPaperServiceImpl implements RollingPaperService {

    private final RollingPaperRepository rollingPaperRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Override
    public RollingPaperGetDto getRollingPaper(String email, Long rollingPaperId) {

        Member member = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
        RollingPaper rollingPaper = rollingPaperRepository.findById(rollingPaperId).orElseThrow(RollingPaperNotFoundException::new);
        Member owner = rollingPaperRepository.findOwner(rollingPaperId).orElseThrow(MemberNotFoundException::new);
        if(!owner.isProfilePublic()){
            Follow follow = followRepository.findFollow(member.getId(), owner.getId()).orElseThrow(FollowNotFoundException::new);
        }
        RollingPaperGetDto res = RollingPaperGetDto.builder()
            .rollingPaperId(rollingPaperId)
            .rollingPaperString(rollingPaper.getRollingPaperString())
            .build();
        return res;
    }

    @Override
    public Long saveRollingPaper(String email, RollingPaperPostDto rollingPaper) {

        RollingPaper beforeRollingPaper = rollingPaperRepository.findById(rollingPaper.getRollingPaperId()).orElseThrow(RollingPaperNotFoundException::new);
        beforeRollingPaper.setRollingPaperString(rollingPaper.getRollingPaperString());
        return rollingPaper.getRollingPaperId();

    }
}
