package com.mybrary.backend.domain.contents.tag.service;

import com.mybrary.backend.domain.contents.tag.entity.Tag;
import com.mybrary.backend.domain.contents.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    public List<String> getTagNameList(Long paperId) {
        Optional<List<Tag>> optionalTagList = tagRepository.getTagsByPaperId(paperId);
        List<Tag> tagList;
        // tag 값이 있으면 반환, null이라면 빈 객체 반환
        tagList = optionalTagList.orElseGet(ArrayList::new);

        // Tag의 tagName 필드를 String 리스트로 변환
        List<String> tagNames = tagList.stream()  // Stream<Tag> 생성
            .map(Tag::getTagName)    // Stream<String>으로 변환
            .toList();     // 결과를 List<String>로 변환

        return tagNames;
    }
}
