package es.upm.Project.Engine;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import java.io.*;

public class ResultadoAnalisis implements Serializable {
	Instances data;
	Evaluation eval;
	Evaluation[] evaluations = new Evaluation[11];
	Logistic clasificadorLogistic;

	//AÑADIR más tipos de clasificadores AQUI
	protected Instances getInstances() {
		return data;
	}

	protected void setInstances(Instances dat) {
		data = dat;
	}

	protected Evaluation getEvaluation() {
		return eval;
	}

	protected void setEvaluation(Evaluation ev) {
		eval = ev;
	}
	
	protected Logistic getClasificadorLogistic() {
		return clasificadorLogistic;
	}
	
	protected void setClasificadorLogistic(Logistic logistic) {
		clasificadorLogistic = logistic;
	}
}
