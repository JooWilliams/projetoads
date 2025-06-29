package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.service.ServiceAluno;

@Controller
public class AlunoController {

	@Autowired
	ServiceAluno serviceAluno;
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@GetMapping("/aluno/inserir")
	public ModelAndView GetInserirAluno() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("aluno", new Aluno());
		mv.setViewName("Aluno/inserirAluno");
		return mv;
	}
	
	@GetMapping("/aluno/listar")
	public ModelAndView GetListarAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAlunos.html");
		return mv;
	}
	
	@GetMapping("/aluno/deletar")
	public ModelAndView GetDeletarAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAlunos.html");
		return mv;
	}
	
	@GetMapping("/aluno/editar")
	public ModelAndView GetEditarAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAlunos.html");
		return mv;
	}
	
	@PostMapping("/aluno/inserir")
	public ModelAndView PostInserirAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		String saida= serviceAluno.cadastrarAluno(aluno);
		if(saida!=null) {
			mv.addObject("msg", saida);
			mv.addObject("aluno", new Aluno());
			mv.addObject("aluno", aluno);
			mv.setViewName("Aluno/inserirAluno");
		}//fim if
		else {
			mv.setViewName("redirect:/aluno/listar");
		}
		return mv;
	}//fim post
	
	@GetMapping("/aluno/alterar/{id}")
	public ModelAndView alterarAlunoGet(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Aluno aluno = alunoRepository.findById(id).get();
		mv.addObject("aluno", aluno);
		mv.setViewName("Aluno/alterarAluno");
		return mv;
	}
	
	@PostMapping("/aluno/alterar")
	public ModelAndView alterarAlunoPost(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		String out= serviceAluno.atualizarAluno(aluno);
		if(out!=null) {
			mv.addObject("msg", out);
			mv.addObject("aluno", aluno);
			mv.setViewName("Aluno/alterarAluno");
		}
		else {
			mv.setViewName("redirect:/aluno/listar");
		}
		return mv;
	}//fim alterar
	
	@GetMapping("/aluno/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		alunoRepository.deleteById(id);
		return "redirect:/aluno/listar";
	}
}//fim controller
