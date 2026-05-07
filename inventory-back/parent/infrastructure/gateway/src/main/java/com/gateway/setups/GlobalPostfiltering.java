package com.gateway.setups;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalPostfiltering {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalPostfiltering.class);

    @Bean
    public GlobalFilter postGlobalFilter () {
        return ((exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("global postfilter executed");
                    }));
        });
    }
}