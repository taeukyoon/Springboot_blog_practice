package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// DAO
// 자동으로 bean등록이 된다.
// @Repository 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);
}




//     JPA Naming 쿼리
//     SELECT * FROM user where username = ? AND password = ?;
//    User findByUsernameAndPassword(String username, String password);