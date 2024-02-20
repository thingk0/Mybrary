package com.mybrary.backend.domain.category.service.impl;

import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.bookshelf.repository.BookShelfRepository;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryPostDto;
import com.mybrary.backend.domain.category.dto.requestDto.CategoryUpdateDto;
import com.mybrary.backend.domain.category.dto.responseDto.CategoryGetDto;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import com.mybrary.backend.domain.category.service.CategoryService;
import com.mybrary.backend.domain.follow.repository.FollowRepository;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.MybraryRepository;
import com.mybrary.backend.global.exception.bookshelf.BookshelfNotFoundException;
import com.mybrary.backend.global.exception.category.CategoryAccessDeniedException;
import com.mybrary.backend.global.exception.category.CategoryNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookShelfRepository bookShelfRepository;
    private final MybraryRepository mybraryRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PlatformTransactionManager transactionManager;

    @Override
    public List<CategoryGetDto> getAllCategory(String email, Long bookshelfId) {

        /* 카테고리 접근 권한 판단 */
        Long requesterId = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new).getId();
        Member owner = mybraryRepository.fetchMybraryByBookshelfId(bookshelfId).getMember();

        /**
         * 마이브러리의 주인이 아니고,
         * 해당 회원이 비공개 계정일 경우,
         */
        if ((!requesterId.equals(owner.getId())) && !owner.isProfilePublic()) {
            followRepository.findFollow(requesterId, owner.getId())
                            .orElseThrow(CategoryAccessDeniedException::new);
        }

        return categoryRepository.fetchCategoriesByBookshelfId(bookshelfId);
    }

    @Transactional
    @Override
    public Long createCategory(CategoryPostDto category) {
        Bookshelf bookshelf = bookShelfRepository.findById(category.getBookShelfId())
                                                 .orElseThrow(BookshelfNotFoundException::new);
        int seq = categoryRepository.nextSeq(category.getBookShelfId()).orElse(0);
        Category categoryEntity = Category.builder()
                                          .categoryName(category.getName())
                                          .bookshelf(bookshelf)
                                          .categorySeq(seq + 1)
                                          .build();
        Category savedCategory = categoryRepository.save(categoryEntity);
        return savedCategory.getId();
    }

    @Transactional
    @Override
    public void updateCategory(CategoryUpdateDto category) {
        for (int i = 0; i < category.getCategoryList().size(); i++) {
            CategoryGetDto c = category.getCategoryList().get(i);
            Long categoryId = c.getCategoryId();
            Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            categoryEntity.setCategoryName(c.getName());
            categoryEntity.setCategorySeq(i + 1);
        }
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public CompletableFuture<Mybrary> fetchMybraryOwnerAsync(Long bookshelfId) {
        return CompletableFuture.supplyAsync(() -> {
            Mybrary mybrary = mybraryRepository.fetchMybraryByBookshelfId(bookshelfId);
            return mybrary;
        });
    }


    @Transactional(readOnly = true)
    public CompletableFuture<Long> fetchMemberIdAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            Member member = memberRepository.searchByEmail(email).orElseThrow(MemberNotFoundException::new);
            return member.getId();
        });
    }

    private TransactionTemplate getTransactionTemplateReadOnly() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(true);
        return transactionTemplate;
    }

}
