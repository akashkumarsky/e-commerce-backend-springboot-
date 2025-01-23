package com.sky.ecommerce.service;

import com.sky.ecommerce.config.JwtProvider;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,JwtProvider jwtTokenProvider) {

        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;

    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        User user=userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+user.getEmail());
        return user;
    }

//	@Override
//	public List<User> findAllCustomers() {
//		return null;
//	}

    @Override
    public List<User> findAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

}
