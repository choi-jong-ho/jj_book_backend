package com.example.jj_book.dto;

import com.example.jj_book.entity.Cart;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartHistDto {

    public CartHistDto(Cart cart){
        this.cartId = cart.getId();
        this.regTime = cart.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private Long cartId; //카트아이디
    private String regTime; //카트등록날짜

    //주문 상품리스트
    private List<CartItemDto> cartItemDtoList = new ArrayList<>();

    public void addCartItemDto(CartItemDto cartItemDto){
        cartItemDtoList.add(cartItemDto);
    }
}
