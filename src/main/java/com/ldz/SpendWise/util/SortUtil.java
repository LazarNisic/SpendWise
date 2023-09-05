package com.ldz.SpendWise.util;

import com.ldz.SpendWise.service.data.SortOrder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortUtil {
    public List<Sort.Order> getSort(List<SortOrder> sortOrders) {
        return getSort(sortOrders, new ArrayList<>());
    }

    public List<Sort.Order> getSort(List<SortOrder> sortOrders, List<String> defaultSortFields) {
        List<Sort.Order> orders = new java.util.ArrayList<>(sortOrders.stream()
                .map(sortOrder -> new Sort.Order(sortOrder.getDirection(), sortOrder.getField())).toList());
        orders.addAll(
                defaultSortFields.stream().filter(field -> !orders.stream().map(Sort.Order::getProperty).toList().contains(field))
                        .map(field -> new Sort.Order(Sort.Direction.ASC, field)).toList());
        return orders;
    }

}
