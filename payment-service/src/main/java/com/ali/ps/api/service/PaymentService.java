package com.ali.ps.api.service;

import com.ali.ps.api.entity.Payment;
import com.ali.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
