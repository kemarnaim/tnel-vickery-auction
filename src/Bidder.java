import java.util.Vector;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;


public class Bidder extends ei.agent.ExternalAgent /*implements AuctionResp */{ 

	public void setup(){
		super.setup();
		
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getLocalName());
		sd.setType((String) arguments.get("role"));
		
		Vector<ServiceDescription> sds = new Vector<ServiceDescription>();
		
		sds.add(sd);
		
		super.regServices(sds);

		addBehaviour(new BidderBehaviour(this, MessageTemplate.MatchPerformative(ACLMessage.CFP)));

	}

	
	@Override
	protected boolean createGUI() {
		// TODO Auto-generated method stub
		
		new ei.agent.gui.MessagesGUI(this);
		return false;
	} 
	
	////////////////////
	///// bidder's behaviour
	///////////////
	
	private class BidderBehaviour extends ContractNetResponder {

		private static final long serialVersionUID = -6573518806436767092L;

		public BidderBehaviour(Agent a, MessageTemplate mt) {
			super(a, mt);
			// TODO Auto-generated constructor stub
		}

		// received CFP message from the Auctioneer and bid
		@Override
		protected ACLMessage handleCfp(ACLMessage cfp)
		{

			ACLMessage reply = cfp.createReply(); // create a reply for the cfp
			reply.setPerformative(ACLMessage.PROPOSE);	// change its performative to ACCEPT
			
			// TODO set the bid in the message
			reply.setContent((String)arguments.get("bid")); // get the bid from the arguments in conf file
			System.out.println("sent a bid to the auctioneer");
			return reply;
		
		}
		// for the winner
		@Override
		protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException
	     {
			accept.setContent(" Thank you!" );
			
			//TODO ask teacher here
			
			return accept;
	     
	     }
		
		//TODO handle reject proposals for losers
		@Override
		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject)
	     {
			System.out.println("You lose!");
	     }
	}
 
} 
