package com.projeto.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.ads.model.Turma;
import com.projeto.ads.model.TurmaAluno;

import jakarta.transaction.Transactional;

public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {

	@Transactional
	public void deleteByTurma(Turma turma);
}
