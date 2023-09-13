package com.example.jj_book.repo;

import com.example.jj_book.entity.ReviewItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewItemRepository extends JpaRepository<ReviewItem, Long> {

    ReviewItem findByReviewIdAndItemId(Long reviewId, Long itemId);

    @Query("select o from ReviewItem o where o.item.id = :id order by o.regTime desc")
    List<ReviewItem> findReviewAllList(@Param("id") Long id, Pageable pageable);

    @Query("select count(o) from ReviewItem o where o.item.id = :id")
    Long countAllReview(@Param("id") Long id);

    @Query("select r from ReviewItem r where r.review.id = :id")
    ReviewItem findByReviewId(@Param("id") Long id);

}