package com.example.jj_book.service;

import com.example.jj_book.dto.CartDetailDto;
import com.example.jj_book.dto.CartItemDto;
import com.example.jj_book.entity.*;
import com.example.jj_book.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemImgRepository itemImgRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {

        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public Page<CartDetailDto> getCartList(String email, Pageable pageable){

        Member member = memberRepository.findByEmail(email);
        List<CartItem> cartItems = cartItemRepository.findCarts(member.getEmail(), pageable);
        Long totalCount = cartItemRepository.countCart(member.getEmail());

        System.out.println("carts : " + cartItems.size());
        System.out.println("totalCount : " + totalCount);

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        //주문 리스트 순회 -> 구매 이력페이지에 전달
        for(CartItem cartItem : cartItems) {

            ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(cartItem.getItem().getId(), "Y");

            CartDetailDto cartDetailDto = new CartDetailDto(cartItem.getId(), cartItem.getItem().getItemNm(), cartItem.getItem().getPrice(), cartItem.getCount(), itemImg.getImgUrl());

            cartDetailDtoList.add(cartDetailDto);
        }

        return new PageImpl<CartDetailDto>(cartDetailDtoList, pageable, totalCount);
    }

}