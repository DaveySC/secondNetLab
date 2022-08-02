package com.example.customertsnode.util;

import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.service.TypeOfPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeOfPaymentConverter implements Converter<String, TypeOfPayment> {
    private TypeOfPaymentService service;

    @Autowired
    public void setService(TypeOfPaymentService service) {
        this.service = service;
    }

    @Override
    public TypeOfPayment convert(String id) {
        return service.findById(Long.parseLong(id));
    }
}
