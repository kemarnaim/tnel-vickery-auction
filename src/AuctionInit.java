
import ei.onto.negotiation.Negotiate;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import java.util.Vector;

public class AuctionInit extends ContractNetInitiator {
	private ACLMessage request;


	public AuctionInit(Agent a, ACLMessage request, Negotiate negotiate) {
		super(a, new ACLMessage(ACLMessage.CFP));
		
		this.request = request;
	}
	
	//prepareCFP -> Deals with the goods and stuff
	/**
    This method must return the vector of ACLMessage objects to be sent. 
	It is called in the first state of this protocol. 
	This default implementation just returns the ACLMessage object (a CFP) passed in the constructor. 
	Programmers might prefer to override this method in order to return a vector of CFP objects 
	for 1:N conversations or also to prepare the messages during the execution of the behaviour. 
	 */
	protected Vector<ACLMessage> prepareCfps(ACLMessage cfp)
	{
		// get the description of the Auctioneer
		DFAgentDescription[] template = searchDF(cfp);
		// prepare request
		
		Vector<ACLMessage> result = new Vector<ACLMessage>(); // prep result vector
		cfp.setContent("Do you want to buy a car?");
		for (int i = 0; i < template.length; i++)
		{
			cfp.addReceiver(template[i].getName());
		}
		result.add(cfp);
		return result;
	}
	//This method is called when all the responses have been collected or when the timeout is expired.
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) 
	{
		if(responses.size() < 2){
			//failure
		} else {
			for (int i = 0; i < responses.size(); i++)
			{
				responses.get(i);
				if (responses.get(i))
				{
					
				}
			}
		}
	}
	
	
	
	private DFAgentDescription[] searchDF(ACLMessage request) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Bidder");
		template.addServices(sd);
		DFAgentDescription[] results = null;
		
		try {
			results = DFService.search(myAgent, template);
			
		} catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		return results;
	}

	public int onEnd()
	{
		ACLMessage reply = request.createReply();
		reply.setContent("the winer is:");
		reply.setPerformative(ACLMessage.INFORM); // depending if it is failure or ...
		myAgent.send(reply);
		
		return 0;
		

	}
}
