import java.awt.Button;
import java.awt.Frame;
import java.util.Vector;

import ei.ElectronicInstitution;
import ei.agent.enterpriseagent.gui.EnterpriseAgentGUI;
import ei.agent.enterpriseagent.ontology.Good;
import ei.agent.gui.MsgViewerGUI;
import ei.onto.negotiation.Negotiate;
import ei.service.negotiation.qfnegotiation.NegotiationOutcome;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;
 
 
@SuppressWarnings("serial")
public class Seller extends ei.agent.ExternalAgent {
	/**
	 * Registering Vickrey Seller
	 */
	public void setup(){
		super.setup();
		
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getLocalName());
		sd.setType((String) arguments.get("role"));
		Vector<ServiceDescription> sds = new Vector<ServiceDescription>();
		sds.add(sd);
		super.regServices(sds); // register the service
		
		 //register the ontology in the DF
		getContentManager().registerOntology(ei.onto.negotiation.NegotiationOntology.getInstance());

		addBehaviour(new NegotiationInit_Negotiate(this, new AID("Auctioneer", false)));
	
	}
	/*// search the DF for auctioneer NOT USED YET
	private DFAgentDescription[] searchDF(ACLMessage request, Negotiate negotiate) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		//sd.setType("Auctioneer");
		sd.setName("Auctioneer");
		template.addServices(sd);
		DFAgentDescription[] results = null;
		
		try {
			results = DFService.search(this, template);
		} catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		return results; 
	} */
	
	@Override
	protected boolean createGUI() {

		this.gui = new Frame();
		Button btt = new Button("bla");
		gui.add(btt);
	

		return true;
	}
	
	// seller's behaviour; the type of behaviour expected from the auctioneer
	private class NegotiationInit_Negotiate extends AchieveREInitiator {
		private static final long serialVersionUID = 5915332397020918685L;

		private AID negotiationMediatorAID; 
		protected boolean inSynchronizedExperiment = false;
		private String contractType = "Vickrey Auction";

		@SuppressWarnings("unused")
		private NegotiationOutcome negotiationOutcome; //  TODO the result for the Seller (see the winner)

		public NegotiationInit_Negotiate(Agent ag, AID negotiationMediatorAID) {
			super(ag, new ACLMessage(ACLMessage.REQUEST));
			
			this.negotiationMediatorAID = negotiationMediatorAID;
		}

		protected Vector<ACLMessage> prepareRequests(ACLMessage req) {
			Vector<ACLMessage> reqs = new Vector<ACLMessage>();
			req.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			req.setOntology(ElectronicInstitution.NEGOTIATION_ONTOLOGY);
			req.setLanguage(getLeapCodec().getName());  // CHANGE TO LEAPCODEC
			req.addReceiver(negotiationMediatorAID);
			Negotiate negotiate = new Negotiate("Vickrey Auction");
			Good ourGood = new Good("Car");
			// TODO think about the goods 
			negotiate.setGood(ourGood);
			negotiate.setContractType(contractType);

			AID actor = myAgent.getAID();  // weirddd!??
			Action act = new Action(actor, negotiate);

			try { //insert Request Object as the content element of this aclmessage
				getContentManager().fillContent(req, act);
			} catch(OntologyException oe) {
				oe.printStackTrace();
			} catch(Codec.CodecException ce) {
				ce.printStackTrace();
			}
			reqs.add(req);
			return reqs;
		}

		protected void handleInform(ACLMessage inform) {
			if(!inSynchronizedExperiment) {
				//				//receive the contract generated from the successful negotiation
				//				ContractWrapper cw = new ContractWrapper(((EnterpriseAgent) myAgent).xsd_file);
				//				cw.unmarshal(inform.getContent(), true);
				//				// save negotiated contract in the to-be-signed contracts ArrayList for future signing
				//				if(!toBeSignedContracts.contains(cw)) {   // only one copy of the contract, since this agent will sign it only once!
				//					toBeSignedContracts.add(cw);
				//				}
			} else {
				try {
					negotiationOutcome = (NegotiationOutcome) inform.getContentObject();
					// some output i suppose
					System.out.println("Seller received that winner is: " + inform.getContent().toString());
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			}
			if(getGui() != null) {
				//((EnterpriseAgentGUI) ((EnterpriseAgent) myAgent).gui).reSet();
			}
		}

		protected void handleFailure(ACLMessage failure) {
			log("Negotiation failed!");
			if(getGui() != null) {
				MsgViewerGUI mv = new MsgViewerGUI(failure, myAgent, gui);
				mv.setVisible(true);
				((EnterpriseAgentGUI) gui).reSet();
			}
		}
		/**
		 * Handle refuse.
		 * @param refuse the refuse
		 */
		protected void handleRefuse(ACLMessage refuse) {
			log("Negotiation refused!");
			if(getGui() != null) {
				MsgViewerGUI mv = new MsgViewerGUI(refuse, myAgent, gui);
				mv.setVisible(true);
				((EnterpriseAgentGUI) gui).reSet();
			}
		}

		protected void handleAgree(ACLMessage agree) {
			log(agree.getContent());
		}

		protected void handleNotUnderstood(ACLMessage notUnderstood) {
			log("Negotiation not understood!");
			if(getGui() != null) {
				MsgViewerGUI mv = new MsgViewerGUI(notUnderstood, myAgent, gui);
				mv.setVisible(true);
				((EnterpriseAgentGUI) gui).reSet();
			}
		}
	}
	
}
