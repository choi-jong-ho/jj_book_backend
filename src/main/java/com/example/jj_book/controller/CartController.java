package com.example.jj_book.controller;

import com.example.jj_book.dto.CartDetailDto;
import com.example.jj_book.dto.CartItemDto;
import com.example.jj_book.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartItemDto cartItemDto
            , BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartItemid;

        try {
            cartItemid = cartService.addCart(cartItemDto, email);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemid, HttpStatus.OK);
    }

    @GetMapping(value = {"/list","/list/{page}"})
    public List<Page> cartHist(@PathVariable("page") Optional<Integer> page, Principal principal){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<CartDetailDto> cartDetailDtoList = cartService.getCartList(principal.getName(), pageable);

        List<Page> list = new ArrayList<>();
        list.add(cartDetailDtoList);

        return list;
    }

    @PostMapping(value = "/update")
    public ResponseEntity cartUpdate(@RequestBody CartDetailDto cartDetailDto){

        try {
            cartService.updateCart(cartDetailDto);
        } catch (Exception e){
        }

        return ResponseEntity.ok(cartDetailDto);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity cartDelete(@RequestBody CartDetailDto cartDetailDto){

        try {
            cartService.deleteCart(cartDetailDto.getCartItemId());
        } catch (Exception e){
        }

        return ResponseEntity.ok(cartDetailDto);
    }
}