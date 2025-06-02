package com.projeto.ads.security;

//Importações do Spring
import org.springframework.context.annotation.Bean;
//Importa a anotação @Bean do Spring para a configuração de beans
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//Importa a anotação @Configuration do Spring para indicar que a classe é uma configuração
import org.springframework.security.authentication.AuthenticationManager;
//Importa a interface AuthenticationManager do Spring Security
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//Importa a classe AuthenticationConfiguration para configuração de autenticação
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//Importa a classe HttpSecurity do Spring Security para configuração de segurança HTTP
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//Importa a implementação BCryptPasswordEncoder do Spring Security para codificar senhas
import org.springframework.security.crypto.password.PasswordEncoder;
//Importa a interface PasswordEncoder do Spring Security para codificação de senhas
import org.springframework.security.web.SecurityFilterChain;

//Importa a classe SecurityFilterChain do Spring Security para configuração do filtro de segurança
@Configuration
public class SecurityConfig {
	@Bean
	public static PasswordEncoder passwordEncoder() { // usado encode e decode
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth ) 
			throws Exception { //gerencia autenticação
		return auth.getAuthenticationManager();
	}//fim metodo 
	
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http)throws Exception {
		 http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize ->
				authorize
				.requestMatchers("/css/**", "/js/**").permitAll()
				.requestMatchers("/usuario/inserir").permitAll()
				.requestMatchers("/usuario/recuperarSenha").permitAll()
				.anyRequest().authenticated()
				.requestMatchers("/usuario/atualizarUsuario").permitAll()
			)
			.formLogin(form -> 
				form
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.successHandler((request, response, authentication)->response.sendRedirect("/dashboard"))
					.permitAll()
			);
		
		return http.build();
			
	}//fim metodo
}// fim class