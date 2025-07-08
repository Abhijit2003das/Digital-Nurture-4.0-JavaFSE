package com.cognizant.spring_learn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class XmlConfig {

    @Bean
    ClassPathXmlApplicationContext xmlContext() {
        return new ClassPathXmlApplicationContext("country.xml");
    }

    @Bean
    Country country() {
        return xmlContext().getBean("country", Country.class);
    }
}

