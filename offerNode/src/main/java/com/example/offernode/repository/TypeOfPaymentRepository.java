package com.example.offernode.repository;

import com.example.offernode.entity.TypeOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfPaymentRepository extends JpaRepository<TypeOfPayment, Long> {
    TypeOfPayment findByType (String type);
}
