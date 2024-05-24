package es.upm.Project.Engine;

import java.io.Serializable;

public class DatosAnalizar implements Serializable {
	
	int embarazos, edad, nivel_glucosa, presion_arterial, pliegue_cutaneo, insulina;
	double masa_muscular, pedigri;
	
	
	protected  int getEmbarazos() {
		return embarazos;
	}
	protected void setEmbarazos(int embarazos) {
		this.embarazos = embarazos;
	}
	protected int getEdad() {
		return edad;
	}
	protected void setEdad(int edad) {
		this.edad = edad;
	}
	protected double getNivel_glucosa() {
		return nivel_glucosa;
	}
	protected void setNivel_glucosa(int nivel_glucosa) {
		this.nivel_glucosa = nivel_glucosa;
	}
	protected double getPresion_arterial() {
		return presion_arterial;
	}
	protected void setPresion_arterial(int presion_arterial) {
		this.presion_arterial = presion_arterial;
	}
	protected double getPliegue_cutaneo() {
		return pliegue_cutaneo;
	}
	protected void setPliegue_cutaneo(int pliegue_cutaneo) {
		this.pliegue_cutaneo = pliegue_cutaneo;
	}
	protected double getInsulina() {
		return insulina;
	}
	protected void setInsulina(int insulina) {
		this.insulina = insulina;
	}
	protected double getMasa_muscular() {
		return masa_muscular;
	}
	protected void setMasa_muscular(double masa_muscular) {
		this.masa_muscular = masa_muscular;
	}
	protected double getPedigri() {
		return pedigri;
	}
	protected void setPedigri(double pedigri) {
		this.pedigri = pedigri;
	}
	
	
}
