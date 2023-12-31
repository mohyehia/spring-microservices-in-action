package com.moh.yehia.apigateway.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpanExporterConfig {
    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${jaeger.tracing.url}") String url) {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build();
    }
}
