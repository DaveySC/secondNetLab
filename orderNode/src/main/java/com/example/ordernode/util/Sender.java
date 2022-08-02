package com.example.ordernode.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Sender {

    public static <T> ResponseEntity<String> send(T data, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<Object>(data,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) return responseEntity;
        return null;
    }



}
