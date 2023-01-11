package Agents;

import Behaviours.BuyerFSM;
import Behaviours.BuyerRequest;
import Helpers.DFHelper;
import ch.qos.logback.core.util.COWArrayList;
import jade.core.AID;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class BuyerAgent extends Agent {
    private String neededBook;
    private int bookNum;
    private String book;
    private List<AID> receivers;
    private List<String> books = new ArrayList<>();
    private List<AID> agents;

    @Override
    protected void setup() {
        DFHelper.registerAgent(this, "Buyer");
        for (int i = 0; i < 2; i++){
            bookNum = new Random().nextInt(3);
            switch (bookNum){
                case 0:
                    book = "War and peace";
                    if (!books.contains(book)){
                        books.add(book);
                    }else {
                        i--;
                    }
                    break;
                case 1:
                    book = "Oblomov";
                    if (!books.contains(book)){
                        books.add(book);
                    }else {
                        i--;
                    }
                    break;
                case 2:
                    book = "Green mile";
                    if (!books.contains(book)){
                        books.add(book);
                    }else {
                        i--;
                    }
            }
        }
        log.info("Buyer looking for {}", books);
        addBehaviour(new BuyerFSM(books));
//        addBehaviour(new BuyerRequest(this, 1000, books.get(0)));
//        addBehaviour(new BuyerRequest(this, 10000, books.get(1)));
    }
}