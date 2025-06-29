package com.projeto.ads.model;

import java.util.Objects;

import com.projeto.ads.Enum.Curso;
import com.projeto.ads.Enum.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="seq_aluno", sequenceName = "seq_aluno", allocationSize = 1, initialValue = 1)
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_aluno")
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private Curso curso;
	private String matricula;
	
	@Column(unique = true)
    private String email;
    
    @Column(unique = true)
	private String cpf;
    
	@Enumerated(EnumType.STRING)
	private Status status;
	private String turno;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cpf, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id);
	}
	
}//fim class
