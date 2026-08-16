package com.myapp.cruddemo.dao;
import com.myapp.cruddemo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}