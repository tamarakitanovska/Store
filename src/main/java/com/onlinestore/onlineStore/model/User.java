package com.onlinestore.onlineStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5, message = "Username should be at least 5 characters")
    private String username;

    @Size(min = 5, message = "Password should be at least 6 characters")
    private String password;

    @Size(min = 6, message = "Email format error")
    private String email;

    @Size(min = 2, message = "First Name should be at least 2 characters")
    private String firstName;

    @Size(min = 2, message = "Last Name should be at least 2 characters")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
    private List<Product> products = new ArrayList<>();

    public User(@Size(min = 5, message = "Username should be at least 5 characters") String username, @Size(min = 5, message = "Password should be at least 6 characters") String password, @Size(min = 6, message = "Email format error") String email, @Size(min = 2, message = "First Name should be at least 2 characters") String firstName, @Size(min = 2, message = "Last Name should be at least 2 characters") String lastName, Roles roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
}

