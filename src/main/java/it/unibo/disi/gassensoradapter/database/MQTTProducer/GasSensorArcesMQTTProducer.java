package it.unibo.disi.gassensoradapter.database.MQTTProducer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("prod-arces")
@Lazy
public class GasSensorArcesMQTTProducer extends GasSensorMQTTProducer {
    
    private static final String S_TOPIC = "application/1/device/f2905f78ac886400/command/down";
    private static final String S_ADDRESS = "boswamp-2.arces.unibo.it";
    private static final String S_PORT = "8883";
    private static final String S_USER = "loraarces";
    private static final String S_PASSWORD = "ercoledecastro";
    
    public GasSensorArcesMQTTProducer() {
        super(S_TOPIC, S_ADDRESS, S_PORT, S_USER, S_PASSWORD);
        this.logger = LoggerFactory.getLogger(GasSensorArcesMQTTProducer.class);
        this.logger.info("Starting Arces MQTT Producer...");
    }

}
