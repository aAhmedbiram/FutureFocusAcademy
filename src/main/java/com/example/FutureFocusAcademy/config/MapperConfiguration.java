package com.example.FutureFocusAcademy.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean(name = "customModelMapper") // Rename the bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
