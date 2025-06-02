package com.projeto.ads.Enum;

public enum Curso {
	ADMINISTRACAO("Administração"),
	ADS("Análise e Desenvolvimento de Sistemas"),
	CONTABILIDADE("Contabilidade"),
	ENFERMAGEM("Enfermagem"),
	MARKETING("Marketing");
	private String descricao;
	private Curso(String desc) {
		this.descricao=desc;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
