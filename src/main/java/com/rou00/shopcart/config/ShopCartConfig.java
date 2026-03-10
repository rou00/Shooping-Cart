package com.rou00.shopcart.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopCartConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
