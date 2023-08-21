package com.example.jj_book.repo;

import com.example.jj_book.entity.QAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class AddressRepositoryCustomImpl implements AddressRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public AddressRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Long updateRepYn(Long id) {

        return queryFactory
                .update(QAddress.address1)
                .set(QAddress.address1.repAddYn, "N")
                .where(QAddress.address1.member.id.eq(id))
                .execute();

    }

}