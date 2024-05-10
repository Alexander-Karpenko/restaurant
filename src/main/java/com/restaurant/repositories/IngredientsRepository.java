package com.restaurant.repositories;


import com.restaurant.entitys.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient,Integer> {
}
