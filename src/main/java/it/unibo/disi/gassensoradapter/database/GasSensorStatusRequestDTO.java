package it.unibo.disi.gassensoradapter.database;

public class GasSensorStatusRequestDTO {

    private int id;
    private double humidity;
    private double resistance;
    private double temperature;

    public GasSensorStatusRequestDTO(int id, double humidity, double resistance, double temperature) {
        this.id = id;
        this.humidity = humidity;
        this.resistance = resistance;
        this.temperature = temperature;
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

    public void setId(int id){
        this.id = id;
    }

    public void setHumidity(double humidity){
        this.humidity = humidity;
    }

    public void setResistance(double resistance){
        this.humidity = resistance;
    }

    public void setTemperature(double temperature){
        this.humidity = temperature;
    }

}
