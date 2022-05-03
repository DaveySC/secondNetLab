package com.example.offernode.repository;

import com.example.offernode.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findByName(String name);

    @Query(value = "select offer.id, offer.created, offer.updated, offer.name, offer.description, offer.price, offer.type_of_payment_id from offer inner join type_of_payment on type_of_payment.id = offer.id where type_of_payment.type = :type",
            nativeQuery = true)
    List<Offer> findAllByTypeOfPayment(@Param("type") String type);



}

