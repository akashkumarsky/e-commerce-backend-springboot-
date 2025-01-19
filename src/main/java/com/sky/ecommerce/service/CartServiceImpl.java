package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Cart;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.CartRepository;
import com.sky.ecommerce.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        // Retrieve the cart for the user
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        Cart cart = cartOptional.orElseThrow(() -> new ProductException("Cart not found for user with id: " + userId));

        // Retrieve the product to add to the cart
        Product product = productService.findProductById(req.getProductId());
        if (product == null) {
            throw new ProductException("Product not found with id: " + req.getProductId());
        }

        // Check if the cart item already exists
        CartItem existingCartItem = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if (existingCartItem == null) {
            // Create a new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setId(userId);

            // Calculate the price based on quantity and discounted price
            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            // Create the CartItem in the database
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);

            // Save the cart after adding the item
            cartRepository.save(cart);
        } else {
            // Handle case where item already exists (optional: update the quantity or price)
            // cartItemService.updateCartItem(existingCartItem, req);
        }

        return "Item added to Cart";
    }


    @Override
    public Cart findUserCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);

        Cart cart;
        try {
            cart = cartOptional.orElseThrow(() -> new ProductException("Cart not found for user with id: " + userId));
        } catch (ProductException e) {
            // Handle the exception here if needed, or just rethrow it
            throw new RuntimeException(e); // Rethrow or handle accordingly
        }
        int totalPrice = 0;
        int totalDiscountPrice = 0;
        int totalItems = 0;

        // Calculate the total price, total discounted price, and total number of items
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getPrice();
            totalDiscountPrice += cartItem.getDiscountedPrice();
            totalItems += cartItem.getQuantity();
        }

        // Set the calculated values to the cart
        cart.setTotalDiscountedPrice(totalDiscountPrice);
        cart.setTotalItem(totalItems);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountPrice);

        // Save the updated cart
        return cartRepository.save(cart);
    }

}
