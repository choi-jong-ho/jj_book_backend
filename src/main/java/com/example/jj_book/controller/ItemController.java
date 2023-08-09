package com.example.jj_book.controller;

import com.example.jj_book.dto.ItemFormDto;
import com.example.jj_book.dto.ItemSearchDto;
import com.example.jj_book.entity.Item;
import com.example.jj_book.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @PostMapping(value = "/item/new")
    public ResponseEntity itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                  @RequestPart(value = "itemImgFile",required = false) List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        System.out.println("이미지 : "+ itemImgFileList.get(0).getOriginalFilename());

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){

        }

        return ResponseEntity.ok(itemFormDto);
    }

    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity itemDtl(@PathVariable("itemId") Long itemId){

        System.out.println("itemId" + itemId);

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        return ResponseEntity.ok(itemFormDto);
    }

    @PostMapping(value = "/item/{itemId}")
    public ResponseEntity itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                     @RequestPart(value = "itemImgFile", required = false) List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
        }

        return ResponseEntity.ok(itemFormDto);
    }

    @GetMapping(value = {"/item/list", "/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }
//
//    @GetMapping(value = "/item/{itemId}")
//    public ResponseEntity itemDtl(@RequestBody Item item){
//        ItemFormDto itemFormDto = itemService.getItemDtl(item.getId());
//        return ResponseEntity.ok(itemFormDto);
//    }

}