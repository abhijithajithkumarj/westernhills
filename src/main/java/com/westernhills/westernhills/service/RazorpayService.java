package com.westernhills.westernhills.service;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.apiKey}")
    private String apiKey;

    @Value("${razorpay.apiSecret}")
    private String apiSecret;

    public Order createOrder(double  amount, String currency) throws RazorpayException {

        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", currency);
            Order order = razorpayClient.orders.create(orderRequest);
            return order;




        } catch (Exception e) {
            throw new RazorpayException("Failed to create Order" + e.getMessage());
        }

    }





}






