package com.projeto.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.ads.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {
	// Método personalizado para encontrar um usuário pelo nome de usuário
	
	public Usuario findByUsername(String args);
	
	@Query("SELECT u FROM Usuario u WHERE u.email=:arg")
	public Usuario findByEmail(String arg);
	
}
