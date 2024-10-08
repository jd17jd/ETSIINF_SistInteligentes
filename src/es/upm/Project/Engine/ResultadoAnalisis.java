package es.upm.Project.Engine;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import java.io.Serializable;

public class ResultadoAnalisis implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Instances data;
    private Evaluation eval;
    private Evaluation[] evaluations;
    //private Logistic clasificadorLogistic;
    //private J48 clasificadorLogistic;
    private NaiveBayes clasificadorLogistic;
    private String resultadoClasificacion; // Nuevo atributo para almacenar el resultado de la clasificación

    // Constructor
    public ResultadoAnalisis() {
        this.evaluations = new Evaluation[11]; // Inicializa el array de evaluaciones
    }

    // Getters and Setters
    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }

    public Evaluation getEval() {
        return eval;
    }

    public void setEval(Evaluation eval) {
        this.eval = eval;
    }

    public Evaluation[] getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Evaluation[] evaluations) {
        if (evaluations.length == 11) {
            this.evaluations = evaluations;
        } else {
            throw new IllegalArgumentException("Evaluations array must have 11 elements.");
        }
    }

//    public Logistic getClasificadorLogistic() {
//        return clasificadorLogistic;
//    }
//    public J48 getClasificadorLogistic() {
//        return clasificadorLogistic;
//    }
    
    public NaiveBayes getClasificadorLogistic() {
        return clasificadorLogistic;
    }

//    public void setClasificadorLogistic(Logistic clasificadorLogistic) {
//        this.clasificadorLogistic = clasificadorLogistic;
//    }
//    public void setClasificadorLogistic(J48 clasificadorLogistic) {
//        this.clasificadorLogistic = clasificadorLogistic;
//    }
    public void setClasificadorLogistic(NaiveBayes clasificadorLogistic) {
        this.clasificadorLogistic = clasificadorLogistic;
    }

    public String getResultadoClasificacion() {
        return resultadoClasificacion;
    }

    public void setResultadoClasificacion(String resultadoClasificacion) {
        this.resultadoClasificacion = resultadoClasificacion;
    }

    // Method to convert analysis result to string
    public String resultadosToString() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados del Análisis:\n");
        
        if (eval != null) {
            sb.append("Evaluación general:\n");
            sb.append(eval.toSummaryString()).append("\n");
            sb.append("Matriz de confusión:\n");
            sb.append(eval.toMatrixString()).append("\n");
            sb.append("Detalles de clasificación:\n");
            sb.append(eval.toClassDetailsString()).append("\n");
        } else {
            sb.append("No hay evaluación disponible.\n");
        }

        sb.append("Evaluaciones adicionales:\n");
        for (int i = 0; i < evaluations.length; i++) {
            if (evaluations[i] != null) {
                sb.append("Evaluación ").append(i + 1).append(":\n");
                sb.append(evaluations[i].toSummaryString()).append("\n");
            } else {
                sb.append("Evaluación ").append(i + 1).append(": No disponible\n");
            }
        }

        if (clasificadorLogistic != null) {
            sb.append("Clasificador Logístico:\n");
            sb.append(clasificadorLogistic.toString()).append("\n");
        } else {
            sb.append("No hay clasificador logístico disponible.\n");
        }

        if (resultadoClasificacion != null) {
            sb.append("Resultado de la Clasificación:\n");
            sb.append(resultadoClasificacion).append("\n");
        } else {
            sb.append("No hay resultado de clasificación disponible.\n");
        }

        return sb.toString();
    }

    // Método para establecer el resultado de la clasificación
    public void setResultado(String resultadoClasificacion) {
        this.resultadoClasificacion = resultadoClasificacion;
    }
}