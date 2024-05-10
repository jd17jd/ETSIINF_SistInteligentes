package es.upm.ejemploSwing;

import javax.swing.JFrame;
import es.upm.ejemploSwing.JFramePrincipal;
import es.upm.ejemploWeka.AgenteUsuario;

public class MainGUI extends Thread {
	String titulo;
	AgenteUsuario agenteUsuario;

	public MainGUI(String tit, AgenteUsuario a) {
//Se reciben dos parámetros
//el título a poner en la ventana
//la referencia al AgenteUsuario
		this.titulo = "Ejemplo Conexión WEKA. Agente " + tit;
		this.agenteUsuario = a;
	}

	public void run() {
//El interfaz se basará en una ventana de tipo JFrame
//Lo personalizamos creando un constructor personalizado para que pueda acceder al agente desde el hilo del interfaz
		JFrame jFrame;
		jFrame = new JFramePrincipal(agenteUsuario);
		jFrame.setTitle(titulo);
		jFrame.setVisible(true);
		jFrame.setResizable(true);
	}
}