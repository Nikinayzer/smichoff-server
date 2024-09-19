package nikinayzer.smichoffserver.security;

import nikinayzer.smichoffserver.security.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF because project uses JWT
                .authorizeHttpRequests((requests) -> requests.requestMatchers(
                                "/auth",
                                "/users/register",
                                //"/routes/all",
                                "/route/{id}",
                                "/error").permitAll().
                        requestMatchers("/admin/**").hasRole("ADMIN").
                        requestMatchers("/users/all").hasRole("ADMIN").
                        anyRequest().authenticated()).
                formLogin(AbstractHttpConfigurer::disable).
                sessionManagement(session -> session.sessionCreationPolicy(STATELESS)).
                authenticationProvider(authenticationProvider).
                addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .httpBasic(Customizer.withDefaults())
        //.logout(LogoutConfigurer::permitAll);


        return http.build();
    }

}