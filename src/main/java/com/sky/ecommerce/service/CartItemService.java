package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.CartItemException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId ,Long id , CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void  removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
