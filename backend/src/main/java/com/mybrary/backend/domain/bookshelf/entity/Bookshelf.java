package com.mybrary.backend.domain.bookshelf.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE bookshelf SET is_deleted = TRUE WHERE bookshelf_id = ?")
public class Bookshelf extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookshelf_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mybrary_id")
    private Mybrary mybrary;

    @OneToMany(mappedBy = "bookshelf", cascade = {CascadeType.REMOVE})
    private List<Category> categoryList = new ArrayList<>();

}
