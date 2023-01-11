package Behaviours;

import ch.qos.logback.core.util.COWArrayList;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BuyerReceive extends Behaviour {

    private String book;
    private List<ACLMessage> answers = new ArrayList<>();
    private int receiversCount;
    private List<Integer> prices = new ArrayList<>();
    private int minprice;
    private List<AID> senders = new ArrayList<>();

    public BuyerReceive(String book, int receiversCount){
        this.book = book;
        this.receiversCount = receiversCount;
    }


    @Override
    public void action() {
//        MessageTemplate mt = MessageTemplate.and(
//                MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
//                MessageTemplate.MatchPerformative(ACLMessage.FAILURE)),
//                MessageTemplate.MatchProtocol(book));
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                MessageTemplate.MatchPerformative(ACLMessage.FAILURE));
        ACLMessage receive = myAgent.receive(mt);
        if (receive != null && !senders.contains(receive.getSender())) {
            senders.add(receive.getSender());
            log.info("I received answers from {} with price {} for book {}", receive.getSender().getLocalName(), receive.getContent(), receive.getProtocol());
            answers.add(receive);
            if (receive.getPerformative() == ACLMessage.PROPOSE){
                prices.add(Integer.valueOf(receive.getContent()));
            }
        }else {
            block();
        }
        if (receiversCount == answers.size()){
            if (prices.size() != 0){
                minprice = prices.get(0);
                for (Integer price: prices){
                    if (price < minprice){
                        minprice = price;
                    }
                }
                log.info("Minimal price for book {} is {}", book, minprice);
            }else {
                log.warn("Sellers don't have this book");
            }

        }
    }

    @Override
    public boolean done() {
        return receiversCount == answers.size();

    }
}
