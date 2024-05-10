package com.restaurant.services;

import com.restaurant.entitys.Dish;
import com.restaurant.entitys.Ingredient;
import com.restaurant.repositories.IngredientsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientsService {
    private final IngredientsRepository ingredientsRepository;

    public IngredientsService(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Transactional
    public void addIngredient(Dish dish, Ingredient updatedIngredient){
        //в этом методе мы добавляем к блюду новый ингредиент
        // для этого создаем новый экземпляр класса, присваиваем ему полученные поля и присваиваем ему dish
        // после сохраняем
        Ingredient ingredient = new Ingredient();
        ingredient.setName(updatedIngredient.getName());
        ingredient.setQuantity(updatedIngredient.getQuantity());
        ingredient.setDish(dish);
        ingredientsRepository.save(ingredient);
    }

    @Transactional
    public void deleteIngredient( Ingredient ingredient) {
        ingredientsRepository.deleteById(ingredient.getId());
    }
}
