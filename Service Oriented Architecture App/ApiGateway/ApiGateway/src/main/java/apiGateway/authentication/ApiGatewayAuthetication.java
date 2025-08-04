package apiGateway.authentication;


import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

import api.dtos.UserDto;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewayAuthetication {
	
	@Autowired
	private  CustomAuthenticationHandler customAuthenticationHandler;

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

   
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeExchange(exchange -> exchange
                .pathMatchers("/currency-exchange").permitAll()
                .pathMatchers("/crypto-exchange").permitAll()
                .pathMatchers("/currency-conversion").hasRole("USER")
                .pathMatchers("/crypto-conversion").hasRole("USER")
                .pathMatchers("/trade-service").hasRole("USER")
                .pathMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN", "OWNER")
                .pathMatchers(HttpMethod.PUT, "/users").hasAnyRole("ADMIN", "OWNER")
                .pathMatchers(HttpMethod.DELETE, "/users/**").hasRole("OWNER")
                .pathMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "OWNER")
                .pathMatchers(HttpMethod.POST, "/bank-account/create").hasAnyRole("ADMIN")
                .pathMatchers(HttpMethod.PUT, "/bank-account/update").hasAnyRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/bank-account").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "bank-account/myAccount").hasRole("USER")
                .pathMatchers(HttpMethod.POST, "/crypto-wallet/create").hasAnyRole("ADMIN")
                .pathMatchers(HttpMethod.PUT, "/crypto-wallet/update").hasAnyRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/crypto-wallet").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "crypto-wallet/myWallet").hasRole("USER")
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(customAuthenticationHandler)
                .authenticationEntryPoint(customAuthenticationHandler)
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
	}
	
	
	@Bean
	ReactiveUserDetailsService userDetailsService(RestTemplate restTemplate, BCryptPasswordEncoder encoder) {
	    return username -> {
	        try {
	            ResponseEntity<List<UserDto>> response =
	                restTemplate.exchange("http://users-service-service:8770/users", HttpMethod.GET,
	                    null, new ParameterizedTypeReference<List<UserDto>>() {});
	            
	            for (UserDto user : response.getBody()) {
	                if (user.getEmail().equalsIgnoreCase(username)) {
	                    return Mono.just(User.withUsername(user.getEmail())
	                        .password(encoder.encode(user.getPassword()))
	                        .roles(user.getRole())
	                        .build());
	                }
	            }
	            return Mono.error(new UsernameNotFoundException("User not found"));
	        } catch (Exception ex) {
	            return Mono.error(new IllegalStateException("Could not fetch user list", ex));
	        }
	    };
	}

	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}