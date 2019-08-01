package com.zt.annotion.customannotion.config;

import lombok.Data;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author kzh
 * @Description
 * @Date 19-7-11 上午9:31
 */
@Component
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {
    private boolean cluster = false;

    private String address = "redis://localhost:6379";

    private String password;

    private int subscriptionConnectionMinimumIdleSize = 1;

    private int subscriptionConnectionPoolSize = 50;

    private int connectionMinimumIdleSize = 32;

    private int connectionPoolSize = 64;

    private int database = 0;

    private long dnsMonitoringInterval = 5000L;

    private int idleConnectionTimeout = 10000;

    private int connectTimeout = 10000;

    private int timeout = 3000;

    private int retryAttempts = 3;

    private int retryInterval = 1500;

    private int subscriptionsPerConnection = 5;

    private boolean sslEnableEndpointIdentification = true;

    private int scanInterval = 5000;

    private String loadBalancer = "org.redisson.connection.balancer.RoundRobinLoadBalancer";

    private int slaveConnectionMinimumIdleSize = 32;

    private int slaveConnectionPoolSize = 64;

    private int failedSlaveReconnectionInterval = 3000;

    private int failedSlaveCheckInterval = 180000;

    private int masterConnectionMinimumIdleSize = 32;

    private int masterConnectionPoolSize = 64;

    private ReadMode readMode = ReadMode.SLAVE;

    private SubscriptionMode subscriptionMode = SubscriptionMode.MASTER;

}
