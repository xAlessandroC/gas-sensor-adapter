package it.unibo.disi.gassensoradapter.database.MQTTConsumer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("cons-ttn")
@Lazy
public class GasSensorStatusTTNMQTTConsumer extends GasSensorStatusMQTTConsumer{

    private static final String S_TOPIC = "v3/gas-sensor-app@ttn/devices/eui-f2905f78ac886400/up";
    private static final String S_ADDRESS = "eu1.cloud.thethings.network";
    private static final String S_PORT = "8883";
    private static final String S_USER = "gas-sensor-app@ttn";
    private static final String S_PASSWORD = "NNSXS.5DXOOGOE7PITT576YNNTXQHQNIETBNL62UYGFEI.UETABWIBEVEN7NR7QQEDL4S2OD5QTQSBTMWH5NWJTNBCBKY4GMRQ";
    
    public GasSensorStatusTTNMQTTConsumer() {
        super(S_TOPIC, S_ADDRESS, S_PORT, S_USER, S_PASSWORD);
        this.logger = LoggerFactory.getLogger(GasSensorStatusTTNMQTTConsumer.class);
        this.logger.info("Starting TTN MQTT Consumer...");
    }

    protected String parsePayload(final String fullMessage){
        logger.info("Full message is " + fullMessage);

        String firstpart = this.jsonSubset(fullMessage, "decoded_payload", "rx_metadata");
        String secondpart = jsonSubset(fullMessage, "received_at", "uplink_message");
        String payload = "{\"timestamp\":" + secondpart + "," + firstpart.substring(1);

        return payload;
    }
    
    private String jsonSubset(final String fullMessage, final String startingFieldName, final String nextFieldName) {
        String result = "";
        final String startingString = startingFieldName + "\":";
        final String endingString = ",\"" + nextFieldName;
        final int startingIndex = fullMessage.indexOf(startingString);
        final int endingIndex = fullMessage.indexOf(endingString);
        result = fullMessage.substring(startingIndex + startingString.length(), endingIndex);
        result = result.trim();
        // result = result.substring(1, result.length()-1);
        return result.trim().replace("\\", "");
    }
}
