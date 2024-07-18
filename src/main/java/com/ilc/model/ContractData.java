package com.ilc.model;

import java.time.LocalDate;

public class ContractData {
	private String arrendador;
	private String arrendatario;
	private LocalDate fecha;
	private String lugar;

	public ContractData() {

	}

	public ContractData(String arrendador, String arrendatario, LocalDate fecha, String lugar) {

		this.arrendador = arrendador;
		this.arrendatario = arrendatario;
		this.fecha = fecha;
		this.lugar = lugar;
	}

	public String getArrendador() {
		return arrendador;
	}

	public void setArrendador(String arrendador) {
		this.arrendador = arrendador;
	}

	public String getArrendatario() {
		return arrendatario;
	}

	public void setArrendatario(String arrendatario) {
		this.arrendatario = arrendatario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	@Override
	public String toString() {
		return "ContractData [arrendador=" + arrendador + ", arrendatario=" + arrendatario + ", fecha=" + fecha
				+ ", lugar=" + lugar + "]";
	}

}
