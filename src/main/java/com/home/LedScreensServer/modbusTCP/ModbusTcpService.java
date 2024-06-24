package com.home.LedScreensServer.modbusTCP;

import com.home.LedScreensServer.model.ModelData;
import com.home.LedScreensServer.model.ReceivedCard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//import static net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage.readHoldingsRequest;

//@Component
public class ModbusTcpService {
    List<ModbusTcpServer> serverList;
    ModelData modelData;

    public ModbusTcpService(ModelData modelData) {
        serverList = new ArrayList<ModbusTcpServer>();
        this.modelData = modelData;

        for (ReceivedCard receivedCard: modelData.getReceivedCardList()) {
            serverList.add(new ModbusTcpServer(receivedCard));
        }
    }

}
