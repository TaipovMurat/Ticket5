package Behaviours;

import Helpers.DFHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
@Slf4j
public class SellerBehaviour extends Behaviour {


    private List<String> books;
    private List<AID> agents;
    private int price;
    private int high = 400;
    private int low = 200;
    private ACLMessage msg = new ACLMessage(ACLMessage.CFP);
    private int response = 0;
//    private boolean response;


    public SellerBehaviour(List<String> books) {
        this.books = books;
    }


    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null){
            agents = DFHelper.findAgents(getAgent(),"Buyer");
            if (books.contains(receive.getContent())){
                price = new Random().nextInt(high-low)+low;
                log.info("It's {} dollars for {}", price, receive.getContent());
                msg.setPerformative(ACLMessage.PROPOSE);
                msg.setProtocol(receive.getContent());
                msg.setContent(String.valueOf(price));
            }else {
                log.info("Agent {} don't have needed book", getAgent().getLocalName());
                msg.setPerformative(ACLMessage.FAILURE);
                msg.setProtocol(receive.getContent());
                msg.setContent("I'm sorry :( ");
            }


            for (AID agent: agents){
                msg.addReceiver(agent);
            }
            response++;
//            response = true;
            getAgent().send(msg);
//            log.info("Agent {} send his response {}", getAgent().getLocalName(), msg.getContent());


        }else {
            block();
        }

    }

    @Override
    public boolean done() {
        return response == 2;
    }
}
