package com.restaurant.controllers;

import com.restaurant.entitys.Dish;
import com.restaurant.entitys.Ingredient;
import com.restaurant.services.DishesService;
import com.restaurant.services.IngredientsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // эта аннотация делает класс контроллером(spring будет понимать, что это контроллер).
// Контроллер принимает запросы по url и потом возвращает ответы
@RequestMapping("/dishes")//помечаем, что будем принимать url с /dishes, после localhost
public class DishesController {

    private final DishesService dishesService;// добавляем поле сервис. В нем лежит логика работы модели
    // и через него обращаемся к базе данных
    private final IngredientsService ingredientsService;


    //Конструктор класса
    public DishesController(DishesService dishesService, IngredientsService ingredientsService) {
        this.dishesService = dishesService;
        this.ingredientsService = ingredientsService;
    }


    @GetMapping()// Помечаем, что будем принимать get запросы
    //Get запрос запрашивает представление ресурса. Запросы с использованием этого метода могут только извлекать данные.
    //Post запрос используется для отправки сущностей к определённому ресурсу.
    // Часто вызывает изменение состояния или какие-то побочные эффекты на сервере.
    public String index(Model model) {
        model.addAttribute("dishes", dishesService.showAll());// добавляем к модели атрибут
        // то есть значение под ключом "dishes"
        //  помощью метода showAll из dishesService находим все блюда
        return "dishes/showAll"; // Возвращаем html страницу, которая лежит у нас на сервере
        // в директории webapp/WEB-INF/views/dishes/showAll
        // (webapp/WEB-INF/views/ указывать не нужно, тк мы это настроили в SpringConfig
    }

    @GetMapping("/{id}") // опять помечаем, что будем принимать get запрос и в скобках указываем продолжение url
    // тк мы указали над классом @RequestMapping("/dishes"), то вместе с @GetMapping("/{id}") у нас получится
    // localhost/dishes/2 (запись в формате {id} означает, что может на месте {id} стоять любое число
    public String show(@PathVariable("id") int id //берем из нашего url число, помеченое {id}
            , Model model //то, что мы будем передавать шаблонизатору в качестве значения
            , @ModelAttribute("ingredient") Ingredient ingredient//то, что мы будем передавать шаблонизатору как пустой класс
                        ) {
        model.addAttribute("dish", dishesService.findOne(id)); // добавляем к модели атрибут
        // то есть значение под ключом "dish"
        //  помощью метода findOne из dishesService находим по id блюдо
        return "dishes/show";
    }
    @GetMapping("/{id}/edit")//принимает get запрос по url localhost/dishes/(число)/edit
    public String edit(@PathVariable("id") int id, Model model, @ModelAttribute("ingredient") Ingredient ingredient) {
        model.addAttribute("dish", dishesService.findOne(id));
        return "dishes/edit";
    }

    @PostMapping("/{id}/add_ingredient")//помечаем, что будем принимать post запрос (те будем изменять значения в базе данных)
    public String addIngredient(@PathVariable("id") int id, @ModelAttribute("ingredient") Ingredient ingredient){
        ingredientsService.addIngredient(dishesService.findOne(id), ingredient); // добавляем в бд
        // новый ингредиент к блюду ( ингредиент передаеться из формы, которую заполнил пользователь)
        return "redirect:/dishes/" + id + "/edit"; // перекидываем пользователя на url localhost/dishes/(id)/edit
    }
   @PostMapping("/{id}/delete_ingredient")
    public String deleteIngredient(@PathVariable("id") int id, @ModelAttribute("ingredient") Ingredient ingredient){
        ingredientsService.deleteIngredient(ingredient); // удаляем ингредиент
        return "redirect:/dishes/" + id + "/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id, @ModelAttribute("dish") Dish newDish){
        dishesService.update(id, newDish); // обновляем блюдо, newDish прийдет с формы запроса
        return "redirect:/dishes/" + id + "/edit";
    }

    @GetMapping("/add")
    public String addDish(@ModelAttribute("dish") Dish dish){
    return "dishes/new"; // возвращаем страницу, для заполения формы для нового блюда
    }

    @PostMapping("/add")
    public String createDish(@ModelAttribute("dish") Dish dish){
        dishesService.save(dish); // добавляем к бд нове блюдо
        return "redirect:/dishes/" + dish.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        dishesService.delete(id); // удаляем из бд блюдо по id
        return "redirect:/dishes";
    }



}
