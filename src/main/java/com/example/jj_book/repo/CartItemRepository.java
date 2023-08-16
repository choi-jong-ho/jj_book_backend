package com.example.jj_book.repo;

import com.example.jj_book.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

//    @Query(value = "select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
//            "from CartItem ci, ItemImg im " +
//            "join ci.item i " +
//            "where ci.cart.id = :cartId " +
//            "and im.item.id = ci.item.id " +
//            "and im.repimgYn = 'Y' " +
//            "order by ci.regTime desc", nativeQuery = true)
//    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}