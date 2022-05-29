package com.example.ordernode.repository;

import com.example.ordernode.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer findOfferById(Long id);

}
