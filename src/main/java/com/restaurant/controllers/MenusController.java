package com.restaurant.controllers;

import com.restaurant.entitys.Dish;
import com.restaurant.entitys.Menu;
import com.restaurant.services.DishesService;
import com.restaurant.services.MenusService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/menus") //помечаем, что будем принимать url с /menus, после localhost
public class MenusController {

    private final MenusService menusService;
    private final DishesService dishesService;

    public MenusController(MenusService menusService, DishesService dishesService) {
        this.menusService = menusService;
        this.dishesService = dishesService;
    }


    @GetMapping() //обрабатываем  get запрос с url localhost/menus
    public String index(Model model) {
        model.addAttribute("menus", menusService.showAll());//добавляем к модели все меню из бд
        return "menus/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model , @ModelAttribute ("dish") Dish dish) {
        model.addAttribute("menu", menusService.findOne(id)); // добавляем найденное по id  меню
        model.addAttribute("dishes", dishesService.showAll()); // добавляем все блюда из бд
        return "menus/show";
    }
    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id, Model model , @ModelAttribute ("dish") Dish dish) {
        model.addAttribute("menu", menusService.findOne(id));
        model.addAttribute("dishes", dishesService.showAll());
        return "menus/edit";
    }

    @GetMapping("/new")
    public String newMenu(@ModelAttribute("menu") Menu menu, Model model) {
        return "menus/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("menu") Menu menu) {
        menusService.save(menu);// сохраняем меню, полученное с формы
        return "redirect:/menus/" + menu.getId();
    }

    @PostMapping("/{id}/add_dish")
    public String addDish(@PathVariable("id") int id, @ModelAttribute("dish") Dish dish){
        menusService.addDish(id, dishesService.findOne(dish.getId()));// добавляем к меню блюдо, полученное с формы
        return "redirect:/menus/" + id + "/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id, @ModelAttribute("dish") Menu newMenu){
        menusService.update(id, newMenu); // обновляем меню
        return "redirect:/menus/" + id + "/edit";
    }

    @PostMapping("/{id}/delete_dish")
    public String deleteDish(@PathVariable("id") int id, @ModelAttribute("dish") Dish dish){
        menusService.deleteDish(id, dishesService.findOne(dish.getId())); // удаляем блюдо из меню
        return "redirect:/menus/" + id + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        menusService.delete(id); // удалаяем меню по id
        return "redirect:/menus";
    }

    @GetMapping("/{id}/ingredients")
    public String showIngredients(@PathVariable("id") int id, Model model ){
        model.addAttribute("menu", menusService.findOne(id)); //передаем меню, найденное по id
        return "menus/show_ingredients";
    }

}
