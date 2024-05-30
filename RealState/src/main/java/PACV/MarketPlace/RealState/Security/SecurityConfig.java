package PACV.MarketPlace.RealState.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.stream.Collectors;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> {
                    try {
                        authz
                                .requestMatchers("/login**", "/login/refresh**", "/logout**","/**").permitAll()
                                .anyRequest().permitAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                        )
                        .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer.jwt((jwt) ->
                                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                                        .jwkSetUri(jwkSetUri)
                                ))
                .sessionManagement((sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Permite todas as origens com padrão. Modifique conforme necessário.
        config.addAllowedHeader("*"); // Permite todos os cabeçalhos.
        config.addAllowedMethod("*"); // Permite todos os métodos.
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
