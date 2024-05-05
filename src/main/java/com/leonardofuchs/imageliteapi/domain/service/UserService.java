package com.leonardofuchs.imageliteapi.domain.service;

import com.leonardofuchs.imageliteapi.domain.AcessToken;
import com.leonardofuchs.imageliteapi.domain.entities.User;

public interface UserService {

    User saveUser (User user);
    User getByEmail (String email);
    AcessToken authenticate(String email, String password);

}
