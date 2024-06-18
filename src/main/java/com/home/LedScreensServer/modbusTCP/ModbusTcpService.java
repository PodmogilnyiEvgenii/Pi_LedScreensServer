package com.home.LedScreensServer.modbusTCP;

import com.home.LedScreensServer.model.Model;
import com.home.LedScreensServer.model.ReceivedCard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//import static net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage.readHoldingsRequest;

@Component
public class ModbusTcpService {
    List<ModbusTcpServer> serverList;
    Model model;

    public ModbusTcpService(Model model) {
        serverList = new ArrayList<ModbusTcpServer>();
        this.model = model;

        for (ReceivedCard receivedCard:model.getReceivedCardList()) {
            serverList.add(new ModbusTcpServer(receivedCard));
        }
    }

}
