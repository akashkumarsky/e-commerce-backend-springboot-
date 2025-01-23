package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c From Cart c where c.user.id=:userId")
    public Cart findByUserId(@Param("userId")Long userId);


}
