package apiGateway.authentication;

import api.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewayAuthetication {

    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
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
    public MapReactiveUserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
        // Use the reactive WebClient to get the user data
        List<UserDto> userDtoList = webClientBuilder.build()
                .get()
                .uri("http://users-service:8770/users")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserDto>>() {})
                .block(); // .block() is acceptable here during application startup

        List<UserDetails> users = new ArrayList<>();
        if (userDtoList != null) {
            for (UserDto userDto : userDtoList) {
                users.add(
                        User.withUsername(userDto.getEmail())
                                .password(encoder.encode(userDto.getPassword()))
                                .roles(userDto.getRole())
                                .build());
            }
        }

        return new MapReactiveUserDetailsService(users);
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}