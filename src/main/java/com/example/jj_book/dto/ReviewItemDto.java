package com.example.jj_book.dto;

import com.example.jj_book.entity.ReviewItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewItemDto {

    private int rating; //리뷰별점
    private String contents; //리뷰내용
    private String imgUrl; //리뷰 이미지 경로

    public ReviewItemDto(ReviewItem reviewItem, String imgUrl) {
        this.rating = reviewItem.getRating();
        this.contents = reviewItem.getContents();
        this.imgUrl = imgUrl;
    }

    public ReviewItemDto(ReviewItem reviewItem) {
        this.rating = reviewItem.getRating();
        this.contents = reviewItem.getContents();
    }
}