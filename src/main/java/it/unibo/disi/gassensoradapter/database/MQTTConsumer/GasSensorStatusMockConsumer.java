package it.unibo.disi.gassensoradapter.database.MQTTConsumer;

import java.time.Instant;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.unibo.disi.gassensoradapter.entity.GasSensorStatusMQTTReadData;

@EnableScheduling
@Component("cons-mock")
@Lazy
public class GasSensorStatusMockConsumer extends GasSensorStatusConsumer{

    protected Logger logger = LoggerFactory.getLogger(GasSensorStatusMockConsumer.class);

    public GasSensorStatusMockConsumer() {
        this.logger = LoggerFactory.getLogger(GasSensorStatusMockConsumer.class);
        this.logger.info("Starting Mock Consumer...");
    }

    public void ClientInit() {
        return;
    }

    String parsePayload(String fullMessage) {
        return null;
    }

    @Scheduled(fixedDelayString = "${generation.delay}")
    private void generateData(){
        Random random = new Random(System.currentTimeMillis());
        double mock_humidity_min = 25.0;
        double mock_humidity_max = 35.0;
        double mock_humidity = random.nextDouble() * (mock_humidity_max - mock_humidity_min) + mock_humidity_min;

        double mock_resistance_min = 22000.0;
        double mock_resistance_max = 23000.0;
        double mock_resistance = random.nextDouble() * (mock_resistance_max - mock_resistance_min) + mock_resistance_min;

        double mock_temperature_min = 25.0;
        double mock_temperature_max = 40.0;
        double mock_temperature = random.nextDouble() * (mock_temperature_max - mock_temperature_min) + mock_temperature_min;

        double mock_voltage_min = 3690;
        double mock_voltage_max = 3890;
        double mock_voltage = random.nextDouble() * (mock_voltage_max - mock_voltage_min) + mock_voltage_min; 

        double mock_irradiance_min = 2067;
        double mock_irradiance_max = 2567;
        double mock_irradiance = random.nextDouble() * (mock_irradiance_max - mock_irradiance_min) + mock_irradiance_min; 

        int mock_dutycycle = 33;

        String mock_timestamp = "" + Instant.now();

        String jsonresponse = "{";
        jsonresponse += "\"timestamp\":\"" + mock_timestamp + "\", ";
        jsonresponse += "\"humidity\":" + mock_humidity + ", ";
        jsonresponse += "\"temperature\":" + mock_temperature + ", ";
        jsonresponse += "\"resistance\":" + mock_resistance + ", ";
        jsonresponse += "\"tensione_batteria\":" + mock_voltage + ", ";
        jsonresponse += "\"Wmq\":" + mock_irradiance + ", ";
        jsonresponse += "\"Dutycycle\":" + mock_dutycycle;
        jsonresponse += "}";

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("Json string:" + jsonresponse);
            final GasSensorStatusMQTTReadData readData = objectMapper.readValue(jsonresponse, GasSensorStatusMQTTReadData.class);
            logger.info("Java obj:" + readData);
            this.setData(readData);
            this.writeOnInflux();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
