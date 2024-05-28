package es.upm.Project.Engine;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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


public class AgenteAnalizadorWeka extends Agent {
	
	private static final long serialVersionUID = 1L;
	protected ResultadoAnalisis resultado;
	protected Instances receivedData; // Variable para almacenar los datos recibidos
	protected Logistic clasificadorLogistico; // Variable para almacenar el clasificador entrenado
	
	protected CyclicBehaviourAnalisisWEKA comportamientoWEKA = new CyclicBehaviourAnalisisWEKA();
    protected ComportamientoEntrenarModelo comportamientoEntrenarModelo = new ComportamientoEntrenarModelo(); // Añadimos este comportamiento

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
		addBehaviour(comportamientoEntrenarModelo);
	}

	public class CyclicBehaviourAnalisisWEKA extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive(mt1);
			if (msg != null) {
				if (msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println(myAgent.getLocalName() + ": Recibo el mensaje: \n" + msg);
					try {
						// Asegurarse de que el clasificador ha sido entrenado
						if (clasificadorLogistico == null) {
							System.err.println("Clasificador no entrenado.");
							return;
						}

						// Recibimos los datos a analizar, que vienen en el mensaje
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
	                    ArrayList<String> classValues = new ArrayList<>(Arrays.asList("tested_negative", "tested_positive"));
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

	                    data.add(new DenseInstance(1.0, instanceValues));

	                    ((AgenteAnalizadorWeka) myAgent).receivedData = data;

						// Crear una instancia de evaluación para obtener el resultado
						double pred = clasificadorLogistico.classifyInstance(data.instance(0));
	                    String resultadoClasificacion = data.classAttribute().value((int) pred);
	                    	
	                    System.out.println(resultadoClasificacion); //aqui iria el resultado de la prediccion (POSITIVO / NEGATIVO)
						
	                    // Crear un objeto ResultadoAnalisis para enviar de vuelta al AgenteUsuario
						ResultadoAnalisis resultadoAnalisis = new ResultadoAnalisis();
						resultadoAnalisis.setResultado(resultadoClasificacion);

						// Serializar el objeto ResultadoAnalisis y enviarlo de vuelta al AgenteUsuario
						ACLMessage reply = msg.createReply();
						reply.setPerformative(ACLMessage.INFORM);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(resultadoAnalisis);
						oos.close();
						reply.setContent(baos.toString("ISO-8859-1"));
						myAgent.send(reply);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				this.block();
			}
		}
	}
	
	
	//Este comportamiento: 
	//	1.- Lee del dataset y lo separa en [entrenamiento] y [test]
	//	2.- Entrena con el algoritmo de clasificación Logística para obtener el modelo
	//	3.- Evalua el modelo con los datos de prueba
	//	4.- Se lo devuelve como resultado al agente
	
	public class ComportamientoEntrenarModelo extends OneShotBehaviour {
	    
	    private static final long serialVersionUID = 1L;

		@Override
	    public void action() {
	        try {
	            System.out.println("Entrenando modelo Logístico...");
	            Instances data = new Instances(new BufferedReader(new FileReader("resources/diabetes.arff")));

	            data.setClassIndex(data.numAttributes() - 1);
				
				// Dividir los datos en entrenamiento y prueba (por ejemplo, 75% entrenamiento, 25% prueba)
	            int trainSize = (int) Math.round(data.numInstances() * 0.70);
	            int testSize = data.numInstances() - trainSize;
	            Instances trainData = new Instances(data, 0, trainSize);
	            Instances testData = new Instances(data, trainSize, testSize);
	            
	            Logistic clasificadorLogistico = new Logistic();
	            clasificadorLogistico.buildClassifier(data); //Entrenar el Clasificador (OBTENGO EL MODELO)
	            
	            Evaluation evalLogistica = new Evaluation(trainData); 
	            evalLogistica.evaluateModel(clasificadorLogistico, testData); //evaluamos el modelo con los datos de test
	            
				((AgenteAnalizadorWeka) myAgent).resultado.setClasificadorLogistic(clasificadorLogistico);
				((AgenteAnalizadorWeka) myAgent).resultado.setEval(evalLogistica);
				((AgenteAnalizadorWeka) myAgent).resultado.setData(data);
				

				System.out.println("Evaluación: " + ((AgenteAnalizadorWeka) myAgent).resultado.getEval().toSummaryString() + "\n\n"
						+ ((AgenteAnalizadorWeka) myAgent).resultado.resultadosToString());
				        //+ ((AgenteAnalizadorWeka) myAgent).resultado.getClasificadorLogistic().toString());

				msg1.setContentObject((Serializable) ((AgenteAnalizadorWeka) myAgent).resultado);

				// Envío del mensaje al agente con el resultado obtenido
				myAgent.send(msg1);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


}
