package com.example.educationapp.repos;

import com.example.educationapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("update User u set u.name = nuser.name, u.username = ?2 where u.id = ?3 ")
    void updateUserById(@Param("uid") int id, @Param("nuser") User user);
}