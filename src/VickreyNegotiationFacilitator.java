import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import ei.EIAgent;
import ei.onto.negotiation.Negotiate;
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
		// TODO Auto-generated method stub
		Behaviour AuctionInit = new AuctionInit();
		
		
		return null;
	}

	@Override
	protected boolean createGUI() {
		// TODO Auto-generated method stub
		return false;
	}

}
