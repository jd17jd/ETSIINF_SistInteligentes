package es.upm.ejemploSwing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import es.upm.ejemploWeka.AgenteUsuario;
import es.upm.ejemploWeka.UtilsUI;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;

public class JFrameSeleccionar extends JFrame {
    private JPanel contentPane;
    private boolean check[] = new boolean [8];
                
    public static void main(String[] args) {
    	FlatLightLaf.setup();
    	Color arrowColor = new Color(58,134,254);
    	UIManager.put("Spinner.buttonPressedArrowColor", arrowColor.darker());
    	UIManager.put("Spinner.buttonHoverArrowColor", arrowColor);
    	UIManager.put("Spinner.buttonArrowColor", new Color(43,43,43));
        UIManager.put("Spinner.buttonBackground", Color.WHITE);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AgenteUsuario agente = new AgenteUsuario();
                    JFrameSeleccionar frame = new JFrameSeleccionar();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    frame.setSize(500, 600);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}