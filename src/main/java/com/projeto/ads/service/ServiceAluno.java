package com.projeto.ads.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.repository.AlunoRepository;

@Service
public class ServiceAluno {
	@Autowired
	AlunoRepository alunoRepository;
	
	//SENAC2025x
	public String gerarMatricula(int id) {
		Date data = new Date();
		Calendar calendario= Calendar.getInstance();
		calendario.setTime(data);
		int ano= calendario.get(Calendar.YEAR);
		return ("SENAC"+ano+(id+1));
	}//fim metodo
	
	public String cadastrarAluno(Aluno aluno) {
		//verificar se já existe um aluno cadastrado com o cpf
		Aluno alunoExistente= alunoRepository.findByCpf(aluno.getCpf());
		Aluno emailExiste= alunoRepository.findByEmail(aluno.getEmail());
		if(emailExiste!=null) {
			return "Já existe um aluno com o mesmo email!";
		}//fim if
		if(alunoExistente==null) {
			Aluno aux= alunoRepository.findLastInsertedAluno();
			if(aux != null) {
				aluno.setMatricula(this.gerarMatricula(Integer.parseInt(aux.getId().toString())));
			}
			else {//quando nao existe nenhum aluno na tabela
				aluno.setMatricula(this.gerarMatricula(0));// SENAC20251
			}
			alunoRepository.save(aluno);
		}//fim if nao existe aluno com o mesmo cpf
		else {
			return "Já existe um aluno com o mesmo cpf!";
		}
		return null;//condição onde deu certo e o aluno foi inserido na base
	}//fim metodo
	
	public String atualizarAluno(Aluno aluno) {
		//verificar se já existe um aluno cadastrado com o cpf
		Aluno alunoExistente= alunoRepository.findByCpf(aluno.getCpf());
		Aluno emailExiste= alunoRepository.findByEmail(aluno.getEmail());
		if(alunoExistente!=null) {
			if(!alunoExistente.getMatricula().equals(aluno.getMatricula()))
				return "Já existe um aluno com o mesmo cpf!";
		}//fim if
		if(emailExiste!=null) {
			if(!emailExiste.getMatricula().equals(aluno.getMatricula()))
				return "Já existe um aluno com o mesmo email!";
		}//fim if
		alunoRepository.save(aluno);
		
		return null;//condição onde deu certo e o aluno foi inserido na base
	}//fim metodo
}//fim class 
