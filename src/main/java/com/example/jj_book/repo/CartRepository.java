package com.example.jj_book.repo;

import com.example.jj_book.entity.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

    @Query("select o from Cart o where o.member.id = :id order by o.id desc")
    List<Cart> findCarts(@Param("id") Long id, Pageable pageable);

    @Query("select count(o) from Cart o where o.member.id = :id")
    Long countCart(@Param("id") Long id);
}