package es.upm.Project.GUI;

import es.upm.Project.Engine.AgenteUsuario;

public class GuiInitializer implements Runnable {

	@Override
	public void run() {
        try {
            AgenteUsuario agente = new AgenteUsuario();
            MainWindow frame = new MainWindow(agente);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
