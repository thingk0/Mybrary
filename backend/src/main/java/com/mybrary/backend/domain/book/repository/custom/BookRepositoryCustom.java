package com.mybrary.backend.domain.book.repository.custom;

import java.util.Optional;

public interface BookRepositoryCustom {


    Optional<Integer> countMyBook(Long bookShelfId);

}
