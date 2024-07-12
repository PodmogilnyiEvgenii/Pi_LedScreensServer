package com.home.LedScreensServer.modbusTCP;

import com.home.LedScreensServer.model.ModelData;

//import static net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage.readHoldingsRequest;

//@Component
public class ModbusTcpService {
    ModelData modelData;

    public ModbusTcpService(ModelData modelData) {
        this.modelData = modelData;
    }

}
