package es.upm.Project.GUI;

import javax.swing.*;

import es.upm.Project.Engine.AgenteUsuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    

    public MainWindow (AgenteUsuario agente) {
    	
    	// Creación de la ventana
        setTitle("Diabetes Predictor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT); // Tamaño de la ventana
        setResizable(false); // No se puede redimensionar
        setLayout(new BorderLayout()); // Layout de la ventana

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(9, 2, 10, 10)); // 9 filas, 2 columnas, espacio de 10px entre componentes

        // Etiquetas y campos de texto
        mainPanel.add(new JLabel("Nº de veces de embarazos:"));
        JTextField pregnanciesField = new JTextField();
        mainPanel.add(pregnanciesField);

        mainPanel.add(new JLabel("Nivel de glucosa en sangre:"));
        JTextField glucoseField = new JTextField();
        mainPanel.add(glucoseField);

        mainPanel.add(new JLabel("Presión arterial diastólica:"));
        JTextField bloodPressureField = new JTextField();
        mainPanel.add(bloodPressureField);

        mainPanel.add(new JLabel("Grosor del pliegue cutáneo del tríceps:"));
        JTextField skinThicknessField = new JTextField();
        mainPanel.add(skinThicknessField);

        mainPanel.add(new JLabel("Insulina sérica de 2 horas:"));
        JTextField insulinField = new JTextField();
        mainPanel.add(insulinField);

        mainPanel.add(new JLabel("Índice de masa corporal:"));
        JTextField bmiField = new JTextField();
        mainPanel.add(bmiField);

        mainPanel.add(new JLabel("Función del pedigrí de la diabetes:"));
        JTextField diabetesPedigreeField = new JTextField();
        mainPanel.add(diabetesPedigreeField);

        mainPanel.add(new JLabel("Edad:"));
        JTextField ageField = new JTextField();
        mainPanel.add(ageField);
        
        

        // Botón de predicción
        JButton predictButton = new JButton("Predecir");
        
        predictButton.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		int embarazos, edad, nivel_glucosa, presion_arterial, pliegue_cutaneo, insulina;
        		double masa_muscular, pedigri;
        		
        		embarazos = Integer.parseInt(pregnanciesField.getText());
                nivel_glucosa = Integer.parseInt(glucoseField.getText());
                presion_arterial = Integer.parseInt(bloodPressureField.getText());
                pliegue_cutaneo = Integer.parseInt(skinThicknessField.getText());
                insulina = Integer.parseInt(insulinField.getText());
                masa_muscular = Integer.parseInt(bmiField.getText());
                pedigri = Integer.parseInt(diabetesPedigreeField.getText());
                edad = Integer.parseInt(ageField.getText());

                agente.setEmbarazos(embarazos);
                agente.setNivel_glucosa(nivel_glucosa);
                agente.setPresion_arterial(presion_arterial);
                agente.setPliegue_cutaneo(pliegue_cutaneo);
                agente.setInsulina(insulina);
                agente.setMasa_muscular(masa_muscular);
                agente.setPedigri(pedigri);
                agente.setEdad(edad);

                // Despertar el agente para que procese los datos
                agente.doWake();
        	}
        	
        });
        
        
        mainPanel.add(predictButton);

        // Espacio vacío para mantener el grid layout balanceado
        mainPanel.add(new JLabel(""));

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
    	
    	EventQueue.invokeLater(new GuiInitializer());
    }
    
}

