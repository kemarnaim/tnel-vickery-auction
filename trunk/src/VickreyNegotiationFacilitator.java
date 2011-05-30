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
import jade.lang.acl.MessageTemplate;
import ei.EIAgent;
import ei.onto.negotiation.Negotiate;
import ei.onto.normenv.report.NewContract.Frame;
import ei.service.negotiation.NegotiationFacilitator;
import ei.*;

  
public class VickreyNegotiationFacilitator extends ei.service.negotiation.NegotiationFacilitator {

	
	@Override
	public void setup()
	{
		super.setup();
		//ServiceDescription sd = new ServiceDescription();
		//sd.setName("Auctioneer");
		//sd.setType("Auctioneer");
		//super.regService(sd);
	}
	
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
	@Override
	protected Behaviour createResponderForNegotiateActionRequest(Agent agent, ACLMessage request, Negotiate negotiate) {
		
		System.out.println("Auctioneer received a message from: " + request.getSender().getName());
		if (request.getPerformative() == ACLMessage.REQUEST)
		{
			System.out.println("Auctioneer received a request from: " + request.getSender().getName());
			return new AuctionInit(agent, request, negotiate);
		}

		return null;
	}

	@Override
	protected boolean createGUI() {

		new ei.agent.gui.MessagesGUI(this);
		
		return false;
	}

}
