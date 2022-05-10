package it.unibo.disi.gassensoradapter.database;

import java.util.Collection;
import java.util.List;
import eu.arrowhead.common.exception.InvalidParameterException;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatusMQTTReadData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import it.unibo.disi.gassensoradapter.database.MQTTConsumer.GasSensorStatusMockConsumer;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component("real-db")
@Lazy
public class InMemoryGasSensorStatusDB extends ConcurrentHashMap<Integer, GasSensorStatus> implements GasSensorStatusDB
{
    private static Logger logger = LoggerFactory.getLogger(InMemoryGasSensorStatusDB.class);
    private static final long serialVersionUID = -2462387539362748691L;
    private int idCounter;

    @Autowired()
    // @Qualifier("cons-ttn")
    @Resource(name="${consumer.type}")
    private GasSensorStatusMockConsumer consumer;
    
    public InMemoryGasSensorStatusDB() {
        logger.info("Starting InMemoryGasSensorStatusDB...");
        this.idCounter = 0;
    }

    @PostConstruct
    public void initAutowiredObjects(){
        this.consumer.ClientInit();
    }
    
    public GasSensorStatus readDataFromMQTT() {
        final GasSensorStatusMQTTReadData readData = this.consumer.getData();
        logger.info("Read data: " + readData);
        if (readData != null && readData.getHumidity() >= 0.0 && readData.getIrradiance() >= 0.0) {
            return this.create(readData.getHumidity(), readData.getResistance(), readData.getTemperature(), readData.getVoltage(), readData.getIrradiance(), readData.getDutyCycle(), readData.getTimestamp());
        }
        return new GasSensorStatus(-1, -1.0, -1.0, -1.0, -1.0, -1.0, -1, "");
    }
    
    public GasSensorStatus create(final double humidity, final double resistance, final double temperature, final double voltage, final double irradiance, final int dutycycle, final String timestamp) {
        if (humidity < 0.0) {
            throw new InvalidParameterException("humidity is negative");
        }
        if (temperature < 0.0) {
            throw new InvalidParameterException("temperature is negative");
        }
        if (voltage < 0.0) {
            throw new InvalidParameterException("voltage is negative");
        }
        if (irradiance < 0.0) {
            throw new InvalidParameterException("irradiance is negative");
        }
        if (dutycycle < 0.0) {
            throw new InvalidParameterException("dutycycle is negative");
        }
        ++this.idCounter;
        this.remove(0);
        this.put(0, new GasSensorStatus(this.idCounter, humidity, resistance, temperature, voltage, irradiance, dutycycle, timestamp));
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
    
    public GasSensorStatus updateById(final int id, final double humidity, final double resistance, final double temperature, final double voltage, final double irradiance) {
        if (this.containsKey(id)) {
            final GasSensorStatus gas = (GasSensorStatus)this.get(id);
            if (humidity > 0.0) {
                gas.setHumidity(humidity);
            }
            if (temperature > 0.0) {
                gas.setTemperature(temperature);
            }
            if (voltage > 0.0) {
                gas.setVoltage(voltage);
            }
            if (irradiance >= 0.0) {
                gas.setIrradiance(irradiance);
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
