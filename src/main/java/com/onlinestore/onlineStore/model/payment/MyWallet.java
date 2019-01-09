package com.onlinestore.onlineStore.model.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyWallet {

    @Id
    @GeneratedValue
    public String id;
    public String password;

}
