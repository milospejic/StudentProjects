package apiGateway.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAuthenticationHandler implements ServerAccessDeniedHandler, ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, org.springframework.security.access.AccessDeniedException denied) {
        return buildErrorResponse(exchange, HttpStatus.FORBIDDEN, "Access Denied");
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return buildErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("statusCode", status.value());
        errorResponse.put("status", status.getReasonPhrase().toUpperCase());
        errorResponse.put("message", message);

        return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(toJsonBytes(errorResponse)))
        );
    }

    private byte[] toJsonBytes(Map<String, Object> errorResponse) {
        try {
            return objectMapper.writeValueAsBytes(errorResponse);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
