package com.restaurant.entitys;


import jakarta.persistence.*;

import java.util.Set;

@Entity// помечаем этой аннотацией, чтобы Hibernate понял, что это сущность(entity)
@Table(name = "dish") // помечаем, что эта сущность относиться к таблице в бд с именем dish
public class Dish {
    @Id// помечаем, что это поле является id
    @Column(name = "id")//помечаем к какому полю в бд относиться наше поле сущности(id)
    @GeneratedValue(strategy = GenerationType.IDENTITY)// указываем, что id должен генерироваться автоматически
    private int id;

    @Column(name = "name")//помечаем к какому полю в бд относиться наше поле сущности(к полю в бд name)
    private String name;

    @Column(name = "ready_dishes")//помечаем к какому полю в бд относиться наше поле сущности(к полю в бд name)
    private int readyDishes;

    @Column(name = "price")
    private int price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})//указываем что это поле имеет связь многиие ко многим
    // указываем  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, чтобы при изменении зависимой сущности
    // те class menu, эта сущность только обновлялась или добавлялась, но не удалялась
    @JoinTable(name = "menu_dish",
            joinColumns =  @JoinColumn(name = "dish_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id")
    )// объединяем таблицы
    private Set<Menu> menus;// здесь будут храниться зависимые от этого блюда меню

    @OneToMany(mappedBy = "dish",cascade = CascadeType.ALL)// указываем, что связь один ко многим,
    // cascade = CascadeType.ALL - указываем, что при изменении этого класса будет меняться и дочерний
    private Set<Ingredient> ingredients; // здесь будут храниться ингредиенты, зависимые от блюда

    // пустой конструктор, нужен для корректной работы hibernate
    public Dish() {
    }

    //конструктор со всеми атрибудами
    public Dish(int id, String name, int readyDishes, int price, Set<Menu> menus, Set<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.readyDishes = readyDishes;
        this.price = price;
        this.menus = menus;
        this.ingredients = ingredients;
    }


    // Гетеры и сетеры для доступа к полям класса
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadyDishes() {
        return readyDishes;
    }

    public void setReadyDishes(int readyDishes) {
        this.readyDishes = readyDishes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    //toString для корректного вывода  класса в консоль
    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", readyDishes=" + readyDishes +
                ", price=" + price +
                ", menus=" + menus +
                '}';
    }
}
