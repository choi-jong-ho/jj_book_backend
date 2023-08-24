package com.example.jj_book.repo;

import com.example.jj_book.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    ReviewImg findByReviewId(Long reviewId);

}