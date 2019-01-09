package com.onlinestore.onlineStore.repository;

import com.onlinestore.onlineStore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String s);
}
