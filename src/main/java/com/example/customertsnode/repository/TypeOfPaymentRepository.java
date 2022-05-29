package com.example.customertsnode.repository;

import com.example.customertsnode.entity.TypeOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfPaymentRepository extends JpaRepository<TypeOfPayment, Long> {

     List<TypeOfPayment> findAll();



}
