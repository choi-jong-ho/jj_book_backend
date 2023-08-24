package com.example.jj_book.dto;

import com.example.jj_book.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewHistDto {

    public ReviewHistDto(Review review){
        this.reviewId = review.getId();
        this.reviewDate = review.getReviewDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private Long reviewId; //리뷰아이디
    private String reviewDate; //리뷰날짜

    //주문 상품리스트
    private List<ReviewItemDto> reviewItemDtoList = new ArrayList<>();

    public void addReviewItemDto(ReviewItemDto reviewItemDto){
        reviewItemDtoList.add(reviewItemDto);
    }

}