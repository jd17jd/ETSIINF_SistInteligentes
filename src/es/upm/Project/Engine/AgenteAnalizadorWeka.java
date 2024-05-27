package es.upm.Project.Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import es.upm.Project.Engine.DatosAnalizar;
import es.upm.Project.Engine.ResultadoAnalisis;
//import es.upm.Project.Engine.AnalizadorWeka.CyclicBehaviourAnalisisWEKA;
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
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;


public class AgenteAnalizadorWeka extends Agent {
	
	protected ResultadoAnalisis resultado;
	protected Instances receivedData; // Variable para almacenar los datos recibidos
	
	protected CyclicBehaviourAnalisisWEKA comportamientoWEKA = new CyclicBehaviourAnalisisWEKA();
    protected ComportamientoLogistic comportamientoLogistic = new ComportamientoLogistic(); // Añadimos este comportamiento

	@SuppressWarnings("deprecation")
	private static ACLMessage msg1 = new ACLMessage();
	
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private static final MessageTemplate mt1 = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));

	
	protected void setup() {
		// CREAMOS SERVICIOS PROPORCIONADOS POR EL AGENTE Y LOS REGISTRAMOS EN LA PLATAFORMA
		// Creamos un servicio de tipo analisis y lo registramos en el directorio de servicios
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("WEKA_Analisis");
		
		// Establecemos el tipo del servicio para poder localizarlo cuando haga una búsqueda
		sd.setType("analisis");
		
		// Los agentes que quieran utilizar el servicio deberán usar el lenguaje de contenido FIPA-SL
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
		addBehaviour(comportamientoLogistic);
	}

	public class CyclicBehaviourAnalisisWEKA extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				if (msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println(myAgent.getLocalName() + ": Recibo el mensaje: \n" + msg);
					try {
						//Recibimos los datos a analizar, que vienen en el mensaje
						DatosAnalizar analizar = (DatosAnalizar) msg.getContentObject();
						
						// Convertir los datos recibidos en una instancia de WEKA
	                    ArrayList<Attribute> attributes = new ArrayList<>();
	                    attributes.add(new Attribute("embarazos"));
	                    attributes.add(new Attribute("edad"));
	                    attributes.add(new Attribute("nivel_glucosa"));
	                    attributes.add(new Attribute("presion_arterial"));
	                    attributes.add(new Attribute("pliegue_cutaneo"));
	                    attributes.add(new Attribute("insulina"));
	                    attributes.add(new Attribute("masa_muscular"));
	                    attributes.add(new Attribute("pedigri"));
	                    ArrayList<String> classValues = new ArrayList<>(Arrays.asList("false", "true"));
	                    attributes.add(new Attribute("class", classValues));

	                    Instances data = new Instances("Rel", attributes, 0);
	                    data.setClassIndex(data.numAttributes() - 1);

	                    double[] instanceValues = new double[data.numAttributes()];
	                    instanceValues[0] = analizar.getEmbarazos();
	                    instanceValues[1] = analizar.getEdad();
	                    instanceValues[2] = analizar.getNivel_glucosa();
	                    instanceValues[3] = analizar.getPresion_arterial();
	                    instanceValues[4] = analizar.getPliegue_cutaneo();
	                    instanceValues[5] = analizar.getInsulina();
	                    instanceValues[6] = analizar.getMasa_muscular();
	                    instanceValues[7] = analizar.getPedigri();
	                    instanceValues[8] = Instance.missingValue(); // Assuming class is unknown

	                    data.add(new DenseInstance(1.0, instanceValues));

	                    ((AgenteAnalizadorWeka) myAgent).receivedData = data;
						
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
	
	public class ComportamientoLogistic extends OneShotBehaviour {
	    
	    @Override
	    public void action() {
	        try {
	            System.out.println("Entro en comportamiento Logistic");
	            
	            Logistic clasificadorLogistico = new Logistic();
	            Logistic clasificadorLogistico_2 = new Logistic();

	            Instances data = ((AgenteAnalizadorWeka) myAgent).receivedData;

	            if (data != null) {
	                data.setClassIndex(data.numAttributes() - 1);
	                clasificadorLogistico.buildClassifier(data);

	                Evaluation evalLogistica = new Evaluation(data);
	                evalLogistica.crossValidateModel(clasificadorLogistico_2, data, 10, new Random(1));

	                ((AgenteAnalizadorWeka) myAgent).resultado.setClasificadorLogistic(clasificadorLogistico);
	                ((AgenteAnalizadorWeka) myAgent).resultado.setEvaluation(evalLogistica);
	                ((AgenteAnalizadorWeka) myAgent).resultado.setInstances(data);

	                System.out.println("Evaluación: " + ((AgenteAnalizadorWeka) myAgent).resultado.getEvaluation().toSummaryString() + "\n\n"
	                        + ((AgenteAnalizadorWeka) myAgent).resultado.getClasificadorLogistic().toString());

	                msg1.setContentObject((Serializable) ((AgenteAnalizadorWeka) myAgent).resultado);

	                // Envío del mensaje al agente con el resultado obtenido
	                myAgent.send(msg1);
	            } else {
	                System.out.println("No se han recibido datos para el análisis.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


}
