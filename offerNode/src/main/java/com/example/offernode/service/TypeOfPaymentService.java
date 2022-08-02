package com.example.offernode.service;

import com.example.offernode.entity.TypeOfPayment;
import com.example.offernode.repository.TypeOfPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeOfPaymentService {
    private TypeOfPaymentRepository repository;

    @Autowired
    public void setRepository(TypeOfPaymentRepository repository) {
        this.repository = repository;
    }

    public TypeOfPayment findTypeOfPaymentByType(String type) {
        return repository.findByType(type);
    }
}
