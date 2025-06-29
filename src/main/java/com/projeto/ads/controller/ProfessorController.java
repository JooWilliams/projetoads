package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.model.Professor;
import com.projeto.ads.repository.ProfessorRepository;
import com.projeto.ads.service.ServiceProfessor;

@Controller
public class ProfessorController {
	
	@Autowired
	ServiceProfessor serviceProfessor;
	
	@Autowired
	ProfessorRepository professorRepository;
	
	@GetMapping("/professor/inserir")
	public ModelAndView inserirPorfGet() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professor", new Professor());
		mv.setViewName("Professor/inserirProfessor");
		return mv;
		
	}
	
	@PostMapping("/professor/inserir")
	public ModelAndView inserirProfPost(@ModelAttribute Professor professor) {
		
		ModelAndView mv = new ModelAndView();
		String saida = serviceProfessor.cadastraProfessor(professor);
		if(saida!=null) {
			mv.addObject("professor", professor);
			mv.addObject("msg",saida);
			mv.setViewName("Professor/inserirProfessor");
			return mv;
			
		}else {
			mv.setViewName("redirect:/professor/listar");
			return mv;
		}
		
		
		
	}
	@GetMapping("/professor/listar")
	public ModelAndView GetListarProfessors() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professores", professorRepository.findAllOrderedById());
		mv.setViewName("Professor/listarProfessores.html");
		return mv;
	}
	
	@GetMapping("/professor/alterar/{id}")
	public ModelAndView alterarProfGet(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Professor prof= professorRepository.findById(id).get();
		mv.addObject("professor",prof);
		mv.setViewName("professor/alterarProfessor");
		return mv;
	}
	
	// Método POST para processar a alteração
	@PostMapping("/professor/alterar")
	public ModelAndView alterarProfPost(@ModelAttribute Professor professor) {
	    ModelAndView mv = new ModelAndView();
	    String msg = serviceProfessor.atualizarProfessor(professor);

	    if (msg == null) {
	        // Se não houver erro, redireciona para listagem ou outra página
	        mv.setViewName("redirect:/professor/listar");
	    } else {
	        // Se houver erro, volta para o formulário de alteração com a mensagem
	        mv.addObject("msg", msg);
	        mv.addObject("professor", professor);
	        mv.setViewName("professor/alterarProfessor");
	    }
	    return mv;
	}
	
	@GetMapping("/professor/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
	    professorRepository.deleteById(id);
	    return "redirect:/professor/listar";
	}
	@GetMapping("/professor/deletar")
	public ModelAndView GetDeletarProfessores() {
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("professores", professorRepository.findAllOrderedById());
	    mv.setViewName("Professor/listarProfessores.html");
	    return mv;
	}

	@GetMapping("/professor/editar")
	public ModelAndView GetEditarProfessores() {
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("professores", professorRepository.findAllOrderedById());
	    mv.setViewName("Professor/listarProfessores.html");
	    return mv;
	}


}
