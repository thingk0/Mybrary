package com.mybrary.backend.domain.chat.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final JPAQueryFactory query;

}
