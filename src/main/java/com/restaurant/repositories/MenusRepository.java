package com.restaurant.repositories;


import com.restaurant.entitys.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenusRepository extends JpaRepository<Menu, Integer> {

}
