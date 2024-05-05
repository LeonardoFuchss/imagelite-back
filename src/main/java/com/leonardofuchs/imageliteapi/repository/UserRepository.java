package com.leonardofuchs.imageliteapi.repository;

import com.leonardofuchs.imageliteapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail (String email);

}
