package com.moh.yehia.apigateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class ResponseFilter {
    @Bean
    public GlobalFilter postFilter() {
        return (exchange, chain) ->
                chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
                    String correlationId = Objects.requireNonNull(httpHeaders.get("X-Correlation-ID")).stream().findFirst().orElse(null);
                    exchange.getResponse().getHeaders().add("X-Correlation-ID", correlationId);
                }));
    }
}
