package es.upm.ejemploWeka;

import java.io.File;
import java.io.*;

public class DatosAnalizar implements Serializable {
//Fichero de datos analizar que ha cargado el usuario en el interfaz
	File file;
//Utilizamos un único método de clasificación que ha seleccionado el usuario en el interfaz
	String classificationMethod;

	protected File getFile() {
		return file;
	}

	protected void setFile(File fichero) {
		file = fichero;
	}

	protected String getMethod() {
		return classificationMethod;
	}

	protected void setMethod(String metodo) {
		classificationMethod = metodo;
	}
}