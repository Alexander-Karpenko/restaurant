package com.restaurant.entitys;


import jakarta.persistence.*;


@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private String quantity;

    @ManyToOne//помечаем связь многие к одному
    @JoinColumn(name = "dish_id", referencedColumnName = "id")// помечаем по какому полю объединять таблицы
    private Dish dish; // помещаем блюдо, с которым связан ингредиент

    public Ingredient() {
    }

    public Ingredient(int id, String name, String quantity, Dish dish) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dish = dish;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
