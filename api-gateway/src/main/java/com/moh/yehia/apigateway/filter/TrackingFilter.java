package com.moh.yehia.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TrackingFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey("X-Correlation-ID")) {
            String correlationId = UUID.randomUUID().toString();
            exchange.mutate()
                    .request(builder -> builder.headers(httpHeaders -> httpHeaders.add("X-Correlation-ID", correlationId)))
                    .build();
        }
        return chain.filter(exchange);
    }
}
