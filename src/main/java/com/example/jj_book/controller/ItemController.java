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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
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

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){

        }

        return ResponseEntity.ok(itemFormDto);
    }

    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity itemDtl(@PathVariable("itemId") Long itemId){

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        return ResponseEntity.ok(itemFormDto);
    }

    @PostMapping(value = "/item/{itemId}")
    public ResponseEntity itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                     @RequestPart(value = "itemImgFile", required = false) List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        System.out.println("itemImgFileList.size :" + itemImgFileList.size());
        System.out.println("itemImgFileList.get(0).getOriginalFilename :" + itemImgFileList.get(0).getOriginalFilename());
        System.out.println("itemImgFileList.get(1).getOriginalFilename :" + itemImgFileList.get(1).getOriginalFilename());

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
        }

        return ResponseEntity.ok(itemFormDto);
    }

    @PostMapping(value = "/item/delete")
    public ResponseEntity itemDelete(@RequestBody ItemFormDto itemFormDto){

        try {
            itemService.deleteItem(itemFormDto);
        } catch (Exception e){
        }

        return ResponseEntity.ok(itemFormDto);
    }

    @GetMapping(value = {"/item/list", "/item/list/{page}"})
    public List<Page> itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        List<Page> list = new ArrayList<>();
        list.add(items);

        return list;
    }

//    @GetMapping(value = "/item/{itemId}")
//    public ResponseEntity itemDtl(@RequestBody Item item){
//        ItemFormDto itemFormDto = itemService.getItemDtl(item.getId());
//        return ResponseEntity.ok(itemFormDto);
//    }

}