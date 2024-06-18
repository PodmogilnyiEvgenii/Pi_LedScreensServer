package com.home.LedScreensServer.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Model {
    List<ReceivedCard> receivedCardList;

    public Model() {
        receivedCardList = new ArrayList<>();
        loadOptions();
    }

    public boolean loadOptions() {
        receivedCardList.add(new ReceivedCard(0));
        receivedCardList.add(new ReceivedCard(1));
        receivedCardList.add(new ReceivedCard(2));
        receivedCardList.add(new ReceivedCard(3));
        return false;
    }

    public boolean saveOptions() {
        return false;
    }

    public List<ReceivedCard> getReceivedCardList() {
        return receivedCardList;
    }
}
