package com.ecommerce.application.service;
import com.ecommerce.application.model.Cart;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.reponse.CartHandler;
import com.ecommerce.application.repository.CartRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartHandler cartHandler;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartHandler cartHandler) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartHandler = cartHandler;
    }

    public Object getCarts(){
        return cartHandler.GenerateResponse();
    }

    public Optional<Cart> getSingleCart(Long id){
        boolean exists = cartRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product id: " + id + " does not exists.");
        }
        return cartRepository.findById(id);
    }

    public Object createCart(Cart cart){
        cartRepository.save(cart);
        return cartHandler.GenerateResponse();
    }

    public Object deleteCart(Long id){
        boolean exists = cartRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Product id: " + id + " does not exist.");
        }

        // delete single product
        cartRepository.deleteById(id);

        return cartHandler.GenerateResponse();
    }

    public Object updateCart(Cart cart, Long id) {
        Cart cartToUpdate = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cart Item id: " + id + " does not exists"));

        // Get price
        Product product = productRepository.findById(cartToUpdate.getProduct_id())
                .orElseThrow(() -> new IllegalStateException("Product id: " + id + " does not exists"));

        // Update Cart Item
        cartToUpdate.setQuantity(cart.getQuantity());
        cartToUpdate.setPrice(product.getPrice() * cart.getQuantity());
        cartRepository.save(cartToUpdate);

        return cartHandler.GenerateResponse();
    }

}