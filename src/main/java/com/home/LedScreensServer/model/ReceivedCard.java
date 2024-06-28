package com.home.LedScreensServer.model;

import lombok.Getter;
import org.springframework.stereotype.Component;


public class ReceivedCard {
    @Getter private LedScreen ledScreen;
    ModbusLedRegisters modbusLedRegisters;
    @Getter
    int number;

    public ReceivedCard(int number) {
        ledScreen = new LedScreen(128, 64);
        modbusLedRegisters = new ModbusLedRegisters();
        this.number = number;
    }

}
