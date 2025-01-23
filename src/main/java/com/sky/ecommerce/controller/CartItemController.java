package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.CartItemException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.CartItem;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.CartItemService;
import com.sky.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart_items")
//@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;

    @Autowired
    public CartItemController(CartItemService cartItemService,UserService userService) {
        this.cartItemService=cartItemService;
        this.userService=userService;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws CartItemException, UserException {

        // Find user by JWT
        User user = userService.findUserProfileByJwt(jwt);

        // Perform delete operation
        cartItemService.removeCartItem(user.getId(), cartItemId);

        // Create a response body indicating successful deletion
        ApiResponse res = new ApiResponse();
        res.setMessage("Item have been removed from Cart");
        res.setStatus(true);

        // Return the response with 204 No Content (as no content is needed after deletion)
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED); // HttpStatus.NO_CONTENT implies successful deletion without returning content
    }




    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{

        User user=userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        //ApiResponse res=new ApiResponse("Item Updated",true);

        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }
}
