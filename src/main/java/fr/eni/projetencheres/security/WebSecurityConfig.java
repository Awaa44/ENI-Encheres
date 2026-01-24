package fr.eni.projetencheres.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/accueil", "/css/*", "/images/*",
                                "/inscription", "/login", "/detail_vente/**", "/all-articles-vendus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/nouvelle_vente").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/nouvelle_vente").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/inscription").permitAll()
                        .requestMatchers(HttpMethod.POST, "/inscription").permitAll()
                        .requestMatchers(HttpMethod.POST, "/filtre").permitAll()
                        .requestMatchers(HttpMethod.GET, "/venteRemportee").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/accueil", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout((logout) -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/accueil")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
