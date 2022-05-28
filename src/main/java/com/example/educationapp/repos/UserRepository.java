package com.example.educationapp.repos;

import com.example.educationapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("update User u " +
            "set u.name = :#{#nuser.name}, " +
            "u.username = :#{#nuser.username}, " +
            "u.password = :#{#nuser.password}, " +
            "u.type = :#{#nuser.type} " +
            "where u.id = :uid")
    Optional<User> updateUserById(@Param("uid") int id, @Param("nuser") User user);
}