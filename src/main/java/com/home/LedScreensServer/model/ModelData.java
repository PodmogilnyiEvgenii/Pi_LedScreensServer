package com.home.LedScreensServer.model;

import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class ModelData {
    List<ReceivedCard> receivedCardList;
    Enumeration<NetworkInterface> networks;


    public ModelData() {
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

    public void setNetworks(Enumeration<NetworkInterface> networks) {
        this.networks = networks;
    }
}
