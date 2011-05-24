import ei.onto.negotiation.Negotiate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class VickerySeller extends ei.agent.ExternalAgent {

	public void setup(){
		super.setup();
		
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Seller");
		sd.setType("");
		super.regService(sd);		
	}
	
	public boolean sendRequest(ACLMessage request, Negotiate negotiate){
		
		DFAgentDescription[] template = searchDF(request, negotiate); // get the description of the Auctioneer
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); // prepare request
		msg.addReceiver(template[0].getName()); // add receiver to the new message
		msg.setContent("I am sellings 5 cars for now");
			
		send(msg); // send the message
			
		return false;
	}
	
	private DFAgentDescription[] searchDF(ACLMessage request, Negotiate negotiate) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Auctioneer");
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
		// on click button
		// create itselft and 
		
		// send request on button click
		return false;
	}
	
	

}
