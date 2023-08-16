package com.example.jj_book.service;

import com.example.jj_book.dto.OrderDto;
import com.example.jj_book.entity.Item;
import com.example.jj_book.entity.Member;
import com.example.jj_book.entity.Order;
import com.example.jj_book.entity.OrderItem;
import com.example.jj_book.repo.ItemImgRepository;
import com.example.jj_book.repo.ItemRepository;
import com.example.jj_book.repo.MemberRepository;
import com.example.jj_book.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email) {

        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}