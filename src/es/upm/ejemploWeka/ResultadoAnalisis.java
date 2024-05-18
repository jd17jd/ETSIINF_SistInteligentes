package es.upm.ejemploWeka;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import java.io.*;

public class ResultadoAnalisis implements Serializable {
	Instances data;
	Evaluation eval;
	Evaluation[] evaluations = new Evaluation[11];
	J48 clasificadorJ48;
	IBk clasificadorKnn;
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

	protected J48 getClasificadorJ48() {
		return clasificadorJ48;
	}

	protected void setClasificadorJ48(J48 j48) {
		clasificadorJ48 = j48;
	}

	protected IBk getClasificadorKnn() {
		return clasificadorKnn;
	}

	protected void setClasificadorKnn(IBk knn) {
		clasificadorKnn = knn;
	}
	
	protected Logistic getClasificadorLogistic() {
		return clasificadorLogistic;
	}
	
	protected void setClasificadorLogistic(Logistic logistic) {
		clasificadorLogistic = logistic;
	}
}