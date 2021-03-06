package it.unibo.disi.gassensoradapter.database.MQTTConsumer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("cons-arces")
@Lazy
public class GasSensorStatusArcesMQTTConsumer extends GasSensorStatusMQTTConsumer{

    private static final String S_TOPIC = "application/1/device/f2905f78ac886400/event/up";
    private static final String S_ADDRESS = "192.168.1.70";
    private static final String S_PORT = "1883";
    private static final String S_USER = "loraarces";
    private static final String S_PASSWORD = "ercoledecastro";
    
    public GasSensorStatusArcesMQTTConsumer() {
        super(S_TOPIC, S_ADDRESS, S_PORT, S_USER, S_PASSWORD);
        this.logger = LoggerFactory.getLogger(GasSensorStatusArcesMQTTConsumer.class);
        this.logger.info("Starting Arces MQTT Consumer...");
    }

    protected String parsePayload(final String fullMessage){
        logger.info("Full message is " + fullMessage);

        String firstpart = this.jsonSubset(fullMessage, "objectJSON", "tags");
        String secondpart = this.jsonSubset(fullMessage, "publishedAt", "deviceProfileID");

        logger.info("Full message is " + secondpart);

        String payload = "{\"timestamp\":\"" + secondpart + "\"," + firstpart.substring(1);

        return payload;

        // return this.jsonSubset(fullMessage, "objectJSON", "tags");
    }
    
    private String jsonSubset(final String fullMessage, final String startingFieldName, final String nextFieldName) {
        String result = "";
        final String startingString = startingFieldName + "\":";
        final String endingString = ",\"" + nextFieldName;
        final int startingIndex = fullMessage.indexOf(startingString);
        final int endingIndex = fullMessage.indexOf(endingString);
        result = fullMessage.substring(startingIndex + startingString.length(), endingIndex);
        result = result.trim();
        result = result.substring(1, result.length()-1);
        return result.trim().replace("\\", "");
    }
}
