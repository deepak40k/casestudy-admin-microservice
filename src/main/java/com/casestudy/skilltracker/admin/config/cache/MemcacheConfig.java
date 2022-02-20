package com.casestudy.skilltracker.admin.config.cache;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@EnableCaching
@Configuration
@Slf4j
public class MemcacheConfig extends CachingConfigurerSupport {

    @Value("${memcached.port}")
    private Integer port;
    @Value("${memcached.configEndpoint}")
    private String configEndpoint;
    @Bean
    public MemcachedClient memcachedClient(){
        try {
            return new MemcachedClient(new InetSocketAddress(configEndpoint,port));
        }catch (Exception e ){
            log.error(e.getMessage());
            try {
                return new MemcachedClient(new InetSocketAddress("localhost",port));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}