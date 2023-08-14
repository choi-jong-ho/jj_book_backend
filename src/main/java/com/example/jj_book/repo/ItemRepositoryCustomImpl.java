package com.example.jj_book.repo;

import com.example.jj_book.constant.ItemSellStatus;
import com.example.jj_book.dto.ItemSearchDto;
import com.example.jj_book.entity.Item;
import com.example.jj_book.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();
        if(searchDateType.equals("all") || searchDateType == null){
            return null;
        } else if(searchDateType.equals("1d")){
            dateTime = dateTime.minusDays(1);
        } else if(searchDateType.equals("1w")){
            dateTime = dateTime.minusWeeks(1);
        } else if(searchDateType.equals("1m")){
            dateTime = dateTime.minusMonths(1);
        } else if(searchDateType.equals("6m")){
            dateTime = dateTime.minusMonths(6);
        }
        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(searchBy.equals("itemNm")){
            return QItem.item.itemNm.like("%"+searchQuery+"%");
        } else if(searchQuery.equals("createBy")){
            return QItem.item.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    private BooleanExpression searchByUse(String useYn){
        if (useYn.equals("N")){
            return QItem.item.useYn.eq(useYn);
        }
        return QItem.item.useYn.eq(useYn);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        // https://toongri.tistory.com/62
        // Querydsl 5.0.0 : fetchResults(), fetchCount() are deprecated
        List<Item> fetch = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()),
                        searchByUse(itemSearchDto.getSearchByUseYn()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> count = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()),
                        searchByUse(itemSearchDto.getSearchByUseYn()));

        return PageableExecutionUtils.getPage(fetch, pageable, ()-> count.fetch().size());
    }

}