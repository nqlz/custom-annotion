package com.zt.annotion.customannotion.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.connection.balancer.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author kzh
 * @Description
 * @Date 19-7-10 下午5:55
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean("redissonClient")
    @ConditionalOnProperty(name = "redisson.cluster", havingValue = "false", matchIfMissing = true)
    public RedissonClient redissonSingleClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redissonProperties.getAddress().split(",")[0])
                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setDatabase(redissonProperties.getDatabase())
                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setSslEnableEndpointIdentification(redissonProperties.isSslEnableEndpointIdentification());
        if(StringUtils.isNotBlank(redissonProperties.getPassword())) {
            config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean("redissonClient")
    @ConditionalOnProperty(name = "redisson.cluster", havingValue = "true")
    public RedissonClient redissonClient() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(redissonProperties.getAddress().split(","))
                .setScanInterval(redissonProperties.getScanInterval())
                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setTimeout(redissonProperties.getTimeout())
                .setRetryAttempts(redissonProperties.getRetryAttempts())
                .setRetryInterval(redissonProperties.getRetryInterval())
                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
                .setLoadBalancer((LoadBalancer) Class.forName(redissonProperties.getLoadBalancer()).newInstance())
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setFailedSlaveReconnectionInterval(redissonProperties.getFailedSlaveReconnectionInterval())
                .setFailedSlaveCheckInterval(redissonProperties.getFailedSlaveCheckInterval())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setReadMode(redissonProperties.getReadMode())
                .setSubscriptionMode(redissonProperties.getSubscriptionMode())
                .setSslEnableEndpointIdentification(redissonProperties.isSslEnableEndpointIdentification())
        ;
        if(StringUtils.isNotBlank(redissonProperties.getPassword())) {
            config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
