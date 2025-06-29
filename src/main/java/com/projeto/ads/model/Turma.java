package com.projeto.ads.model;

import java.util.List;
import java.util.Objects;

import com.projeto.ads.Enum.Curso;
import com.projeto.ads.Enum.Periodo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "turmas")
@SequenceGenerator(name = "seq_turma", sequenceName = "seq_turma", allocationSize = 1, initialValue = 1)
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_turma")
	private Long id;
	@Column(unique = true, nullable = false)
	private String codTurma;
	@Enumerated(EnumType.STRING)
	private Curso curso;
	@Enumerated(EnumType.STRING)
	private Periodo periodo;
	@Column(nullable = false)
	private String turno;

	@ManyToMany
	@JoinTable(name = "turma_aluno", joinColumns = @JoinColumn(name = "turma_id"), inverseJoinColumns = @JoinColumn(name = "aluno_id"))
	private List<Aluno> alunos;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(String codTurma) {
		this.codTurma = codTurma;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Periodo getPeriodo() {
	    return periodo;
	}

	public void setPeriodo(Periodo periodo) {
	    this.periodo = periodo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codTurma, curso, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(codTurma, other.codTurma) && curso == other.curso && Objects.equals(id, other.id);
	}

}
