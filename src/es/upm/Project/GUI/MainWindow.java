package es.upm.Project.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import es.upm.Project.Engine.AgenteUsuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private boolean check[] = new boolean [8]; //numero de atributos a checkear
    
 // Método para mostrar los resultados del análisis    
    public void mostrarResultados(String resultados) {
    	System.out.println("Mostramos resultados");
        JTextArea textArea = new JTextArea(resultados);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JFrame resultFrame = new JFrame("Resultados del Análisis");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.add(scrollPane);
        resultFrame.setSize(500, 400);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);
    }
    
    

    public MainWindow (AgenteUsuario agente) {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
            	String opciones[] = { "Sí", "No" };
            	int selec = JOptionPane.showOptionDialog(null,
                        "¿Está seguro de que quiere salir?", "Salir",
                        2, JOptionPane.QUESTION_MESSAGE, null, opciones, "No");
                if (selec == 0) {
                    dispose();
                    agente.doDelete();
                }
            }
        });

        for(int i = 0; i < check.length; i++) {
        	check[i] = true;
        }
        
        Font impact = new Font("Impact", Font.PLAIN, 40);
        Font arial1 = new Font("Arial", Font.BOLD, 15);
        Font arialError = new Font("Arial", Font.PLAIN, 10);
        Border errorBorder = new LineBorder(Color.RED, 1);
        
        setTitle("Predicción Diabetes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 698, 555);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel titulo = new JLabel("Predicción de Diabetes");
        titulo.setFont(impact);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0, 25, 480, 50);
        contentPane.add(titulo);
        
        // EMBARAZOS ---------------------------------------------------------------------------
        JLabel embarazosLabel = new JLabel("Número de embarazos:");
        embarazosLabel.setFont(arial1);
        embarazosLabel.setBounds(50, 100, 300, 25);
        contentPane.add(embarazosLabel);
        
        JSpinner nEmbarazos = new JSpinner();
        nEmbarazos.setBounds(350, 100, 80, 25);
        contentPane.add(nEmbarazos);
        
        JLabel errorEmbarazoLabel = new JLabel("Introduzca un valor entre 0 y 20");
		errorEmbarazoLabel.setBounds(50, 125, 300, 10);
        errorEmbarazoLabel.setForeground(Color.RED);
        errorEmbarazoLabel.setFont(arialError);
        
        Border embarazosBorder = nEmbarazos.getBorder();
        
        nEmbarazos.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) nEmbarazos.getValue();
                if (value < 0 || value > 20) {
                    check[0] = false;
    				contentPane.add(errorEmbarazoLabel);
    				contentPane.repaint();
    				nEmbarazos.setBackground(new Color(254,240,241));
    				nEmbarazos.setBorder(errorBorder);
                }
                else {
                    check[0] = true;
                    contentPane.remove(errorEmbarazoLabel);
    				contentPane.repaint();
    				nEmbarazos.setBackground(Color.WHITE);
    				nEmbarazos.setBorder(embarazosBorder);
                }
            }
        });
        
        // GLUCOSA ---------------------------------------------------------------------------
        
        JLabel glucosaLabel = new JLabel("Concentración de glucosa en plasma:");
        glucosaLabel.setFont(arial1);
        glucosaLabel.setBounds(50, 150, 300, 25);
        contentPane.add(glucosaLabel);
        
        JSpinner glucosa = new JSpinner();
        glucosa.setBounds(350, 150, 80, 25);
        contentPane.add(glucosa);
        
        JLabel errorGlucosaLabel = new JLabel("Introduzca un valor entre 0 y 199");
		errorGlucosaLabel.setBounds(50, 175, 300, 10);
        errorGlucosaLabel.setForeground(Color.RED);
        errorGlucosaLabel.setFont(arialError);
        
        Border glucosaBorder = glucosa.getBorder();
        
        glucosa.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) glucosa.getValue(); 
                if (value < 0 || value > 199) {
                    check[1] = false;
    				contentPane.add(errorGlucosaLabel);
    				contentPane.repaint();
    				glucosa.setBackground(new Color(254,240,241));
    				glucosa.setBorder(errorBorder);
                }
                else {
                    check[1] = true;
                    contentPane.remove(errorGlucosaLabel);
    				contentPane.repaint();
    				glucosa.setBackground(Color.WHITE);
    				glucosa.setBorder(glucosaBorder);
                }
            }
        });
        
        // PRESION ---------------------------------------------------------------------------
        
        JLabel presionLabel = new JLabel("Presión arterial (mm Hg):");
        presionLabel.setFont(arial1);
        presionLabel.setBounds(50, 200, 300, 25);
        contentPane.add(presionLabel);
        
        JSpinner presion = new JSpinner();
        presion.setBounds(350, 200, 80, 25);
        contentPane.add(presion);
        
        JLabel errorPresionLabel = new JLabel("Introduzca un valor entre 0 y 125");
		errorPresionLabel.setBounds(50, 225, 300, 10);
        errorPresionLabel.setForeground(Color.RED);
        errorPresionLabel.setFont(arialError);
        
        Border presionBorder = nEmbarazos.getBorder();
        
        presion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) presion.getValue();
                if (value < 0 || value > 125) {
                    check[2] = false;
    				contentPane.add(errorPresionLabel);
    				contentPane.repaint();
    				presion.setBackground(new Color(254,240,241));
    				presion.setBorder(errorBorder);
                }
                else {
                    check[2] = true;
                    contentPane.remove(errorPresionLabel);
    				contentPane.repaint();
    				presion.setBackground(Color.WHITE);
    				presion.setBorder(presionBorder);
                }
            }
        });
        
        // GROSOR CUTANEO ---------------------------------------------------------------------------
        
        JLabel grosorLabel = new JLabel("Grosor pliegue cutáneo tríceps (mm):");
        grosorLabel.setFont(arial1);
        grosorLabel.setBounds(50, 250, 300, 25);
        contentPane.add(grosorLabel);
        
        JSpinner grosor = new JSpinner();
        grosor.setBounds(350, 250, 80, 25);
        contentPane.add(grosor);
        

        JLabel errorGrosorLabel = new JLabel("Introduzca un valor entre 0 y 99");
		errorGrosorLabel.setBounds(50, 275, 300, 10);
        errorGrosorLabel.setForeground(Color.RED);
        errorGrosorLabel.setFont(arialError);
        
        Border grosorBorder = grosor.getBorder();
        
        grosor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) grosor.getValue();
                if (value < 0 || value > 99) {
                    check[3] = false;
    				contentPane.add(errorGrosorLabel);
    				contentPane.repaint();
    				grosor.setBackground(new Color(254,240,241));
    				grosor.setBorder(errorBorder);
                }
                else {
                    check[3] = true;
                    contentPane.remove(errorGrosorLabel);
    				contentPane.repaint();
    				grosor.setBackground(Color.WHITE);
    				grosor.setBorder(grosorBorder);
                }
            }
        });
        
        // INSULINA ---------------------------------------------------------------------------
        
        JLabel insulinaLabel = new JLabel("Insulina sérica a las 2 horas (mu U/ml):");
        insulinaLabel.setFont(arial1);
        insulinaLabel.setBounds(50, 300, 300, 25);
        contentPane.add(insulinaLabel);
        
        JSpinner insulina = new JSpinner();
        insulina.setBounds(350, 300, 80, 25);
        contentPane.add(insulina);
        
        JLabel errorInsulinaLabel = new JLabel("Introduzca un valor entre 0 y 846");
		errorInsulinaLabel.setBounds(50, 325, 300, 10);
        errorInsulinaLabel.setForeground(Color.RED);
        errorInsulinaLabel.setFont(arialError);
        
        Border insulinaBorder = insulina.getBorder();
        
        insulina.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) insulina.getValue();
                if (value < 0 || value > 846) {
                    check[4] = false;
    				contentPane.add(errorInsulinaLabel);
    				contentPane.repaint();
    				insulina.setBackground(new Color(254,240,241));
    				insulina.setBorder(errorBorder);
                }
                else {
                    check[4] = true;
                    contentPane.remove(errorInsulinaLabel);
    				contentPane.repaint();
    				insulina.setBackground(Color.WHITE);
    				insulina.setBorder(insulinaBorder);
                }
            }
        });
        
        // MASA CORPORAL ---------------------------------------------------------------------------
        
        JLabel indiceLabel = new JLabel("Índice de masa corporal (kg/m^2):");
        indiceLabel.setFont(arial1);
        indiceLabel.setBounds(50, 350, 300, 25);
        contentPane.add(indiceLabel);
        
        SpinnerNumberModel indiceModel = new SpinnerNumberModel(0, -1, 999, 0.1);
        JSpinner indice = new JSpinner(indiceModel);
        indice.setBounds(350, 350, 80, 25);
        contentPane.add(indice);

        JLabel errorIndiceLabel = new JLabel("Introduzca un valor entre 0 y 100");
		errorIndiceLabel.setBounds(50, 375, 300, 10);
        errorIndiceLabel.setForeground(Color.RED);
        errorIndiceLabel.setFont(arialError);
        
        Border indiceBorder = indice.getBorder();
        
        indice.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	double value = (double) indice.getValue();
                if (value < 0 || value > 100) {
                    check[5] = false;
    				contentPane.add(errorIndiceLabel);
    				contentPane.repaint();
    				indice.setBackground(new Color(254,240,241));
    				indice.setBorder(errorBorder);
                }
                else {
                    check[5] = true;
                    contentPane.remove(errorIndiceLabel);
    				contentPane.repaint();
    				indice.setBackground(Color.WHITE);
    				indice.setBorder(indiceBorder);
                }
            }
        });
        
        // ANTECEDENTES ---------------------------------------------------------------------------
        
        JLabel antecedentesLabel = new JLabel("Función de antecedente de diabetes:");
        antecedentesLabel.setFont(arial1);
        antecedentesLabel.setBounds(50, 400, 300, 25);
        contentPane.add(antecedentesLabel);
        
        SpinnerNumberModel antecedentesModel = new SpinnerNumberModel(0, -1, 10, 0.1);
        JSpinner antecedentes = new JSpinner(antecedentesModel);
        antecedentes.setBounds(350, 400, 80, 25);
        contentPane.add(antecedentes);
        
        JLabel errorAntecedentesLabel = new JLabel("Introduzca un valor entre 0 y 1");
		errorAntecedentesLabel.setBounds(50, 425, 300, 10);
        errorAntecedentesLabel.setForeground(Color.RED);
        errorAntecedentesLabel.setFont(arialError);
        
        Border antecedentesBorder = antecedentes.getBorder();
        
        antecedentes.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	double value = (double) antecedentes.getValue();
                if (value < 0 || value > 20) {
                    check[6] = false;
    				contentPane.add(errorAntecedentesLabel);
    				contentPane.repaint();
    				antecedentes.setBackground(new Color(254,240,241));
    				antecedentes.setBorder(errorBorder);
                }
                else {
                    check[6] = true;
                    contentPane.remove(errorAntecedentesLabel);
    				contentPane.repaint();
    				antecedentes.setBackground(Color.WHITE);
    				antecedentes.setBorder(antecedentesBorder);
                }
            }
        });
        
        // EDAD ---------------------------------------------------------------------------
        
        JLabel edadLabel = new JLabel("Edad (años):");
        edadLabel.setFont(arial1);
        edadLabel.setBounds(50, 450, 300, 25);
        contentPane.add(edadLabel);
        
        JSpinner edad = new JSpinner();
        edad.setBounds(350, 450, 80, 25);
        contentPane.add(edad);
        

        JLabel errorEdadLabel = new JLabel("Introduzca un valor entre 0 y 125");
		errorEdadLabel.setBounds(50, 475, 300, 10);
        errorEdadLabel.setForeground(Color.RED);
        errorEdadLabel.setFont(arialError);
        
        Border edadBorder = edad.getBorder();
        
        edad.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
            	int value = (int) edad.getValue();
                if (value < 0 || value > 125) {
                    check[7] = false;
    				contentPane.add(errorEdadLabel);
    				contentPane.repaint();
    				edad.setBackground(new Color(254,240,241));
    				edad.setBorder(errorBorder);
                }
                else {
                    check[7] = true;
                    contentPane.remove(errorEdadLabel);
    				contentPane.repaint();
    				edad.setBackground(Color.WHITE);
    				edad.setBorder(edadBorder);
                }
            }
        });
        
        // ---------------------------------------------------------------------------
        
        JFrameBoton btnEnviar = new JFrameBoton("Enviar", new Color(58,134,254), arial1, Color.WHITE);
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 8; i++) {
                    if (!check[i]) {
                        JOptionPane.showMessageDialog(contentPane, "Por favor, introduzca los valores correctamente", "Error", JOptionPane.WARNING_MESSAGE);
            			return;
                    }
                }
            	
            	int argEmbarazos = (int) nEmbarazos.getValue();
        		int argGlucosa = (int) glucosa.getValue();
        		int argPresion = (int) presion.getValue();
        		int argGrosor = (int) grosor.getValue();
                int argInsulina = (int) insulina.getValue();
        		double argIndice = (double) indice.getValue();
                double argFuncion = (double) antecedentes.getValue();
                int argEdad = (int) edad.getValue();

                System.out.println("Se ha pulsado el botón enviar. Datos enviados:");
                System.out.println("Número de embarazos: " + argEmbarazos);
                System.out.println("Concentración de glucosa en plasma: " + argGlucosa);
                System.out.println("Presión arterial (mm Hg): " + argPresion);
                System.out.println("Grosor pliegue cutáneo tríceps (mm): " + argGrosor);
                System.out.println("Insulina sérica a las 2 horas (mu U/ml): " + argInsulina);
                System.out.println("Índice de masa corporal (kg/m^2): " + argIndice);
                System.out.println("Función de antecedente de diabetes: " + argFuncion);
                System.out.println("Edad (años): " + argEdad);
                
                //Llama al método del agente con los datos
                agente.setAtributes(argEmbarazos, argGlucosa, argPresion, argGrosor, argInsulina, argIndice, argFuncion, argEdad);
                
                agente.doWake();
                //dispose();
            }
        });
        btnEnviar.setBounds(192, 500, 115, 40);
        contentPane.add(btnEnviar);
    }

    public static void main(String[] args) {
    	
    	//FlatLightLaf.setup();
    	Color arrowColor = new Color(58,134,254);
    	UIManager.put("Spinner.buttonPressedArrowColor", arrowColor.darker());
    	UIManager.put("Spinner.buttonHoverArrowColor", arrowColor);
    	UIManager.put("Spinner.buttonArrowColor", new Color(43,43,43));
        UIManager.put("Spinner.buttonBackground", Color.WHITE);
        
    }
    
}