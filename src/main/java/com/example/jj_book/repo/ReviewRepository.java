package com.example.jj_book.repo;

import com.example.jj_book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {

    Review findByMemberId(Long memberId);

}
