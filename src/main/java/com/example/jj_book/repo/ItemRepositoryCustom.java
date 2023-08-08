package com.example.jj_book.repo;

import com.example.jj_book.dto.ItemSearchDto;
import com.example.jj_book.dto.MainItemDto;
import com.example.jj_book.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}