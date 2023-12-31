package com.moh.yehia.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {
    @Bean
    public KeyResolver keyResolver() {
        return exchange -> Mono.just("1");
    }
}
