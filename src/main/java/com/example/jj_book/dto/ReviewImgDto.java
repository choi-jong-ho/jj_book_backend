package com.example.jj_book.dto;

import com.example.jj_book.entity.ReviewImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ReviewImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;


    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewImgDto of(ReviewImg reviewImg) {
        return modelMapper.map(reviewImg, ReviewImgDto.class);
    }

}