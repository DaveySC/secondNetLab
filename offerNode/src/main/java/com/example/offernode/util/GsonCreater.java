package com.example.offernode.util;


import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;

public class GsonCreater {

    @Bean
    public static Gson createGson() {
       return new Gson();
    }
}
