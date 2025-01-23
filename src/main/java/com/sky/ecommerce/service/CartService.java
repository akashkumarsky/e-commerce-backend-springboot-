package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart (User user);

    public CartItem addCartItem(Long userId, AddItemRequest req)throws ProductException;

    public Cart findUserCart(Long userId);
}
