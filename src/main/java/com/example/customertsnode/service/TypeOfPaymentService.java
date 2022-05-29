package com.example.customertsnode.service;

import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.repository.TypeOfPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfPaymentService {
    private TypeOfPaymentRepository repository;

    @Autowired
    public void setRepository(TypeOfPaymentRepository repository) {
        this.repository = repository;
    }

    public List<TypeOfPayment> findAll() {
        return repository.findAll();
    }


    public TypeOfPayment findById(Long id) {return repository.findById(id).get();}
}
