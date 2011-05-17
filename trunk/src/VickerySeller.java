import ei.onto.negotiation.Negotiate;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class VickerySeller extends ei.agent.ExternalAgent {

	public void setup(){
		super.setup();
		
		ServiceDescription sd = new ServiceDescription();
		sd.setName("seller");
		sd.setType("");
		super.regService(sd);
		
	}
	
	public boolean sendRequest(ACLMessage request, Negotiate negotiate){
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Vickery");
		template.addServices(sd);
		/*
		try {
			DFAgentDescription[] results = DFService.search(this, template);
			for(int i=0; i<results.length;++i){
				
			}
		} catch(){
			
		}*/
		
		return false;
	}
	
	@Override
	protected boolean createGUI() {
		// TODO Auto-generated method stub
		return false;
	}

}
