package com.ismail.Ecommerce.controller;

import com.ismail.Ecommerce.dto.Purchase;
import com.ismail.Ecommerce.dto.PurchaseResponse;
import com.ismail.Ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    // This controller will handle checkout-related operations,
    // such as processing payments and managing orders.
    private final CheckoutService checkoutService;

    // Constructor injection of the CheckoutService
    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        // Call the service layer to process the purchase and return a response
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        // Return the response to the client
        return purchaseResponse;


    }
}
