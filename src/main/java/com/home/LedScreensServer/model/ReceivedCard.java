package com.home.LedScreensServer.model;

import org.springframework.stereotype.Component;


public class ReceivedCard {
    LedScreen ledScreen;
    ModbusLedRegisters modbusLedRegisters;
    int number;

    public ReceivedCard(int number) {
        ledScreen = new LedScreen(128, 64);
        modbusLedRegisters = new ModbusLedRegisters();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
