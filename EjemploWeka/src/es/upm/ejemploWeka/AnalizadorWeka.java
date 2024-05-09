package es.upm.ejemploWeka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Random;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.OneShotBehaviour;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import java.io.File;

public class AnalizadorWeka extends Agent {
	private static final long serialVersionUID = 1L;
	static final int _J48 = 0;
	static final int _KNN = 6;
	protected ResultadoAnalisis resultado;
	File fichero;
	protected CyclicBehaviourAnalisisWEKA comportamientoWEKA = new CyclicBehaviourAnalisisWEKA();
	ACLMessage msg1 = new ACLMessage();
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private static final MessageTemplate mt1 = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));

	protected void setup() {
		// Crear servicios proporcionados por el agentes y registrarlos en la plataforma
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
			// registramos los servicios
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println("Agente " + getLocalName() + ": " + e.getMessage());
		}
		// Creamos el objeto de resultados que se enviará al AgenteUsuario para que se
		// muestre al usuario
		resultado = new ResultadoAnalisis();
		// Añadimos el comportamiento cíclico de análisis que se está ejecutando
		// continuamente esperando peticiones de análisis de datos
		addBehaviour(comportamientoWEKA);
	}

	public class CyclicBehaviourAnalisisWEKA extends CyclicBehaviour {
		public void action() {
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				if (msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println();
					System.out.println(myAgent.getLocalName() + ": Recibo el mensaje: \n" + msg);
					try {
//Recibimos los datos a analizar, que vienen en el mensaje
						DatosAnalizar analizar = new DatosAnalizar();
						analizar = (DatosAnalizar) msg.getContentObject();
//Extraemos el fichero y el método de clasificación de los datos a analizar
						fichero = analizar.getFile();
						String metodo = analizar.getMethod();
//Lanzamos el comportamiento correspondiente al método de clasificación que haya seleccionado el usuario
						if (metodo.equals("J48")) {
							OneShotBehaviour b1 = new ComportamientoJ48();
							myAgent.addBehaviour(b1);
						} else if (metodo.equals("KNN")) {
							OneShotBehaviour b2 = new ComportamientoKnn();
							myAgent.addBehaviour(b2);
						}
						msg1 = msg.createReply();
						msg1.addReceiver(msg.getSender());
						msg1.setPerformative(ACLMessage.INFORM);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				this.block();
			}
		}
	}

	public class ComportamientoJ48 extends OneShotBehaviour {
		public void action() {
			try {
				System.out.println("Entro en comportamiento J48");
				J48 clasificadorJ48 = new J48();
				J48 clasificadorJ48_2 = new J48();
				clasificadorJ48.setUnpruned(true);
				Instances data = new Instances(new BufferedReader(new FileReader(fichero)));
				data.setClassIndex(data.numAttributes() - 1);
//Construimos el clasificador
				clasificadorJ48.buildClassifier(data);
//Si solo tenemos el conjunto de entrenemiento y no de test, hacemos validación cruzada 
//No debemos crear el clasificador previamente para evitar sesgos. Lo hemos creado para enviarlo en el resultado
				Evaluation evalJ48 = new Evaluation(data);
				evalJ48.crossValidateModel(clasificadorJ48_2, data, 10, new Random(1));
				resultado.setClasificadorJ48(clasificadorJ48);
				resultado.setEvaluation(evalJ48);
				resultado.setInstances(data);
				System.out.println("Evaluación: " + resultado.getEvaluation().toSummaryString() + "\n\n"
						+ resultado.getClasificadorJ48().toString());
				msg1.setContentObject((Serializable) resultado);
// Envío del mensaje al agente con el resultado obtenido
				this.myAgent.send(msg1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class ComportamientoKnn extends OneShotBehaviour {
		public void action() {
			try {
				IBk clasificadorKnn = new IBk();
				IBk clasificadorKnn_2 = new IBk();
				Instances data = new Instances(new BufferedReader(new FileReader(fichero)));
				data.setClassIndex(data.numAttributes() - 1);
				clasificadorKnn.buildClassifier(data);
//Si solo tenemos el conjunto de entrenemiento y no de test, hacemos validación cruzada
//No debemos crear el clasificador previamente para evitar sesgos. Lo hemos creado para enviarlo en el resultado
				Evaluation evalKNN = new Evaluation(data);
				evalKNN.crossValidateModel(clasificadorKnn_2, data, 10, new Random(1));
				resultado.setClasificadorKnn(clasificadorKnn);
				resultado.setEvaluation(evalKNN);
				resultado.setInstances(data);
				System.out.println("Evaluación: " + resultado.getEvaluation().toSummaryString() + "\n\n"
						+ resultado.getClasificadorKnn().toString());
				msg1.setContentObject((Serializable) resultado);
// Envío del mensaje al agente con el resultado obtenido
				this.myAgent.send(msg1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}