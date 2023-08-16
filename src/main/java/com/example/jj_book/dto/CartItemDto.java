package com.example.jj_book.dto;

import com.example.jj_book.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {

    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private int count;

    private String itemNm; //상품명
    private String imgUrl; //상품 이미지 경로

    public CartItemDto(CartItem cartItem, String imgUrl) {
        this.itemNm = cartItem.getItem().getItemNm();
        this.count = cartItem.getCount();
        this.imgUrl = imgUrl;
    }

}