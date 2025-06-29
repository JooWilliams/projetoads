package com.projeto.ads.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ads.model.Professor;
import com.projeto.ads.repository.ProfessorRepository;

@Service
public class ServiceProfessor {
	
	@Autowired
	ProfessorRepository professorRepository;
	
	//SENAC2025x
		public String gerarMatricula(int id) {
			Date data = new Date();
			Calendar calendario= Calendar.getInstance();
			calendario.setTime(data);
			int ano= calendario.get(Calendar.YEAR);
			return ("SENAC"+ano+(id+1));
		}//fim metodo
		
		public String cadastraProfessor(Professor professor) {
		    String out = "";
		    Professor aux = professorRepository.findByCpf(professor.getCpf());
		    Professor aux2 = professorRepository.findByEmail(professor.getEmail());
		    
		    if(aux != null) {
		        out = "ja existe professor com esse cpf!";
		    }
		    if(aux2 != null) {
		        out = "ja existe professor com esse email!";
		    }
		    if(out.equals("")) {
		        Professor last = professorRepository.findLastInsertedProfessor();
		        if(last == null) {
		            professor.setMatricula(this.gerarMatricula(0)); // SENACPROF20251
		        } else {
		            professor.setMatricula(this.gerarMatricula(Integer.parseInt(last.getId().toString())));
		        }
		        professorRepository.save(professor);
		        return null;
		        }
		    
		        else {
		        	return out;
		        }
		    }
		
		public String atualizarProfessor(Professor professor) {
		
			Professor aux = professorRepository.findByCpf(professor.getCpf());
			String out="";
			if(aux!=null && aux.getId() !=professor.getId()) {
				out="esse cpf esta sendo usado por outro professor";
			}
			Professor profEmail = professorRepository.findByEmail(professor.getEmail());
			if(profEmail !=null && profEmail.getId() != professor.getId()) {
				out+="esse email ja esta sendo usado por outro professor";
				
			}
			if(!out.equals("")) {
				return out;
				
			}
			professorRepository.save(professor);
			return null;
			
			
		
		}
		}
		    
		 


