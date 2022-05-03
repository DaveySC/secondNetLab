package com.example.offernode.controller;

import com.example.offernode.entity.Offer;
import com.example.offernode.entity.TypeOfPayment;
import com.example.offernode.service.OfferService;
import com.example.offernode.service.TypeOfPaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ResApiController {
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
        List<TypeOfPayment> test = Arrays.asList(Objects.requireNonNull(httpEntity.getBody()));
        for (TypeOfPayment type : test) {
            System.out.println(type);
        }

        List<Offer> offers = offerService.findOffersByTypeOfPayment("cash");
        return gson.toJson(offers);
    }

}
