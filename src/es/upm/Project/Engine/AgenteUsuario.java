package es.upm.Project.Engine;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import es.upm.Project.GUI.MainWindow;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteUsuario extends Agent {
	
	private static final long serialVersionUID = 1L;
	private MainWindow principal;
	private int embarazos, edad, nivel_glucosa, presion_arterial, pliegue_cutaneo, insulina;
	private double masa_muscular, pedigri;
	
	protected void setup() {
		
		// Añadimos los comportamientos
		
			// 1. ComportamientoX --> Solcitamos analisis al analizador
		addBehaviour(new CyclicBehaviourSolicitarAnalisis());
		
			// 2. ComportamientoY --> Mostramos resultados post-analisis
		addBehaviour(new CyclicBehaviourMostrarResultados());
		
			// 3. ComportamientoZ --> Lanzamos la interfaz en otro hilo
		principal = new MainWindow(this);
	}
	
	public void setAtributes(int argEmbarazos, int argGlucosa, int argPresion, int argGrosor, int argInsulina, double argIndice, double argFuncion, int argEdad) {
		this.edad = argEdad;
		this.embarazos = argEmbarazos;
		this.insulina = argInsulina;
		this.masa_muscular = argIndice;
		this.nivel_glucosa = argGlucosa;
		this.pedigri = argFuncion;
		this.pliegue_cutaneo = argGrosor;
		this.presion_arterial = argPresion;
		
	}
	
	public class CyclicBehaviourSolicitarAnalisis extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;

		@Override
		//Comportamiento de usuario
		public void action() {
			try {
				System.out.println("ENTRO AQUIIII DENTROOO");
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
				
				// Enviamos el mensaje de solicitud de Utils de clasificacion al AgenteAnalizador
				
				// Enviamos al agente que tenga registrado el servicio "analisis"
				// Enviamos el contenido el objeto a analizar
				// Interactua con el directory facilitator de Jave para buscar un servicio "analisi" a consumir
				
				Utils.enviarMensaje(this.myAgent, "analisis", datosAnalizar);
			
			} catch (Exception e) {
				System.err.println("Error en ... * FALTA ESPECIFICAR EL TIPO DE ERROR *");
				e.printStackTrace();
			}
		}
		
	}
	
	 public class CyclicBehaviourMostrarResultados extends CyclicBehaviour {

	        private static final long serialVersionUID = 1L;

			@Override
	        public void action() {
	            // Creamos un mensaje de espera bloqueante para esperar un mensaje de tipo INFORM
	            ACLMessage msg1 = this.myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

	            try {
	                // Recibimos los datos ya analizados, que vienen en el mensaje del Agente AnalizadorWeka como un objeto ResultadoAnalisis 
	                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(msg1.getByteSequenceContent()));
	                ResultadoAnalisis resultadoAnalisis = (ResultadoAnalisis) ois.readObject();
	                
	                // Convertimos los resultados a string
	                String resultados = resultadoAnalisis.resultadosToString();
	                
	                // Mostramos los resultados en la interfaz gráfica
	                AgenteUsuario agente = (AgenteUsuario) this.myAgent;
	                
	                //Esto no lo hace
	                agente.getPrincipal().mostrarResultados(resultados);
	            } catch (Exception e) {
	                System.err.println("Error en CyclicBehaviourMostrarResultados: " + e.getMessage());
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

	public void setNivel_glucosa(int nivel_glucosa) {
		this.nivel_glucosa = nivel_glucosa;
	}

	public double getPresion_arterial() {
		return presion_arterial;
	}

	public void setPresion_arterial(int presion_arterial) {
		this.presion_arterial = presion_arterial;
	}

	public double getPliegue_cutaneo() {
		return pliegue_cutaneo;
	}

	public void setPliegue_cutaneo(int pliegue_cutaneo) {
		this.pliegue_cutaneo = pliegue_cutaneo;
	}

	public double getInsulina() {
		return insulina;
	}

	public void setInsulina(int insulina) {
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
