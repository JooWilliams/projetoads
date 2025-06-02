package com.projeto.ads.Enum;

public enum Status {
	ATIVO("Ativo"),
	CANCELADO("Cancelado"),
	INATIVO("Inativo"),
	TRANCADO("Trancado");
	private String status;
	private Status(String status) {
		this.status= status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status= status;
	}
}
