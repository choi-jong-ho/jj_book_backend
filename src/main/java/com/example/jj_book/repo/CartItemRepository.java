package com.example.jj_book.repo;

import com.example.jj_book.dto.CartDetailDto;
import com.example.jj_book.entity.CartItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query(value = "select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc", nativeQuery = true)
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

    @Query("select o from CartItem o where o.cart.member.email = :email order by o.regTime desc")
    List<CartItem> findCarts(@Param("email") String email, Pageable pageable);

    @Query("select count(*) from CartItem o where o.cart.member.email = :email")
    Long countCart(@Param("email") String email);

}