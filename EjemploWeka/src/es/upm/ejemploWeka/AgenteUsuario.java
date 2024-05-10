package es.upm.ejemploWeka;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.upm.ejemploSwing.JFrameSeleccionar;
import es.upm.ejemploSwing.MainGUI;
import es.upm.ejemploSwing.JFramePrincipal;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteUsuario extends Agent {
	//Métodos de clasificación que vamos a utilizar en este ejemplo: árbol de decisión J48 y clustering KNN
	//arbol decision + k-nearest neighbour (vecino mas cercano) para clustering
	static final int _J48 = 0;
	static final int _KNN = 6;
	private File fichero;
	private String metodo_clasificacion;
	JFramePrincipal principal;
	JFrameSeleccionar ventana;

	protected void setup() {
		//Añadimos los comportamientos cíclicos para Solicitar análisis al analizador y para Mostrar resultados al usuario
		addBehaviour(new CyclicBehaviourSolicitarAnalisis());
		addBehaviour(new CyclicBehaviourMostrarResultados());
		//Lanzamos el interfaz en un hilo de ejecución independiente --> Creamos un MainGUI, que a su vez crea un JFrame principal para el agente
		MainGUI gui = new MainGUI(this.getLocalName(), this);
		gui.run();
	}

	//Comportamiento cíclico para solicitor la realización de análisis de los datos
	public class CyclicBehaviourSolicitarAnalisis extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		public void action() {
			try {
				//El agente de usuario espera a que el usuario haya seleccionado método de clasificación y un fichero
				myAgent.doWait();
				//Desde el interfaz de usuario el usuario ha pulsado el botón Enviar y podemos continuar
				System.out.println("Continuando ...");
				//Definimos un objeto DatosAnalizar que sea serializable y que contenga
				//método de clasificación a aplicar que ha seleccionado el usuario en el interfaz
				//fichero a analizar que ha cargado el usuario en el interfaz
				DatosAnalizar datosAnalizar = new DatosAnalizar();
				datosAnalizar.setFile(fichero);
				datosAnalizar.setMethod(metodo_clasificacion);
				//Enviamos el mensaje de solicitud de clasificación al agente Análisis
				//Buscar el agente en el directorio de Servicios
				//Utilizamos la función de envío definida en Utils
				//Crea un mensaje de tipo REQUEST
				//Lo envía a los agentes que tienen registrado el servicio "analisis"
				//Como contenido se pasa el objeto analizar 
				Utils.enviarMensaje(this.myAgent, "analisis", datosAnalizar);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//Comportamiento cíclico para mostrar los resultados al usuario
	public class CyclicBehaviourMostrarResultados extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		public void action() {
			//Creamos un mensaje de espera bloqueante para esperar un mensaje de tipo INFORM
			ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			try {
				//Recibimos los datos a analizar, que vienen en el mensaje del agente AnalizadorWeka como un objeto ResultadoAnalisis
				ResultadoAnalisis resultadoAnalisis = new ResultadoAnalisis();
				resultadoAnalisis = (ResultadoAnalisis) msg1.getContentObject();
				//Mostramos al usuario el resultado obtenido en la pantalla en la JTextArea
				JTextArea textArea = new JTextArea();
				if (metodo_clasificacion.equals("J48")) {
					String j48 = textArea.getText()
							+ " Resultado del análisis con WEKA utilizando el algoritmo de clasificación" + "J48: \n"
							+ resultadoAnalisis.getEvaluation().toSummaryString() + "\n\n"
							+ resultadoAnalisis.getClasificadorJ48().toString();
					textArea.setText(j48);
				} else if (metodo_clasificacion.equals("KNN")) {
					String knn = textArea.getText()
							+ "\n\n Resultado del análisis con WEKA utilizando el algoritmo de clasificación"
							+ "KNN: \n" + resultadoAnalisis.getEvaluation().toSummaryString() + "\n\n"
							+ resultadoAnalisis.getClasificadorKnn().toString();
					textArea.setText(knn);
				}
				//Creamos una ventana para mostrar los resultados obtenidos. Lo podemos hacer por ejemplo con un JOptionPane
				//Y le ponemos un scroll para desplazarnos porque puede haber muchos resultados
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(800, 800));
				JOptionPane.showMessageDialog(null, scrollPane, "Resultados Analisis WEKA",
						JOptionPane.INFORMATION_MESSAGE);
				//Limpiamos metodos_clasificación para que pase a estar vacío cuando el usuario haga una nueva solicitud
				metodo_clasificacion = "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//Establecemos métodos para almacenar y recuperar el fichero y el método de clasificación
	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero_nuevo) {
		this.fichero = fichero_nuevo;
	}

	public String getMetodo() {
		return metodo_clasificacion;
	}

	public void setMetodo(String nuevo_metodo) {
		this.metodo_clasificacion = nuevo_metodo;
	}
}