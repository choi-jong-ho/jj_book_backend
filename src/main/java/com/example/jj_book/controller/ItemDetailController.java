package com.example.jj_book.controller;

import com.example.jj_book.dto.ItemFormDto;
import com.example.jj_book.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemDetailController {

    private final ItemService itemService;

    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity itemDtl(@PathVariable("itemId") Long itemId){

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        return ResponseEntity.ok(itemFormDto);
    }
}
