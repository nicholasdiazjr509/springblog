package com.codeup.springblog.repositories;


import com.codeup.springblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


//We will need a UserRepository to authenticate users.
// We need a method to find users by their username.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
