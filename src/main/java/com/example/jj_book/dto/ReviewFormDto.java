package com.example.jj_book.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewFormDto {

    private Long itemId;

    @NotBlank(message = "리뷰내용은 필수 입력 값입니다.")
    private String contents;

    @NotNull(message = "별점은 필수 입력 값입니다.")
    private Integer rating;

    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

}