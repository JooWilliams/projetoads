package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.ads.Enum.Curso;
import com.projeto.ads.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	@Query("SELECT a FROM Aluno a WHERE a.id = (SELECT MAX(a2.id) FROM Aluno a2)")
	public Aluno findLastInsertedAluno();

	@Query("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
	public Aluno findByCpf(@Param("cpf") String cpf);

	@Query("SELECT a FROM Aluno a WHERE a.email = :email")
	public Aluno findByEmail(@Param("email") String email);

	@Query("SELECT a FROM Aluno a ORDER BY a.id")
	public List<Aluno> findAllOrderedById();

	@Query("SELECT a FROM Aluno a ORDER BY a.nome")
	public List<Aluno> findAllOrderedByNome();

	@Query("SELECT a FROM Aluno a WHERE a.curso = :curso AND a.turno = :turno  ORDER BY a.nome")
	public List<Aluno> findByCursoAndTurnOrderByNome(@Param("curso") Curso curso, @Param("turno") String turno);

	@Query("SELECT a FROM Aluno a WHERE a.curso = :curso ORDER BY a.nome")
	public List<Aluno> findByCursoOrderByNome(@Param("curso") Curso curso);

}
