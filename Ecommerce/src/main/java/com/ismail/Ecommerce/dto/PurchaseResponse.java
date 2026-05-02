package com.ismail.Ecommerce.dto;


import lombok.Data;

// using this class to send back the response to the client after a purchase is made
@Data
public class PurchaseResponse {

    private final String orderTrackingNumber;


}
