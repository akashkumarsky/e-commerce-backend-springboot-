package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserPorfileByJwt(String jwt) throws UserException;
}
