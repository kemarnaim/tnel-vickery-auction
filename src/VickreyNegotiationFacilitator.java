import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import ei.EIAgent;
import ei.onto.negotiation.Negotiate;
import ei.service.negotiation.NegotiationFacilitator;
import ei.*;


public class VickreyNegotiationFacilitator extends ei.service.negotiation.NegotiationFacilitator {

	@Override
	/**
	 * This method should return a behaviour capable of handling a negotiate action request.
	 * In particular, such a behaviour should answer appropriately to that request.
	 * A typical use is to start a negotiation process, and reply to the request according to the outcome of that process, by implementing onEnd().
	 * 
	 * @param agent
	 * @param request
	 * @param negotiate
	 * @return
	 */
	protected Behaviour createResponderForNegotiateActionRequest(Agent agent,
			ACLMessage request, Negotiate negotiate) {
		
		// The AuctionInit behaviour should reply to the
		// request at the end of the negotiation protocol (auction), 
		// providing information about the outcome of the auction.
		Behaviour AuctionInit = new AuctionInit(agent, request, negotiate);
		
		
		// PUT ALL THIS CODE IN AUCTIONINIT.JAVA on prepareCFP
		
		
		// Searchs in DF for potential buyers
		DFAgentDescription[] results = searchDF(request, negotiate);
		// Sends everyone the request
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		
		//for
		msg.addReceiver(results[1].getName());

	
		
		
		
		
		// if true:
			// create the behaviour for the vickery auction
		
		// if not
			// sends failure
		ACLMessage response = request.createReply();
		response.setPerformative(ACLMessage.FAILURE);
		send(response);
		return null;
		
	}

	
	
	
	private DFAgentDescription[] searchDF(ACLMessage request,
			Negotiate negotiate) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Vickery");
		template.addServices(sd);
		DFAgentDescription[] results = null;
		
		try {
			results = DFService.search(this, template);
			
		} catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		return results;
	}

	@Override
	protected boolean createGUI() {
		// TODO Auto-generated method stub
		return false;
	}

}
