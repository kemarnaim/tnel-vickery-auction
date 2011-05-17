import antlr.collections.impl.Vector;
import ei.onto.negotiation.Negotiate;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;


public class AuctionInit extends ContractNetInitiator {

	
	public AuctionInit(Agent a, ACLMessage cfp, Negotiate negotiate) {
		super(a, new ACLMessage(ACLMessage.CFP));
		
	}
	
	
	//prepareCFP -> Deals with the goods and stuff
	/**
    This method must return the vector of ACLMessage objects to be sent. 
	It is called in the first state of this protocol. 
	This default implementation just returns the ACLMessage object (a CFP) passed in the constructor. 
	Programmers might prefer to override this method in order to return a vector of CFP objects 
	for 1:N conversations or also to prepare the messages during the execution of the behaviour. 
	 */
	protected java.util.Vector prepareCfps(ACLMessage cfp){
		return null;
		
	}

}
