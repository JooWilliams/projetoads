package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.projeto.ads.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	
	@Query("SELECT p FROM Professor p WHERE p.id=(SELECT MAX(p2.id) FROM Professor p2)")
	public Professor findLastInsertedProfessor();
	
	@Query("SELECT p FROM Professor p WHERE p.cpf=:cpf")
	public Professor findByCpf(String cpf);
	
	@Query("SELECT p FROM Professor p WHERE p.email=:email")
	public Professor findByEmail(String email);
	
	@Query("SELECT p FROM Professor p ORDER BY p.id")
	public List<Professor> findAllOrderedById();
	

}
