package it.unibo.disi.gassensoradapter.database;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import eu.arrowhead.common.exception.InvalidParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Lazy
public class MockGasSensorStatusDB extends ConcurrentHashMap<Integer, GasSensorStatus>
{
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(MockGasSensorStatusDB.class);
    private static final long serialVersionUID = -2462387539362748691L;
    private int idCounter;
    
    public MockGasSensorStatusDB() {
        logger.info("Starting MockGasSensorStatusDB...");
        this.idCounter = 0;
    }
    
    public GasSensorStatus readDataFromMQTT() {
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


        return this.create(mock_humidity, mock_resistance, mock_temperature);
    }
    
    public GasSensorStatus create(final double humidity, final double resistance, final double temperature) {
        if (humidity < 0.0) {
            throw new InvalidParameterException("humidity is null or empty");
        }
        if (temperature < 0.0) {
            throw new InvalidParameterException("temperature is null or empty");
        }
        ++this.idCounter;
        this.remove(0);
        this.put(0, new GasSensorStatus(this.idCounter, humidity, resistance, temperature));
        return (GasSensorStatus)this.get(0);
    }
    
    public List<GasSensorStatus> getAll() {
        return List.copyOf((Collection<? extends GasSensorStatus>)this.values());
    }
    
    public GasSensorStatus getById(final int id) {
        if (this.containsKey(id)) {
            return (GasSensorStatus)this.get(id);
        }
        throw new InvalidParameterException("id '" + id + "' not exists");
    }
    
    public GasSensorStatus updateById(final int id, final double humidity, final double resistance, final double temperature) {
        if (this.containsKey(id)) {
            final GasSensorStatus gas = (GasSensorStatus)this.get(id);
            if (humidity > 0.0) {
                gas.setHumidity(humidity);
            }
            if (temperature > 0.0) {
                gas.setTemperature(temperature);
            }
            gas.setResistance(resistance);
            this.put(id, gas);
            return gas;
        }
        throw new InvalidParameterException("id '" + id + "' not exists");
    }
    
    public void removeById(final int id) {
        if (this.containsKey(id)) {
            this.remove(id);
        }
    }
}
