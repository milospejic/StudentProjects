package apiGateway.authentication;


import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

import api.dtos.UserDto;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewayAuthetication {
	
	@Autowired
	private  CustomAuthenticationHandler customAuthenticationHandler;

	@Autowired
	private RestTemplate restTemplate; // Injected bean

	@Bean
	@LoadBalanced // This makes the RestTemplate service-discovery-aware
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
	MapReactiveUserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
		ResponseEntity<List<UserDto>> response =
				//Obratiti paznju na URL prilikom dockerizacije
				//U dokera vrednost je users-service:8770/users
				//van dokera vrednost mora biti loaclhost:8770/users
				restTemplate.exchange("http://users-service:8770/users", HttpMethod.GET,
						null, new ParameterizedTypeReference<List<UserDto>>() {});
		
		List<UserDetails> users = new ArrayList<UserDetails>();
		for(UserDto user : response.getBody()) {
			users.add(
					User.withUsername(user.getEmail())
					.password(encoder.encode(user.getPassword()))
					.roles(user.getRole())
					.build());

		}

		return new MapReactiveUserDetailsService(users);

	}
	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}