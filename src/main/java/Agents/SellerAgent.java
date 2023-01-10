package Agents;

import Behaviours.SellerBehaviour;
import Helpers.DFHelper;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Slf4j
public class SellerAgent extends Agent {

    private List<String> books = new ArrayList<>();
    private String book;
    private int bookNum;

    @Override
    protected void setup() {
        DFHelper.registerAgent(this,"Seller");
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
        log.info("Agent {} has these books: {}", this.getLocalName(), books);
        addBehaviour(new SellerBehaviour(books));
    }
}
