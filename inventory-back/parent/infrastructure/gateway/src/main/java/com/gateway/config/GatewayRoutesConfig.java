package com.gateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//setting up the reoutes
@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                // auth route for login/signup ---------------------------------------------------------------------------------
                .route("auth-route", r -> r
                        .path("/auth/**")//controller name
                        .uri("lb://USER") //microservice name
                )

                //authorization path for user (validation)--------------------------------------------------------------
                .route(p -> p
                        .path("/users/**")
                        .filters(f -> f.addRequestHeader("user-service", "Request"))
                        .uri("lb://USER")) //microservice name

                //Inventory  service --------------------------------------------------------------------------------------
                .route("inventory", r -> r
                        .path("/api/inventory/**")//controller name
                        .uri("lb://INVENTORY")//microservice name
                )
                //Inventory-AI  service --------------------------------------------------------------------------------------
                .route("inventory-ai", r -> r
                        .path("/ai/**")//controller name
                        .uri("lb://INVENTORY-AI-SERVICE")//microservice name
                )
                //movements  service --------------------------------------------------------------------------------------
                .route("movements", r -> r
                        .path("/api/movements/**")//controller name
                        .uri("lb://MOVEMENTS")//microservice name
                )

                .build();
    }
}