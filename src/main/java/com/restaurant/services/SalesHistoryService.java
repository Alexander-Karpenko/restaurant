package com.restaurant.services;

import com.restaurant.entitys.Dish;
import com.restaurant.entitys.SalesHistory;
import com.restaurant.repositories.SalesHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalesHistoryService {
    private final SalesHistoryRepository salesHistoryRepository;

    public SalesHistoryService(SalesHistoryRepository salesHistoryRepository) {
        this.salesHistoryRepository = salesHistoryRepository;
    }

    //добавляем новую запись в историю продаж
    @Transactional
    public void save(Dish dish){
        SalesHistory salesHistory = new SalesHistory();
        salesHistory.setDate(LocalDateTime.now());
        salesHistory.setDishName(dish.getName());
        salesHistory.setPrice(dish.getPrice());
        salesHistoryRepository.save(salesHistory);
    }

    //находим все записи из истории продаж
    public List<SalesHistory> showAll(){
        return salesHistoryRepository.findAll();
    }
}
