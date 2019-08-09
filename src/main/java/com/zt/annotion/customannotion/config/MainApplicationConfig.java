package com.zt.annotion.customannotion.config;

import com.zt.annotion.customannotion.annotion.EnableGlobalLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/31 10:56
 */
@Configuration
@ComponentScan(basePackages="com.zt.annotion.customannotion")
@EnableCaching
@EnableGlobalLock
public class MainApplicationConfig {

    @Autowired
    private RestTemplateBuilder builder;
    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}
