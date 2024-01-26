package com.mybrary.backend.global.Support;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


@Repository
public abstract class Querydsl4RepositorySupport<T> {

    private final Class<T> domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    public Querydsl4RepositorySupport(Class<T> domainClass) {
        Assert.notNull(domainClass, "도메인은 null 이 아니어야 합니다.");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager 는 null 이 아니어야 합니다.");
        JpaEntityInformation<T, ?> entityInformation =
            JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());

        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager,
                                     new PathBuilder<>(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager 는 null 이 아니어야 합니다.");
        Assert.notNull(querydsl, "Querydsl 은 null 이 아니어야 합니다.");
        Assert.notNull(queryFactory, "QueryFactory 는 null 이 아니어야 합니다.");
    }

    protected JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected JPAQuery<T> select(Expression<T> expr) {
        return getQueryFactory().select(expr);
    }

    protected JPADeleteClause delete(EntityPath<T> from) {
        return getQueryFactory().delete(from);
    }

    protected JPAInsertClause insert(EntityPath<T> from) {
        return getQueryFactory().insert(from);
    }

    protected JPAUpdateClause update(EntityPath<T> from) {
        return getQueryFactory().update(from);
    }

    protected JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getQueryFactory().selectFrom(from);
    }

    protected Page<T> applyPagination(Pageable pageable,
                                      Function<JPAQueryFactory, JPAQuery<T>> contentQuery) {

        JPAQuery<T> jpaQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getFetch(pageable, jpaQuery);

        return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount);
    }

    protected Page<T> applyPagination(Pageable pageable,
                                      Function<JPAQueryFactory, JPAQuery<T>> contentQuery,
                                      Function<JPAQueryFactory, JPAQuery<Long>> countQuery) {

        JPAQuery<T> jpaContentQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getFetch(pageable, jpaContentQuery);
        JPAQuery<Long> countResult = countQuery.apply(getQueryFactory());

        return PageableExecutionUtils.getPage(content, pageable, countResult::fetchCount);
    }

    private List<T> getFetch(Pageable pageable, JPAQuery<T> jpaContentQuery) {
        return getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
    }
}
