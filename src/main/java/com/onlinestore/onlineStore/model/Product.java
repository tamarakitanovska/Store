package com.onlinestore.onlineStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long Id;

    @Size(min=2, message = "Name should be at least 2 characters")
    private String Name;

    @Range(min = 1, max = Integer.MAX_VALUE)
    private Integer Price;

    @Range(min = 0, max = Integer.MAX_VALUE)
    private Integer Quantity;

    @Size(min = 5, message = "ImgURL should be at least 5 characters")
    private String ImgURL;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_products", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<User> users = new ArrayList<>();

    public Product(@Size(min = 5, message = "Name should be at least 5 characters") String name, @Range(min = 1, max = Integer.MAX_VALUE) Integer price, @Range(min = 0, max = Integer.MAX_VALUE) Integer quantity, @Size(min = 5, message = "ImgURL should be at least 5 characters") String imgURL) {
        Name = name;
        Price = price;
        Quantity = quantity;
        ImgURL = imgURL;
    }

    public void addUserToProduct(User user){
        users.add(user);
    }

    public void removeUserFromProduct(User user){
        users.remove(user);
    }
}
