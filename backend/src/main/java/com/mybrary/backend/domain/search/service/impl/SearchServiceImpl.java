package com.mybrary.backend.domain.search.service.impl;

import com.mybrary.backend.domain.contents.thread.dto.ThreadSimpleGetDto;
import com.mybrary.backend.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {


    @Override
    public List<ThreadSimpleGetDto> searchThread(String keyword, Pageable page) {

        // 1. keyword가 포함되어있는 태그 리스트 조회 (페이퍼Id가 중복되면 하나만 가져오기)

        // 2. 태그가 포함된 페이퍼가 포함되어있는 스레드 리스트 조회 (스레드Id가 중복되면 하나만 가져오기)
        //    근데 이때 (좋아요수+스크랩수*5)가 제일 큰 페이퍼Id를 갖고 있어야함

        // 3. (좋아요수+스크랩수*5) 순으로 정렬해서 페이징



        return null;
    }
}
