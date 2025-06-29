package com.projeto.ads.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/css/**", "/js/**").permitAll()
						.requestMatchers("/usuario/inserir").permitAll().requestMatchers("/usuario/recuperarSenha")
						.permitAll().requestMatchers("/usuario/atualizarUsuario").permitAll().anyRequest()
						.authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
						.successHandler((request, response, authentication) -> response.sendRedirect("/dashboard"))
						.permitAll());

		return http.build();
	}
}