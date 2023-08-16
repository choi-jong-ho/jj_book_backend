package com.example.jj_book.dto;

import com.example.jj_book.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String itemNm; //상품명
    private int count; //주문수량
    private int orderPrice; //주문금액
    private String imgUrl; //상품 이미지 경로

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}