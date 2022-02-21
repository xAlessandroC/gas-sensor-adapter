package it.unibo.disi.gassensoradapter.entity;

public class GasSensorStatus
{
    private final int id;
    private double humidity;
    private double resistance;
    private double temperature;
    
    public GasSensorStatus(final int id, final double humidity, final double resistance, final double temperature) {
        this.id = id;
        this.humidity = humidity;
        this.resistance = resistance;
        this.temperature = temperature;
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
}
