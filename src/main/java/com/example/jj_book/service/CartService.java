package com.example.jj_book.service;

import com.example.jj_book.dto.CartItemDto;
import com.example.jj_book.entity.Cart;
import com.example.jj_book.entity.CartItem;
import com.example.jj_book.entity.Item;
import com.example.jj_book.entity.Member;
import com.example.jj_book.repo.CartItemRepository;
import com.example.jj_book.repo.CartRepository;
import com.example.jj_book.repo.ItemRepository;
import com.example.jj_book.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

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

//    @Transactional(readOnly = true)
//    public List<CartDetailDto> getCartList(String email){
//        List<CartDetailDto> cartDetailDtoList = new ArrayList<CartDetailDto>();
//
//        Member member = memberRepository.findByEmail(email);
//        Cart cart = cartRepository.findByMemberId(member.getId());
//        if(cart == null){
//            return cartDetailDtoList;
//        }
//
//        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
//        return cartDetailDtoList;
//    }
}