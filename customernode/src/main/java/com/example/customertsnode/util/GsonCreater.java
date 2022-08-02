package com.example.customertsnode.util;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;

public class GsonCreater {

    @Bean
    public Gson createGson() {
        return new Gson();
    }
}
