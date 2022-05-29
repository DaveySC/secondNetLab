package com.example.offernode.service;

import com.example.offernode.entity.Offer;
import com.example.offernode.entity.TypeOfPayment;
import com.example.offernode.repository.OfferRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Offer> findOffersByTypeOfPayment(List<String> type) {
        return offerRepository.findAllByTypeOfPayment(type);
    }

    public List<Offer> getAllOffersById(String[] ids) {
        List<Offer> offers = new ArrayList<>();
        for (String id : ids) {
            Optional<Offer> optionalOffer =   offerRepository.findById(Long.parseLong(id));
            optionalOffer.ifPresent(offers::add);
        }
        return offers;
    }


    public List<Offer> getAllOffersById(List<Long> ids) {
        List<Offer> offers = new ArrayList<>();
        for (Long id : ids) {
            Optional<Offer> optionalOffer =   offerRepository.findById(id);
            optionalOffer.ifPresent(offers::add);
        }
        return offers;
    }
}
