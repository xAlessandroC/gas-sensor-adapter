package it.unibo.disi.gassensoradapter.database.MQTTProducer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("prod-ttn")
@Lazy
public class GasSensorTTNMQTTProducer extends GasSensorMQTTProducer {
    
    private static final String S_TOPIC = "v3/gas-sensor-app@ttn/devices/eui-f2905f78ac886400/down/push";
    private static final String S_ADDRESS = "192.168.1.2";
    private static final String S_PORT = "8883";
    private static final String S_USER = "gas-sensor-app";
    // private static final String S_PASSWORD = "NNSXS.5DXOOGOE7PITT576YNNTXQHQNIETBNL62UYGFEI.UETABWIBEVEN7NR7QQEDL4S2OD5QTQSBTMWH5NWJTNBCBKY4GMRQ";
    private static final String S_PASSWORD = "NNSXS.GU7D6Y4PUTNUDJUTGTYPZWVKI2XBMFVPPF33G3I.FG7VWLPCRTT36FAOB4M5LCYJTES3DA7COVON4K7QX5FXSPLQ2PAQ";
    

    public GasSensorTTNMQTTProducer() {
        super(S_TOPIC, S_ADDRESS, S_PORT, S_USER, S_PASSWORD);
        this.logger = LoggerFactory.getLogger(GasSensorMockMQTTProducer.class);
        this.logger.info("Starting TTN MQTT Producer...");
    }

}
