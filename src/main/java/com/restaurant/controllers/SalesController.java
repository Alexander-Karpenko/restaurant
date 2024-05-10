package com.restaurant.controllers;

import com.restaurant.entitys.Dish;
import com.restaurant.services.DishesService;
import com.restaurant.services.SalesHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales") //помечаем, что будем принимать url с /sales, после localhost
public class SalesController {

    private final DishesService dishesService;
    private final SalesHistoryService salesHistoryService;

    public SalesController(DishesService dishesService, SalesHistoryService salesHistoryService) {
        this.dishesService = dishesService;
        this.salesHistoryService = salesHistoryService;
    }

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("sales", salesHistoryService.showAll()); // находим всю историю продаж и добавляем к модели
        return "sales/show_all";
    }


    @GetMapping("/{id}")
    public String sale(Model model,@PathVariable("id") int id){
        model.addAttribute("readyDishes", dishesService.findOne(id).getReadyDishes());// добавляем к модели
        // количество готовых блюд, найденных по id люда
        dishesService.subtractOneFromReadyDishes(id);// отнимаем от кол-ва готовых блюд 1, если кол-во готовых блюд 0
        // то оставляем 0
        return "sales/show";
    }


    @PostMapping()
    public String sale(@ModelAttribute("dish") Dish dish){
        salesHistoryService.save(dishesService.findOne(dish.getId()));// записываем в историю продаж в бд какое
        // блюдо и когда продали
        return "redirect:/sales/" + dish.getId();
    }
}
