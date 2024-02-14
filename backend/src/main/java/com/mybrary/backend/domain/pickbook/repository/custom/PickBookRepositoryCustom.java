
package com.mybrary.backend.domain.pickbook.repository.custom;
import com.mybrary.backend.domain.mybrary.dto.MybraryGetDto;
import com.mybrary.backend.domain.mybrary.dto.MybraryOtherGetDto;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.pickbook.entity.PickBook;
import java.util.List;
import java.util.Optional;

public interface PickBookRepositoryCustom {
    Mybrary findByMybraryId(Long mybraryId);
    MybraryGetDto getMybrary(Long myId);
    MybraryOtherGetDto getOtherMybrary(Long otherId);

    Optional<PickBook> getPickBook(Long bookId, Long categoryId);

    Optional<List<PickBook>> findAllByBookId(Long bookId);

    Optional<List<PickBook>> getPickBookList(Long myId, Long bookId);

}
