package com.projeto.ads.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.model.Turma;
import com.projeto.ads.model.TurmaAluno;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.repository.TurmaAlunoRepository;
import com.projeto.ads.repository.TurmaRepository;

@Service
public class ServiceTurma {

	@Autowired
	TurmaRepository turmaRepository;

	@Autowired
	TurmaAlunoRepository turmaAlunoRepository;

	@Autowired
	AlunoRepository alunoRepository;

	public String gerarCodigo(String curso, String periodo, String turno) {
		Date data = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		int ano = calendario.get(Calendar.YEAR);
		Turma turma = turmaRepository.findLastInsertTurma();
		if (turma == null) {
			return curso + "-" + periodo + "-" + turno + "-" + ano + "1";
		} else {
			return curso + "-" + periodo + "-" + turno + "-" + ano + "" + turma.getId() + 1;
		}

	}

	public void salvarTurmaComAlunos(Turma turma, List<Long> alunosSelecionandos) {
		Turma turmaSalvar = turmaRepository.save(turma);

		turmaAlunoRepository.deleteByTurma(turmaSalvar);

		List<Aluno> alunos = alunoRepository.findAllById(alunosSelecionandos);

		List<TurmaAluno> turmaAlunos = alunos.stream().map(aluno -> {

			TurmaAluno turmaAluno = new TurmaAluno();
			turmaAluno.setTurma(turmaSalvar);
			turmaAluno.setAluno(aluno);
			return turmaAluno;
		})

				.collect(Collectors.toList());
		turmaAlunoRepository.saveAll(turmaAlunos);

	}
}
