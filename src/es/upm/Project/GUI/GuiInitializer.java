package es.upm.Project.GUI;

import es.upm.Project.Engine.AgenteUsuario;

public class GuiInitializer implements Runnable {

	@Override
	public void run() {
        try {
        	System.out.println("Estoy dentro jeje");
            AgenteUsuario agente = new AgenteUsuario();
            MainWindow mainWindow = new MainWindow(agente);
            mainWindow.setVisible(true);
            mainWindow.setLocationRelativeTo(null);
            mainWindow.setSize(500, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
