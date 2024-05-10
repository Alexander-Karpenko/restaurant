package com.restaurant.repositories;

import com.restaurant.entitys.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//этот интерфейс представляет собой репозиторий
// Он нужен для того, чтобы не писать ручками запросы, а Spring Data JPA генерировал их за нас
// при помощи hibernate. Для этого мы наследуемся от JpaRepository и помещаем в него наш класс,
// с которым мы хотели работать в бд и тип его первичного ключа(id)
@Repository
public interface DishesRepository extends JpaRepository<Dish, Integer> {
}
