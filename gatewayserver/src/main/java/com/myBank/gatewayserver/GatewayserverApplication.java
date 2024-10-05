package com.myBank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator myBankRouteConfig(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(p -> p
						.path("/mybank/accounts/**")
						.filters(f -> f.rewritePath("/mybank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/mybank/loans/**")
						.filters(f -> f.rewritePath("/mybank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/mybank/cards/**")
						.filters(f -> f.rewritePath("/mybank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();


	}

}
