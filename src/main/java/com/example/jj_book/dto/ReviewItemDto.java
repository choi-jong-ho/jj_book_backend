package com.example.jj_book.dto;

import com.example.jj_book.entity.ReviewItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewItemDto {

    private Long reviewId;
    private Long itemId;
    private String itemNm;
    private Long rating; //리뷰별점
    private String contents; //리뷰내용
    private String imgUrl; //리뷰 이미지 경로
    private String email;
    private Long likeCount;
    private Long notLikeCount;

    public ReviewItemDto(ReviewItem reviewItem, String imgUrl) {
        this.reviewId = reviewItem.getReview().getId();
        this.itemNm = reviewItem.getItem().getItemNm();
        this.rating = reviewItem.getRating();
        this.contents = reviewItem.getContents();
        this.imgUrl = imgUrl;
        this.email = reviewItem.getReview().getMember().getEmail();
    }

    public ReviewItemDto(ReviewItem reviewItem, Long likeCount, Long notLikeCount) {
        this.reviewId = reviewItem.getReview().getId();
        this.itemNm = reviewItem.getItem().getItemNm();
        this.rating = reviewItem.getRating();
        this.contents = reviewItem.getContents();
        this.itemId = reviewItem.getItem().getId();
        this.email = reviewItem.getReview().getMember().getEmail();
        this.likeCount = likeCount;
        this.notLikeCount = notLikeCount;
    }
}