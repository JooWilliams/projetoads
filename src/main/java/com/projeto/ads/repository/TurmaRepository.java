package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.ads.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query("SELECT t FROM Turma t WHERE t.codTurma = :codTurma")
	Turma findByCodTurma(String codTurma);

	@Query("SELECT t FROM Turma t ORDER BY t.id")
	List<Turma> findAllOrdenById(); // Se quiser, pode renomear para findAllOrderById

	@Query("SELECT t FROM Turma t WHERE t.id = (SELECT MAX(t2.id) FROM Turma t2)")
	Turma findLastInsertTurma();

	@Query("SELECT t FROM Turma t ORDER BY t.codTurma")
	List<Turma> findAllOrderByCodTurma();

}
