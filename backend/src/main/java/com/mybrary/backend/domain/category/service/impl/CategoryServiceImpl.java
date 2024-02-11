package com.mybrary.backend.domain.category.service.impl;

import com.mybrary.backend.domain.book.service.impl.BookServiceImpl;
import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.bookshelf.repository.BookShelfRepository;
import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import com.mybrary.backend.domain.category.service.CategoryService;
import com.mybrary.backend.domain.follow.entity.Follow;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.global.exception.book.BookAlreadySubscribeException;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
import com.mybrary.backend.global.exception.bookshelf.BookshelfNotFoundException;
import com.mybrary.backend.global.exception.category.CategoryAccessDeniedException;
import com.mybrary.backend.global.exception.category.CategoryNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.exception.mybrary.MybraryNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookShelfRepository bookShelfRepository;
    private final MybraryRepository mybraryRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Override
    public List<CategoryGetDto> getAllCategory(String email, Long bookshelfId) {

        /* 카테고리 접근 권한 판단 */
        Long myId = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new).getId();
        Bookshelf bookshelf = bookShelfRepository.findById(bookshelfId).orElseThrow(BookshelfNotFoundException::new);
        Mybrary mybrary = mybraryRepository.findById(bookshelf.getMybrary().getId()).orElseThrow(MybraryNotFoundException::new);
        Member Owner = memberRepository.findById(mybrary.getMember().getId()).orElseThrow(MemberNotFoundException::new);
        if(!Owner.isProfilePublic()){
            Follow follow = followRepository.findFollow(myId, Owner.getId()).orElseThrow(CategoryAccessDeniedException::new);
        }

        return categoryRepository.getAllCategory(bookshelfId).orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    @Override
    public Long createCategory(CategoryPostDto category) {
        Bookshelf bookshelf = bookShelfRepository.findById(category.getBookShelfId()).orElseThrow(BookshelfNotFoundException::new);
        int seq = categoryRepository.nextSeq(category.getBookShelfId()).orElse(0);
        Category categoryEntity = Category.builder()
            .categoryName(category.getName())
            .bookshelf(bookshelf)
            .categorySeq(seq+1)
            .build();
        Category savedCategory = categoryRepository.save(categoryEntity);
        return savedCategory.getId();
    }

    @Transactional
    @Override
    public void updateCategory(CategoryUpdateDto category) {
        for (int i = 0;i<category.getCategoryList().size();i++) {
            CategoryGetDto c = category.getCategoryList().get(i);

            Long categoryId = c.getCategoryId();
            Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            categoryEntity.setCategoryName(c.getName());
            categoryEntity.setCategorySeq(i+1);
        }
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }
}
