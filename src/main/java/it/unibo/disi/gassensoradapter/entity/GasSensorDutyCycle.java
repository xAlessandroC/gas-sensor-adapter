package it.unibo.disi.gassensoradapter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GasSensorDutyCycle {

    private int duty_cycle;

    public GasSensorDutyCycle(){
        
    }

    public GasSensorDutyCycle(int duty_cycle){
        this.duty_cycle = duty_cycle;
    }

    @JsonProperty("duty_cycle")
    public int getDutyCycle(){
        return this.duty_cycle;
    }

    @JsonProperty("duty_cycle")
    public void setDutyCycle(int duty_cycle){
        this.duty_cycle = duty_cycle;
    }
}
