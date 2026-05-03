package com.ismail.Ecommerce.service;
import com.ismail.Ecommerce.dto.Purchase;
import com.ismail.Ecommerce.dto.PurchaseResponse;



public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);

}


