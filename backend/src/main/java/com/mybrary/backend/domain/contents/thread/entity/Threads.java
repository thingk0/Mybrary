package com.mybrary.backend.domain.contents.thread.entity;

import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Threads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "mybrary_id")
    private Mybrary mybraryId;


}

