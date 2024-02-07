package com.mybrary.backend.domain.book.service.impl;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import com.mybrary.backend.domain.book.dto.MyBookGetDto;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.book.service.BookService;
import com.mybrary.backend.domain.category.dto.MyCategoryGetDto;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<MyCategoryGetDto> getMyBookList(Long memberId) {
        /* 카테고리 관련 먼저 받아오기 */
        List<MyCategoryGetDto> myCategoryGetDtoList = categoryRepository.getMyAllCategory(memberId)
                                                                        .orElse(new ArrayList<>());
        if(!myCategoryGetDtoList.isEmpty()){
            for(MyCategoryGetDto myCategoryGetDto : myCategoryGetDtoList){
                /* MyBookGetDto */
                List<MyBookGetDto> myBookGetDtoList = bookRepository.getAllMyBookList(memberId, myCategoryGetDto.getCategoryId())
                                                                    .orElse(new ArrayList<>());
                myCategoryGetDto.setBookList(myBookGetDtoList);
            }
        }

        return myCategoryGetDtoList;
    }

    @Override
    public Long createBook(BookPostDto bookPostDto) {
        return null;
    }

    @Override
    public BookGetDto getBookInfo(Long bookId) {
        return null;
    }

    @Override
    public Long updateBook(BookUpdateDto bookUpdateDto) {
        return null;
    }

    @Override
    public Long deleteBook(Long bookId) {
        return null;
    }

    @Override
    public Long subscribeBook(BookSubscribeDto bookSubscribeDto) {
        return null;
    }

    @Override
    public Long unsubscribeBook(Long bookId) {
        return null;
    }

    @Override
    public List<BookGetDto> getAllBookByCategoryId(Long categoryId) {
        return bookRepository.getAllBookByCategoryId(categoryId);
    }

    @Override
    public Long deletePaperFromBook(Long bookId, List<Long> paperIdList) {
        return null;
    }

    @Override
    public List<BookListGetFromPaperDto> getBookListFromPaper(Long paperId) {
        return null;
    }

}
