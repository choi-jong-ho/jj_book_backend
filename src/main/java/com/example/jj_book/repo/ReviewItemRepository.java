package com.example.jj_book.repo;

import com.example.jj_book.entity.ReviewItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewItemRepository extends JpaRepository<ReviewItem, Long> {

    ReviewItem findByReviewIdAndItemId(Long reviewId, Long itemId);

}