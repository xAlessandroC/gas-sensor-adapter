package it.unibo.disi.gassensoradapter.database;

public class GasSensorStatusRequestDTO {

    private int id;
    private double humidity;
    private double resistance;
    private double temperature;
    private double voltage;
    private double irradiance;
    private int dutycycle;
    private String timestamp;

    public GasSensorStatusRequestDTO(int id, double humidity, double resistance, double temperature, double voltage, double irradiance, int dutycycle, String timestamp) {
        this.id = id;
        this.humidity = humidity;
        this.resistance = resistance;
        this.temperature = temperature;
        this.voltage = voltage;
        this.irradiance = irradiance;
        this.dutycycle = dutycycle;
        this.timestamp = timestamp;
    }

    public int getId(){
        return this.id;
    }

    public double getHumidity(){
        return this.humidity;
    }

    public double getResistance(){
        return this.resistance;
    }

    public double getTemperature(){
        return this.temperature;
    }

    public double getVoltage(){
        return this.voltage;
    }

    public double getIrradiance(){
        return this.irradiance;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setHumidity(double humidity){
        this.humidity = humidity;
    }

    public void setResistance(double resistance){
        this.resistance = resistance;
    }

    public void setTemperature(double temperature){
        this.temperature = temperature;
    }

    public void setVoltage(double voltage){
        this.voltage = voltage;
    }

    public void setIrradiance(double irradiance){
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
