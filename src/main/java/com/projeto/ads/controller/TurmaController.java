package com.projeto.ads.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.model.Turma;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.repository.ProfessorRepository;
import com.projeto.ads.repository.TurmaRepository;
import com.projeto.ads.service.ServiceTurma;

@Controller
public class TurmaController {

	@Autowired
	ProfessorRepository professorRepository;
	
	@Autowired
	ServiceTurma serviceTurma;
	
	@Autowired
	TurmaRepository turmaRepository;
	
	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping("/turma/inserir")
	public ModelAndView inserirTurmaGet() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professores", professorRepository.findAllOrderedByNome());
		mv.addObject("turma", new Turma());
		mv.setViewName("Turma/inserirTurma");
		return mv;
	}

	@PostMapping("/turma/inserir")
	public ModelAndView inserirTurmaPost(Turma turma) {
		ModelAndView mv = new ModelAndView();
		turma.setCodTurma(serviceTurma.gerarCodigo(turma.getCurso().toString().toLowerCase(),
				turma.getPeriodo().toString().toLowerCase(), turma.getTurno().toLowerCase()));
		turmaRepository.save(turma);
		mv.setViewName("redirect:/turma/listar");
		return mv;
	}

	@GetMapping("/turma/listar")
	public ModelAndView listarTurmas() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("turmas", turmaRepository.findAllOrdenById());
		mv.setViewName("Turma/listarTurmas");
		return mv;
	}

	@GetMapping("/turma/inserirAlunosTurma")
	public ModelAndView carregarForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Turma/inserirAlunoTurma");
		mv.addObject("turmas", turmaRepository.findAllOrderByCodTurma());
		mv.addObject("alunos", List.of());
		mv.addObject("turma", new Turma());
		return mv;
	}

	@GetMapping("/turma/buscarAlunos/{id}")
	@ResponseBody
	public List<Aluno> buscarAlunos(@PathVariable("id") Long turmaId) {
		return turmaRepository.findById(turmaId)
				.map(turma -> alunoRepository.findByCursoAndTurnOrderByNome(turma.getCurso(), turma.getTurno()))
				.orElse(List.of());
	}

	// MÉTODO QUE ESTAVA FALTANDO - Para listar alunos de uma turma em uma página
	@GetMapping("/turma/listarAlunos/{id}")
	public ModelAndView listarAlunosDaTurma(@PathVariable("id") Long turmaId) {
		ModelAndView mv = new ModelAndView();
		
		// Buscar a turma pelo ID
		Turma turma = turmaRepository.findById(turmaId).orElse(null);
		
		if (turma == null) {
			mv.setViewName("redirect:/turma/listar");
			return mv;
		}
		
		// Buscar os alunos da turma
		List<Aluno> alunos = turma.getAlunos(); // Assumindo que existe o relacionamento
		
		mv.addObject("turma", turma);
		mv.addObject("alunos", alunos);
		mv.setViewName("Turma/listarAlunosTurma"); // Nome da view que você precisa criar
		
		return mv;
	}

	@PostMapping("/turma/inserirAlunosTurma")
	public ModelAndView inserirAlunosTurma(Turma turma, @RequestParam List<Long> alunosSelecionados) {
		ModelAndView mv = new ModelAndView();
		Turma auxturma = turmaRepository.findById(turma.getId()).get();
		serviceTurma.salvarTurmaComAlunos(auxturma, alunosSelecionados);
		mv.setViewName("redirect:/turma/listar");
		return mv;
	}
}