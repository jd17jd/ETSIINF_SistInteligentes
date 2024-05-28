package es.upm.Project.Engine;

import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;

public class Prueba {
    public static void main(String[] args) {
        try {
            // Lee el archivo ARFF
            BufferedReader reader = new BufferedReader(new FileReader("resources/diabetes.arff"));
            Instances data = new Instances(reader);
            reader.close();

            // Establece la clase de atributo (si es necesario)
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }

            // Muestra información básica
            System.out.println("Número de instancias: " + data.numInstances());
            System.out.println("Número de atributos: " + data.numAttributes());
            System.out.println("Primeras 5 instancias: ");
            for (int i = 0; i < 5; i++) {
                System.out.println(data.instance(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
