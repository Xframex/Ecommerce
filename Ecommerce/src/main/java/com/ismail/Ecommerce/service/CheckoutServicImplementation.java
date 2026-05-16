package com.ismail.Ecommerce.service;

import com.ismail.Ecommerce.dao.CustomerRepository;
import com.ismail.Ecommerce.dto.Purchase;
import com.ismail.Ecommerce.dto.PurchaseResponse;
import com.ismail.Ecommerce.entity.Customer;
import com.ismail.Ecommerce.entity.Order;
import com.ismail.Ecommerce.entity.OrderItem;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CheckoutServicImplementation implements CheckoutService {

    private CustomerRepository customerRepository;
    private StripeService stripeService;

    //inject the customer repository
    public CheckoutServicImplementation(CustomerRepository customerRepository, StripeService stripeService) {
        this.customerRepository = customerRepository;
        this.stripeService = stripeService;
    }

    @Override
    @Transactional
    //Client → Proxy → TransactionInterceptor → TransactionManager → finally(target method)
    public PurchaseResponse placeOrder(Purchase purchase) {

        // verify payment intent with Stripe
        String paymentIntentId = purchase.getPaymentIntentId();
        if (paymentIntentId != null) {
            try {
                PaymentIntent paymentIntent = stripeService.retrievePaymentIntent(paymentIntentId);
                if (!"succeeded".equals(paymentIntent.getStatus())) {
                    throw new RuntimeException("Payment not succeeded: " + paymentIntent.getStatus());
                }
            } catch (Exception e) {
                throw new RuntimeException("Payment verification failed: " + e.getMessage());
            }
        }

        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate the order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        //populate customer with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this an existing customer
        String theEmail = customer.getEmail();

        //if this customer exist in DB
        Customer customerfromDb = customerRepository.findByEmail(theEmail);

        if(customerfromDb !=null){
            // we found them .. assign them accordingly
            customer = customerfromDb;
        } else {
            // save new customer
            customerRepository.save(customer);
        }

        customer.add(order);

        //save to the database
        customerRepository.save(customer);

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number (UUID version-4)
        return java.util.UUID.randomUUID().toString();
    }

}
