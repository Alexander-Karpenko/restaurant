package com.restaurant.services;

import com.restaurant.entitys.Dish;
import com.restaurant.entitys.Menu;
import com.restaurant.repositories.MenusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class MenusService {
    private final MenusRepository menusRepository;

    public MenusService(MenusRepository menusRepository) {
        this.menusRepository = menusRepository;
    }

    // находим все меню из бд
    public List<Menu> showAll (){
        return menusRepository.findAll();
    }

    // находим одно меню из бд
    public Menu findOne(int id){
        return menusRepository.findById(id).orElse(null);
    }

    //сохраняем полученное меню
    @Transactional
    public void save(Menu menu){
        menusRepository.save(menu);
    }

    //обновляем меню
    @Transactional
    public void update(int id, Menu newMenu){
        Menu menu = menusRepository.findById(id).orElse(null);
        menu.setDate(newMenu.getDate());// присваиваем новую дату
        menusRepository.save(menu);
    }

    //добавляем новое блюдо к меню
    @Transactional
    public void addDish (int id, Dish dish){
        Menu menu = menusRepository.findById(id).orElse(null);
        Set<Dish> dishSet = menu.getDishes();
        dishSet.add(dish);
        menu.setDishes(dishSet);
        menusRepository.save(menu);
    }

    //удаляем меню
    @Transactional
    public void delete(int id){
        menusRepository.deleteById(id);
    }

    // удаляем блюдо из меню
    @Transactional
    public void deleteDish(int id, Dish dish) {
        Menu menu = menusRepository.findById(id).orElse(null);
        Set<Dish> dishSet = menu.getDishes();
        dishSet.remove(dish);
        menu.setDishes(dishSet);
        menusRepository.save(menu);
    }
}
