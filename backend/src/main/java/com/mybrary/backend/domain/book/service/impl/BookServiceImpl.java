package com.mybrary.backend.domain.book.service.impl;

import com.mybrary.backend.domain.book.dto.requestDto.BookPostDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookUpdateDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookPaperGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.MyBookGetDto;
import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.book.service.BookService;
import com.mybrary.backend.domain.category.dto.responseDto.MyCategoryGetDto;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.CategoryRepository;
import com.mybrary.backend.domain.comment.repository.CommentRepository;
import com.mybrary.backend.domain.contents.like.entity.Like;
import com.mybrary.backend.domain.contents.like.repository.LikeRepository;
import com.mybrary.backend.domain.contents.paper.dto.responseDto.PaperInBookGetDto;
import com.mybrary.backend.domain.contents.paper.repository.PaperRepository;
import com.mybrary.backend.domain.contents.scrap.entity.Scrap;
import com.mybrary.backend.domain.contents.scrap.repository.ScrapRepository;
import com.mybrary.backend.domain.contents.tag.repository.TagRepository;
import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.ImageRepository;
import com.mybrary.backend.domain.member.dto.responseDto.MemberInfoDto;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.repository.MemberRepository;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.domain.pickbook.entity.PickBook;
import com.mybrary.backend.domain.pickbook.repository.PickBookRepository;
import com.mybrary.backend.global.exception.image.ImageNotFoundException;
import com.mybrary.backend.global.exception.pickbook.PickBookNotFoundException;
import com.mybrary.backend.global.exception.book.BookAlreadySubscribeException;
import com.mybrary.backend.global.exception.book.BookCreateException;
import com.mybrary.backend.global.exception.book.BookDeleteException;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
import com.mybrary.backend.global.exception.book.BookSubscribeException;
import com.mybrary.backend.global.exception.book.BookUpdateException;
import com.mybrary.backend.global.exception.category.CategoryNotFoundException;
import com.mybrary.backend.global.exception.category.CategoryOwnerNotFoundException;
import com.mybrary.backend.global.exception.member.MemberNotFoundException;
import com.mybrary.backend.global.exception.paper.PaperDeleteException;
import com.mybrary.backend.global.exception.paper.PaperListNotFoundException;
import com.mybrary.backend.global.exception.tag.TagNotFoundException;
import com.mybrary.backend.global.exception.thread.ThreadIdNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.validation.SchemaFactoryConfigurationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PickBookRepository pickBookRepository;
    private final ImageRepository imageRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PaperRepository paperRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;


    @Override
    public List<MyCategoryGetDto> getMyBookList(Long memberId) {
        /* 카테고리 관련 먼저 받아오기 */
        List<MyCategoryGetDto> myCategoryGetDtoList = categoryRepository.getMyAllCategory(memberId)
                                                                        .orElse(new ArrayList<>());
        if (!myCategoryGetDtoList.isEmpty()) {
            for (MyCategoryGetDto myCategoryGetDto : myCategoryGetDtoList) {
                /* MyBookGetDto */
                List<MyBookGetDto> myBookGetDtoList = bookRepository.getAllMyBookList(memberId, myCategoryGetDto.getCategoryId())
                                                                    .orElse(new ArrayList<>());
                myCategoryGetDto.setBookList(myBookGetDtoList);
            }
        }

        return myCategoryGetDtoList;
    }

    @Transactional
    @Override
    public Long createBook(String email, BookPostDto bookPostDto) {

        Member member = memberService.findMember(email);

        Image image = imageRepository.findById(bookPostDto.getCoverImageId()).orElseThrow(ImageNotFoundException::new);
        Category category = categoryRepository.findById(bookPostDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        Long categoryOwnerId = categoryRepository.findCategoryOwnerId(category.getId()).orElseThrow(CategoryOwnerNotFoundException::new);

        if(!member.getId().equals(categoryOwnerId)){
            throw new BookCreateException();
        }

        Book book = Book.builder()
                        .coverImage(image)
                        .coverTitle(bookPostDto.getTitle())
                        .coverLayout(bookPostDto.getCoverLayout())
                        .coverColor(bookPostDto.getCoverColorCode())
                        .member(member)
                        .build();
        Book savedBook = bookRepository.save(book);

        PickBook pickBook = PickBook.builder()
            .category(category)
            .book(book)
            .build();
        pickBookRepository.save(pickBook);

        return savedBook.getId();
    }

    @Override
    public BookPaperGetDto getBookInfo(String email, Long bookId) {

        Long myId = memberService.findMember(email).getId();
        List<PaperInBookGetDto> paperList = paperRepository.getPaperList(bookId).orElseThrow(PaperListNotFoundException::new);

        for (int i = 0;i<paperList.size();i++) {

            Long threadId = paperRepository.getThreadIdByPaperId(paperList.get(i).getPaperId()).orElseThrow(ThreadIdNotFoundException::new);
            MemberInfoDto writer = paperRepository.getWriter(paperList.get(i).getPaperId()).orElseThrow(MemberNotFoundException::new);
            String imageUrl1 = paperRepository.getImageUrl(paperList.get(i).getPaperId(), 1).orElseThrow(ImageNotFoundException::new);
            String imageUrl2 = paperRepository.getImageUrl(paperList.get(i).getPaperId(), 2).orElseThrow(ImageNotFoundException::new);
            List<String> tagList = tagRepository.getTagList(paperList.get(i).getPaperId()).orElseThrow(TagNotFoundException::new);
            int likeCount = likeRepository.getLikeCount(paperList.get(i).getPaperId()).orElse(0);
            int commentCount = commentRepository.getCommentCount(paperList.get(i).getPaperId()).orElse(0);
            int scrapCount = scrapRepository.getScrapCount(paperList.get(i).getPaperId()).orElse(0);
            Like like = likeRepository.isLikedPaper(paperList.get(i).getPaperId(), myId).orElse(null);
            boolean isLiked = false;
            if(like!=null) isLiked = true;

            paperList.get(i).setThreadId(threadId);
            paperList.get(i).setWriter(writer);
            paperList.get(i).setImage1Url(imageUrl1);
            paperList.get(i).setImage2Url(imageUrl2);
            paperList.get(i).setTagList(tagList);
            paperList.get(i).setLikeCount(likeCount);
            paperList.get(i).setCommentCount(commentCount);
            paperList.get(i).setScrapCount(scrapCount);
            paperList.get(i).setLiked(isLiked);

        }

        BookPaperGetDto book = BookPaperGetDto.builder()
            .bookId(bookId)
            .paperList(paperList)
            .build();

        return book;
    }

    @Transactional
    @Override
    public Long updateBook(String email, BookUpdateDto bookUpdateDto) {

        Member member = memberService.findMember(email);
        Book book = bookRepository.findById(bookUpdateDto.getBookId()).orElseThrow(BookCreateException::new);
        if(!book.getMember().getId().equals(member.getId())){
            throw new BookUpdateException();
        }


        // 현재 카테고리와 수정할 카테고리가 다를때만 픽북삭제하고 새로 저장
        if(!bookUpdateDto.getBeforeCategoryId().equals(bookUpdateDto.getAfterCategoryId())){
            System.out.println("다를때");
            PickBook pickBook = pickBookRepository.getPickBook(bookUpdateDto.getBookId(), bookUpdateDto.getBeforeCategoryId()).orElseThrow(PickBookNotFoundException::new);
            pickBookRepository.delete(pickBook);

            Category afterCategory = categoryRepository.findById(bookUpdateDto.getAfterCategoryId()).orElseThrow(CategoryNotFoundException::new);
            PickBook newPickBook = PickBook.builder()
                                           .book(book)
                                           .category(afterCategory)
                                           .build();
            pickBookRepository.save(newPickBook);
        }

        book.setCoverTitle(bookUpdateDto.getTitle());
        book.setCoverLayout(bookUpdateDto.getCoverLayout());
        book.setCoverColor(bookUpdateDto.getCoverColorCode());


        return book.getId();
    }

    @Transactional
    @Override
    public void deleteBook(String email, Long bookId) {

        Member member = memberService.findMember(email);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        // 책 작성자가 본인이 아니면 지울 수 없음
        if(!book.getMember().getId().equals(member.getId())){
            throw new BookDeleteException();
        }

        Image image = imageRepository.findById(book.getCoverImage().getId()).orElseThrow(ImageNotFoundException::new);

        // 책표지이미지 삭제하고 책도 삭제하고
        imageRepository.delete(image);
        bookRepository.delete(book);

        // 책을 모든 카테고리에서 빼자
        List<PickBook> pickBookList = pickBookRepository.findAllByBookId(bookId).orElseThrow(PickBookNotFoundException::new);
        pickBookRepository.deleteAll(pickBookList);

    }

    @Transactional
    @Override
    public Long subscribeBook(String email, BookSubscribeDto bookSubscribeDto) {

        Member member = memberService.findMember(email);
        Book book = bookRepository.findById(bookSubscribeDto.getBookId()).orElseThrow(BookNotFoundException::new);
        Category category = categoryRepository.findById(bookSubscribeDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Long categoryOwnerId = categoryRepository.findCategoryOwnerId(category.getId()).orElseThrow(CategoryOwnerNotFoundException::new);

        // 꽂으려는 카테고리가 내것이 아님
        if(!member.getId().equals(categoryOwnerId)){
            throw new BookSubscribeException();
        }

        PickBook pickBook = pickBookRepository.getPickBook(bookSubscribeDto.getBookId(), bookSubscribeDto.getCategoryId()).orElse(null);

        // 이미 책이 카테고리에 꽂혀있는 것
        if(pickBook!=null){
            throw new BookAlreadySubscribeException();
        }

        PickBook subscribe = PickBook.builder()
            .book(book)
            .category(category)
            .build();
        PickBook savedSubscribe = pickBookRepository.save(subscribe);

        return savedSubscribe.getId();
    }

    @Override
    public Long unsubscribeBook(String email, Long bookId) {

        Member member = memberService.findMember(email);

        List<PickBook> pickBookList = pickBookRepository.getPickBookList(member.getId(), bookId).orElseThrow(PickBookNotFoundException::new);
        pickBookRepository.deleteAll(pickBookList);

        return null;
    }

    @Override
    public List<BookGetDto> getAllBookByCategoryId(Long categoryId) {
        return bookRepository.getAllBookByCategoryId(categoryId).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void deletePaperFromBook(String email, Long bookId, Long paperId) {

        Member member = memberService.findMember(email);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Long bookOwnerId = book.getMember().getId();

        // 책이 내가 만든것이 아닐 때
        if(!member.getId().equals(bookOwnerId)){
            throw new PaperDeleteException();
        }

        Scrap scrap = scrapRepository.getScrap(bookId, paperId).orElseThrow(SchemaFactoryConfigurationError::new);
        scrapRepository.delete(scrap);

    }

    @Override
    public List<BookListGetFromPaperDto> getBookListFromPaper(Long paperId) {

        return bookRepository.getBookListFromPaper(paperId).orElseThrow(BookNotFoundException::new);

    }

}
