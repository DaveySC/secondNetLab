package com.example.offernode.controller;

import com.example.offernode.entity.Offer;
import com.example.offernode.entity.TypeOfPayment;
import com.example.offernode.service.OfferService;
import com.example.offernode.service.TypeOfPaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestApiController {
    private OfferService offerService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    private TypeOfPaymentService typeOfPaymentService;

    @Autowired
    public void setTypeOfPaymentService(TypeOfPaymentService typeOfPaymentService) {
        this.typeOfPaymentService = typeOfPaymentService;
    }

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }


    @PostMapping("/payment")
    public String returnOffers(HttpEntity<TypeOfPayment[]> httpEntity) {
        List<TypeOfPayment> test = Arrays.asList(httpEntity.getBody());
        List<Offer> offers = offerService.findOffersByTypeOfPayment(test.stream().map(TypeOfPayment::getType).collect(Collectors.toList()));
        return gson.toJson(offers);
    }


    @PostMapping("/get/offers")
    public String getOffers(HttpEntity<String[]> httpEntity) {
        List<Offer> offers = offerService.getAllOffersById(httpEntity.getBody());
        return gson.toJson(offers);
    }


    @PostMapping("/get/price")
    public Long getPrice(HttpEntity<List<Long>> httpEntity) {
        List<Long> ids = httpEntity.getBody();
        Long sum = 0L;
        for(Offer offer : offerService.getAllOffersById(ids)) {
           sum += offer.getPrice();
        }
        return sum;


    }
}
