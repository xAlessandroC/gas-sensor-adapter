package it.unibo.disi.gassensoradapter.database.MQTTProducer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("prod-mock")
@Lazy
public class GasSensorMockMQTTProducer extends GasSensorMQTTProducer {
    
    private static final String S_TOPIC = "";
    private static final String S_ADDRESS = "";
    private static final String S_PORT = "";
    private static final String S_USER = "";
    private static final String S_PASSWORD = "";
    
    public GasSensorMockMQTTProducer() {
        super(S_TOPIC, S_ADDRESS, S_PORT, S_USER, S_PASSWORD);
        this.logger = LoggerFactory.getLogger(GasSensorMockMQTTProducer.class);
        this.logger.info("Starting Mock MQTT Producer...");
    }

    @Override
    public void ClientInit() {
        return;
    }

    @Override
    public boolean publishMessage(String message){
        return true;
    }


}
