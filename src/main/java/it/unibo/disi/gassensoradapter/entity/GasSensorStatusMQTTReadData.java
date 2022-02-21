package it.unibo.disi.gassensoradapter.entity;

public class GasSensorStatusMQTTReadData
{
    private double humidity;
    private double resistance;
    private double temperature;
    
    public GasSensorStatusMQTTReadData() {
        this.humidity = -1.0;
        this.resistance = -1.0;
        this.temperature = -1.0;
    }
    
    public GasSensorStatusMQTTReadData(final double humidity, final double resistance, final double temperature) {
        this.humidity = humidity;
        this.resistance = resistance;
        this.temperature = temperature;
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

    @Override
    public String toString(){
        return "[" + this.getHumidity() + ", " + this.getResistance() + ", " + this.getTemperature() + "]";
    }
}
