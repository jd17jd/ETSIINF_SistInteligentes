package es.upm.Project.Engine;

import es.upm.Project.GUI.MainWindow;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteUsuario extends Agent {
	
	private MainWindow principal;
	private int embarazos, edad;
	private double nivel_glucosa, presion_arterial, 
	pliegue_cutaneo, insulina, masa_muscular, pedigri;
	
	protected void setup() {
		
		// Añadimos los comportamientos
		
			// 1. ComportamientoX --> Solcitamos analisis al analizador
		addBehaviour(new CyclicBehaviourSolicitarAnalisis());
		
			// 2. ComportamientoY --> Mostramos resultados post-analisis
		addBehaviour(new CyclicBehaviourMostrarResultados());
		
			// 3. ComportamientoZ --> Lanzamos la interfaz en otro hilo
		principal = new MainWindow(this);
		
	}
	
	public class CyclicBehaviourSolicitarAnalisis extends CyclicBehaviour {

		@Override
		//Comportamiento de usuario
		public void action() {
			try {
				//Agente espera a que el usuario introduzca los parámetros y seleccione predecir
				myAgent.doWait();
				
				DatosAnalizar datosAnalizar = new DatosAnalizar();
								
				datosAnalizar.setEdad(edad);
				datosAnalizar.setEmbarazos(embarazos);
				datosAnalizar.setInsulina(insulina);
				datosAnalizar.setMasa_muscular(masa_muscular);
				datosAnalizar.setNivel_glucosa(nivel_glucosa);
				datosAnalizar.setPedigri(pedigri);
				datosAnalizar.setPliegue_cutaneo(pliegue_cutaneo);
				datosAnalizar.setPresion_arterial(presion_arterial);
				
				
				Utils.enviarMensaje(this.myAgent, "analisis", null); //Null: En el ejemplo le pasa datosAnalizar
			}
			catch (Exception e) {
				System.err.println("Error en ...(?)");
				e.printStackTrace();
			}
		}
		
	}
	
	public class CyclicBehaviourMostrarResultados extends CyclicBehaviour {

		@Override
		//Comportamiento de usuario
		public void action() {
			//Creamos un mensaje de espera bloqueante para esperar un mensaje de tipo INFORM
			ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			try {
				//Recibimos los datos ya analizados,que vienen en el mensaje del Agente AnalizadorWeka como un objeto ResultadoAnalisis 
			}
			catch (Exception e) {
				System.err.println("Error en ...(?)");
				e.printStackTrace();
			}
			
		}
		
	}
	
	//Getters y setters
	public MainWindow getPrincipal() {
		return principal;
	}

	public void setPrincipal(MainWindow principal) {
		this.principal = principal;
	}

	public int getEmbarazos() {
		return embarazos;
	}

	public void setEmbarazos(int embarazos) {
		this.embarazos = embarazos;
	}

	public double getNivel_glucosa() {
		return nivel_glucosa;
	}

	public void setNivel_glucosa(double nivel_glucosa) {
		this.nivel_glucosa = nivel_glucosa;
	}

	public double getPresion_arterial() {
		return presion_arterial;
	}

	public void setPresion_arterial(double presion_arterial) {
		this.presion_arterial = presion_arterial;
	}

	public double getPliegue_cutaneo() {
		return pliegue_cutaneo;
	}

	public void setPliegue_cutaneo(double pliegue_cutaneo) {
		this.pliegue_cutaneo = pliegue_cutaneo;
	}

	public double getInsulina() {
		return insulina;
	}

	public void setInsulina(double insulina) {
		this.insulina = insulina;
	}

	public double getMasa_muscular() {
		return masa_muscular;
	}

	public void setMasa_muscular(double masa_muscular) {
		this.masa_muscular = masa_muscular;
	}

	public double getPedigri() {
		return pedigri;
	}

	public void setPedigri(double pedigri) {
		this.pedigri = pedigri;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	
	
}
