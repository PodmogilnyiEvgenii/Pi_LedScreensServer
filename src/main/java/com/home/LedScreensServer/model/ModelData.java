package com.home.LedScreensServer.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelData {
    private final Network network;
    private final LedScreen ledScreen;

    public ModelData(Network network, LedScreen ledScreen) {
        this.network = network;
        this.ledScreen = ledScreen;
    }
}
