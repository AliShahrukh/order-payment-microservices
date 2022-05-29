package com.ali.os.api.service;

import com.ali.os.api.dto.Payment;
import com.ali.os.api.dto.TransactionRequest;
import com.ali.os.api.dto.TransactionResponse;
import com.ali.os.api.entity.Order;
import com.ali.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate template;

    public TransactionResponse saveOrder(TransactionRequest request) {

        String message = null;
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);

        if("success".equals(paymentResponse.getPaymentStatus())){
            message = "Payment Successful";
        } else {
            message = "Payment Failed";
        }
        orderRepository.save(order);

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), message);
    }
}
