package com.restaurant.services;

import com.restaurant.entitys.Dish;
import com.restaurant.repositories.DishesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service// помечаем класс, что он является сервисом
// то есть в нем мы пишем всю логику и работу с бд
public class DishesService {

    private final DishesRepository dishesRepository;// обращаемся к нашему репозиторию

    public DishesService(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    //метод для нахождения всех блюд из репозитория (те бд)
    //В репозиторие есть стандартные методы, которые были прописаны по умолчанию
    //Их мы и будем использовать
    public List<Dish> showAll (){
        return dishesRepository.findAll();
    }

    //метод для нахождения одного блюда
    // пишем orElse(null), тк мы можем и не найди блюдо по этому id
    public Dish findOne (int id) {
        return dishesRepository.findById(id).orElse(null);
    }

    @Transactional// эта аннотация отвечает за то, чтобы мы смогли редактировать записи в бд
    public void save(Dish  dish){
        dishesRepository.save(dish);// сохраняем полученную сущность в бд
    }

    @Transactional
    public void subtractOneFromReadyDishes(int id) {
        //в этом методе сначала находим блюдо по id, потом если кол-во готовых блюд больше 0, то отнимаем 1 от кол-ва
        // и сохранаем сущность
       Dish dish = dishesRepository.findById(id).orElse(null);
       if(dish.getReadyDishes() > 0 ){
           dish.setReadyDishes(dish.getReadyDishes() - 1);
           dishesRepository.save(dish);
       }
    }

    @Transactional
    public void delete(int id) {
        dishesRepository.deleteById(id);//удаляем запись(наше блюдо) из бд по id
    }

    @Transactional
    public void update(int id, Dish newDish) {
        //В этом методе мы сначала находим блюдо по id, потом присваиваем найденному блюду значения из полученного
        // newDish и сохраняем его. Благодаря этому происходит обновление
        Dish dish = dishesRepository.findById(id).orElse(null);
        dish.setName(newDish.getName());
        dish.setPrice(newDish.getPrice());
        dish.setReadyDishes(newDish.getReadyDishes());
        dishesRepository.save(dish);
    }
}
