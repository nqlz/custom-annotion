package com.zt.annotion.customannotion.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/31 10:56
 */
@Configuration
@ComponentScan(basePackages="com.zt.annotion.customannotion")
@EnableCaching
public class MainApplicationConfig {
}
