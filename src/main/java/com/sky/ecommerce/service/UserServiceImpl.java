package com.sky.ecommerce.service;

import com.sky.ecommerce.config.JwtProvider;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.UserRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id - "+userId);
    }

    @Override
    public User findUserPorfileByJwt(String jwt) throws UserException {

        String email =jwtProvider.extractEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);

        if (user==null){
            throw new UserException("user not found with email - " + email);
        }
        return user;
    }
}
