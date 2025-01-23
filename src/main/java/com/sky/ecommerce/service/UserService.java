package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.User;

import java.util.List;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public List<User> findAllUsers();


}
