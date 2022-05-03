package com.example.offernode.service;

import com.example.offernode.entity.Offer;
import com.example.offernode.entity.TypeOfPayment;
import com.example.offernode.repository.OfferRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class OfferService{

    private OfferRepository offerRepository;

    @Autowired
    public void setOfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public void createOrUpdateOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void deleteOffer(Offer offer) {
        offerRepository.delete(offer);
    }

    public List<Offer> findOffersByTypeOfPayment(String type) {
        return offerRepository.findAllByTypeOfPayment(type);
    }
}
