package com.example.jj_book.repo;

import com.example.jj_book.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);

}