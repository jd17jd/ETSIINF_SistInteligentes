package es.upm.Project.Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Random;

import es.upm.ejemploWeka.DatosAnalizar;
import es.upm.ejemploWeka.ResultadoAnalisis;
import es.upm.ejemploWeka.AnalizadorWeka.CyclicBehaviourAnalisisWEKA;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;


public class AgenteAnalizadorWeka extends Agent {
	
	protected ResultadoAnalisis resultado;
	
	protected CyclicBehaviourAnalisisWEKA comportamientoWEKA = new CyclicBehaviourAnalisisWEKA();
	
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	
	private static final MessageTemplate mt1 = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));


	
	protected void setup() {
		
		// Crear servicios proporcionados por el agente y registrarlos en la plataforma
		// En este caso creamos un servicio de tipo analisis y lo registramos en el
		// directorio de servicios
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("WEKA_Analisis");
		// Establecemos el tipo del servicio para poder localizarlo cuando haga una
		// búsqueda
		sd.setType("analisis");
		// Los agentes que quieran utilizar el servicio deberán usar el lenguaje de
		// contenido FIPA-SL
		sd.addLanguages(new SLCodec().getName());
		// Se añade el servicio al dfd
		dfd.addServices(sd);
		// Enviamos el nuevo servicio al agente DF para que se realice el registro
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException e) {
			System.err.println("Agente " + getLocalName() + ": " + e.getMessage());
		}
		// Creamos el objeto de resultados que se enviará al AgenteUsuario para que se
		// muestre al usuario
		resultado = new ResultadoAnalisis();
		addBehaviour(comportamientoWEKA);
	}

	public class CyclicBehaviourAnalisisWEKA extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				if (msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println(myAgent.getLocalName() + ": Recibo el mensaje: \n" + msg);
					try {
						
						DatosAnalizar analizar = new DatosAnalizar();
						analizar = (DatosAnalizar) msg.getContentObject();
						
						
						int embarazos, edad;
						double nivel_glucosa, presion_arterial, 
						pliegue_cutaneo, insulina, masa_muscular, pedigri;
						
						// Los malditos getters no funcionan
						// embarazos = analizar.get
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			else {
				this.block();
			}
			
			
		}
	}
	
	public class ComportamientoLogistic extends OneShotBehaviour {
	    public void action() {
//	        try {
//	            Logistic clasificadorLogistico = new Logistic();
//	            Logistic clasificadorLogistico_2 = new Logistic();
//	            Instances data = new Instances(new BufferedReader(new FileReader("/PROJECT_SISTEMAS_INTELIGENTES/resources/diabetes.arff")));
//	            data.setClassIndex(data.numAttributes() - 1);
//	            clasificadorLogistico.buildClassifier(data);
//	            // Si solo tenemos el conjunto de entrenamiento y no de test, hacemos validación cruzada
//	            // No debemos crear el clasificador previamente para evitar sesgos. Lo hemos creado para enviarlo en el resultado
//	            Evaluation evalLogistica = new Evaluation(data);
//	            evalLogistica.crossValidateModel(clasificadorLogistico_2, data, 10, new Random(1));
//	            resultado.setClasificadorLogistic(clasificadorLogistico);
//	            resultado.setEvaluation(evalLogistica);
//	            resultado.setInstances(data);
//	            System.out.println("Evaluación: " + resultado.getEvaluation().toSummaryString() + "\n\n"
//	                    + resultado.getClasificadorLogistic().toString());
//	            msg1.setContentObject((Serializable) resultado);
//	            // Envío del mensaje al agente con el resultado obtenido
//	            this.myAgent.send(msg1);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
	    }
	}

}
