package com.sky.ecommerce.repository;

import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci from CartItem ci Where ci.cart =:cart And ci.product =:product And size =:size And ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product, @Param("size") String size, @Param("userId") Long userId);
}
