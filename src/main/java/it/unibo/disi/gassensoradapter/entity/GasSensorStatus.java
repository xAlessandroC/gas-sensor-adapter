package it.unibo.disi.gassensoradapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GasSensorStatus
{
    private final int id;
    private double humidity;
    private double resistance;
    private double temperature;
    private double voltage;
    private double irradiance;
    private int dutycycle;

    private String timestamp;
    
    public GasSensorStatus(final int id, final double humidity, final double resistance, final double temperature, final double voltage, final double irradiance, final int dutycycle, final String timestamp) {
        this.id = id;
        this.humidity = humidity;
        this.resistance = resistance;
        this.temperature = temperature;
        this.voltage = voltage;
        this.irradiance = irradiance;
        this.dutycycle = dutycycle;
        this.timestamp = timestamp;
    }
    
    public int getId() {
        return this.id;
    }
    
    public double getHumidity() {
        return this.humidity;
    }
    
    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }
    
    public double getResistance() {
        return this.resistance;
    }
    
    public void setResistance(final double resistance) {
        this.resistance = resistance;
    }
    
    public double getTemperature() {
        return this.temperature;
    }
    
    public void setTemperature(final double temperature) {
        this.temperature = temperature;
    }

    public double getVoltage() {
        return this.voltage;
    }
    
    public void setVoltage(final double voltage) {
        this.voltage = voltage;
    }

    public double getIrradiance() {
        return this.irradiance;
    }
    
    public void setIrradiance(final double irradiance) {
        this.irradiance = irradiance;
    }

    public int getDutyCycle() {
        return this.dutycycle;
    }
    
    public void setDutyCycle(final int dutycycle) {
        this.dutycycle = dutycycle;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }
}
