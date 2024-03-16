package com.demo.microservices.microservicesapigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservicesApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesApiGatewayServerApplication.class, args);
	}

	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("currency-conversion-service",r -> r.path("/currency-conversion-service/**")
                		//.filters(f -> f.prefixPath("/currency-conversion-service"))
                	    .filters(f -> f.rewritePath("/currency-conversion-service/(?<segment>.*)", "/${segment}"))
                	    .uri("lb://currency-conversion-service"))
                .route("currency-conversion-service2",r -> r.path("/currency-conversion-service2/**")
                		//.filters(f -> f.prefixPath("/currency-conversion-service"))
                	    .filters(f -> f.rewritePath("/currency-conversion-service2/(?<segment>.*)", "/${segment}"))
                	    .uri("lb://currency-conversion-service"))
              //  .route("service2", r -> r.path("/service2/**")
               //         .uri("lb://service2"))
                // Add more routes for other services as needed
                .build();
    }
	
	@Bean  
	public Sampler defaultSampler(){  
		return Sampler.ALWAYS_SAMPLE;  
	} 
}
