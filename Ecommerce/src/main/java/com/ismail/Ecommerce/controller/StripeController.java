package com.ismail.Ecommerce.controller;

import com.ismail.Ecommerce.dto.PaymentInfo;
import com.ismail.Ecommerce.dto.PaymentResponse;
import com.ismail.Ecommerce.service.StripeService;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentResponse> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) {
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                    paymentInfo.getAmount(),
                    paymentInfo.getCurrency()
            );
            return ResponseEntity.ok(new PaymentResponse(paymentIntent.getClientSecret()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
