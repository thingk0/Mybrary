
package com.mybrary.backend.domain.pickbook.repository;

import com.mybrary.backend.domain.pickbook.entity.PickBook;
import com.mybrary.backend.domain.pickbook.repository.custom.PickBookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickBookRepository extends JpaRepository<PickBook, Long>, PickBookRepositoryCustom {

}
