package es.upm.Project.GUI;
import java.awt.Color;
import javax.swing.UIManager;
import es.upm.Project.Engine.AgenteUsuario;

public class GuiInitializer implements Runnable {
    String titulo;
    AgenteUsuario agenteUsuario;
    MainWindow window;

    public GuiInitializer(String tit, AgenteUsuario a){
        this.titulo = tit;
        this.agenteUsuario = a;
    }

    public MainWindow getMainWindow() {
        return window;
    }

    @Override
    public void run() {
        try {
            Color arrowColor = new Color(58,134,254);
            UIManager.put("Spinner.buttonPressedArrowColor", arrowColor.darker());
            UIManager.put("Spinner.buttonHoverArrowColor", arrowColor);
            UIManager.put("Spinner.buttonArrowColor", new Color(43,43,43));
            UIManager.put("Spinner.buttonBackground", Color.WHITE);

            MainWindow mainWindow = new MainWindow(agenteUsuario);
            this.window = mainWindow;
            mainWindow.setVisible(true);
            mainWindow.setLocationRelativeTo(null);
            mainWindow.setSize(500, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}