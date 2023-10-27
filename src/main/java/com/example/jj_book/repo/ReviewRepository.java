package com.example.jj_book.repo;

import com.example.jj_book.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {

    Optional<Review> findById (Long id);

    @Query("select o from Review o where o.member.email = :email order by o.reviewDate desc")
    List<Review> findReviewList(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Review o where o.member.email = :email")
    Long countReview(@Param("email") String email);
}
