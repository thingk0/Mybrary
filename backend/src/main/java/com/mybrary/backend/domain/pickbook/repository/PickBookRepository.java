
package com.mybrary.backend.domain.pickbook.repository;
import com.mybrary.backend.domain.chat.entity.ChatJoin;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.custom.MybraryRepositoryCustom;
import com.mybrary.backend.domain.pickbook.entity.PickBook;
import com.mybrary.backend.domain.pickbook.repository.custom.PickBookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PickBookRepository extends JpaRepository<PickBook, Long>, PickBookRepositoryCustom {

}
