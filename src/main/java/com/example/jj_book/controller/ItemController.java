package com.example.jj_book.controller;

import com.example.jj_book.dto.ItemFormDto;
import com.example.jj_book.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(value = "/admin/item/{itemId}")
    public ResponseEntity itemDtl(@RequestBody Long itemId){

        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        return ResponseEntity.ok(itemFormDto);
    }

//    @PostMapping(value = "/admin/item/{itemId}")
//    public ResponseEntity itemUpdate(@RequestBody @Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
//        if(bindingResult.hasErrors()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
//        }
//
//        try {
//            itemService.updateItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//        }
//
//        return ResponseEntity.ok(itemFormDto);
//    }
//
//    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
//    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
//
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
//        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
//
//        model.addAttribute("items", items);
//        model.addAttribute("itemSearchDto", itemSearchDto);
//        model.addAttribute("maxPage", 5);
//
//        return "item/itemMng";
//    }
//
//    @GetMapping(value = "/item/{itemId}")
//    public ResponseEntity itemDtl(@RequestBody Item item){
//        ItemFormDto itemFormDto = itemService.getItemDtl(item.getId());
//        return ResponseEntity.ok(itemFormDto);
//    }

}