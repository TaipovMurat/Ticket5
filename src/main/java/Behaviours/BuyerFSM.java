package Behaviours;

import jade.core.behaviours.FSMBehaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class BuyerFSM extends FSMBehaviour {
    private static final String SEND_FIRST = "Send first request";
    private static final String WAIT_FIRST = "Wait response for first";
    private static final String SEND_SECOND = "Send second request";
    private static final String WAIT_SECOND = "Wait response for second";

    private String book2;
    private String book1;
    List<String> books = new ArrayList<>();

    public BuyerFSM(List<String> books){
        this.books = books;
        book1 = books.get(0);
        book2 = books.get(1);


        registerFirstState(new BuyerRequest(getAgent(), 1000, book1), SEND_FIRST);
        registerState(new BuyerReceive(book1, 3), WAIT_FIRST);
        registerState(new BuyerRequest(getAgent(), 1000, book2), SEND_SECOND);
        registerLastState(new BuyerReceive(book2, 3), WAIT_SECOND);

        registerDefaultTransition(SEND_FIRST,WAIT_FIRST);
        registerDefaultTransition(WAIT_FIRST, SEND_SECOND);
        registerDefaultTransition(SEND_SECOND,WAIT_SECOND);
    }
}
