
import ei.agent.enterpriseagent.ontology.Good;
import ei.onto.negotiation.Negotiate;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class AuctionInit extends ContractNetInitiator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4525714406937616488L;

	private ACLMessage request;
	private Negotiate neg;


	public AuctionInit(Agent a, ACLMessage request, Negotiate negotiate) {
		super(a, new ACLMessage(ACLMessage.CFP));
		this.neg = negotiate;
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
	//  send CFPs to the bidders
	protected Vector<ACLMessage> prepareCfps(ACLMessage cfp)
	{
		// look for the bidders in the DF
		DFAgentDescription[] template = searchForBidders(cfp);
		// prepare request
		Vector<ACLMessage> result = new Vector<ACLMessage>(); // prep result vector
		
		Good theGood = neg.getGood();
		cfp.setContent("Offer is");
		try {
			cfp.setContentObject(theGood);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Offer:" + request.getContent() + "by" + request.getSender());
		
		for (int i = 0; i < template.length; i++)
		{
			cfp.addReceiver(template[i].getName());
		}
		result.add(cfp);
		return result;
	}
	//This method is called when all the responses have been collected or when the timeout is expired.
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) 
	{		
		//int result = 0;
		if(responses.size() < 2) { // the bideers must be more than 1
			// failure - cannot continue with the auction
			//return;
		} else {
			
			for (int i = 0; i < responses.size(); i++)
			{
				//check if the bidders propose
				if (((ACLMessage)responses.get(i)).getPerformative() == ACLMessage.PROPOSE )
				{
					//put them in acceptances - in order everyone to receive the message
					acceptances.add(((ACLMessage)responses.get(i)).createReply());
					((ACLMessage) acceptances.get(i)).setContent(((ACLMessage)responses.get(i)).getContent());
					System.out.println("accepted: " + ((ACLMessage) acceptances.get(i)).getContent());
				}
			}
		}
		// create the comparator for the messages 
		Comparator<ACLMessage> c = new Comparator<ACLMessage>() { 
			@Override
			public int compare(ACLMessage o1, ACLMessage o2) {
	
				int first = Integer.parseInt(o1.getContent());
				int second = Integer.parseInt(o2.getContent());
				
				if ( first == second ){
					return 0;
				}
				else if (first < second){
					return -1;
				}
				return 1;
			}
		};
		
		sortMessages(acceptances, c); // sort the messages 
		 
		System.out.println("print the sorted bids");
		for(int i = 0; i < acceptances.size(); i++)
		{
			System.out.println("bid"+ i + ": " + ((ACLMessage)acceptances.get(i)).getContent());
		}
		
		// heeey losers 
		// set performative as REJECT_PROPOSAL except for the last one (he/she's the winner
		for (int i = 0; i < acceptances.size() - 1; i++) 
		{
			((ACLMessage)acceptances.get(i)).setPerformative(ACLMessage.REJECT_PROPOSAL);
		}
		
		/// heeeey winner!!
		//edit the message for the winner
		((ACLMessage)acceptances.get(acceptances.size() - 1)).setPerformative(ACLMessage.ACCEPT_PROPOSAL);
		// the winner pays the proposal of the second highest bidder
		((ACLMessage)acceptances.get(acceptances.size() - 1)).setContent("You have to pay: " + ((ACLMessage)acceptances.get(acceptances.size() - 2)).getContent());
	}

	private void sortMessages (Vector<ACLMessage> acceptances, Comparator<ACLMessage> c) {
		Collections.sort(acceptances, c);
	}
	
	/**
	 * Help method to search in the DF 
	 * @param request
	 * @return
	 */
	private DFAgentDescription[] searchForBidders(ACLMessage request) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("IR-enterprise-agent");
		sd.setName("Bidder");
		template.addServices(sd);
		DFAgentDescription[] results = null;
		
		try {
			results = DFService.search(myAgent, template);
			
		} catch(FIPAException fe){
			fe.printStackTrace();
			System.err.println("AuctionInit.searchForBidders() : " + fe.getMessage());
		}
		
		return results;
	}


	@Override
	public int onEnd()
	{
		// send to the seller. request was firstly sent by him
		ACLMessage reply = request.createReply();
		reply.setContent("the winner is:");
		reply.setPerformative(ACLMessage.INFORM); // depending if it is failure or ...
		myAgent.send(reply);
		
		return 0;
		

	}
}
