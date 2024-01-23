package com.mybrary.backend.domain.category.entity;


import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_shelf_id")
    private Bookshelf bookshelf;

    private String categoryName;

    private int categorySeq;
}
