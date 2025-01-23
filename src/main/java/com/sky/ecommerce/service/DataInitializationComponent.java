package com.sky.ecommerce.service;

import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.UserRepository;
import com.sky.ecommerce.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;

    private CartService cartService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializationComponent(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder,
                                       CartService cartService) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.cartService=cartService;
    }

    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminUsername = "akash220701@gmail.com";

        if (userRepository.findByEmail(adminUsername)==null) {
            User adminUser = new User();

            adminUser.setPassword(passwordEncoder.encode("sky5995040"));
            adminUser.setFirstName("Akash");
            adminUser.setLastName("kumarsu");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(UserRole.ROLE_ADMIN.toString());

            User admin=userRepository.save(adminUser);

            cartService.createCart(admin);
        }
    }

}

