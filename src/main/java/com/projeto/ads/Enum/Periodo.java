package com.projeto.ads.Enum;

public enum Periodo {
	PRIMEIRO("1"),
	SEGUNDO("2"),
	TERCEIRO("3"),
	QUARTO("4");
	private String periodo;
	private Periodo(String p) {
		periodo= p;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
}
