package it.unibo.disi.gassensoradapter.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"devId","harvId","lowpwrI","activeI","duty","Vload","devAvgI","batSOC","batV","phIrr","thThot","thTcold","thGrad","vibAcc","vibFreq"})
public class GasSensorHarvesterInfo {
    
    private String devId;
    private String harvId;

    private Float lowpwrI;
    private Float activeI;
    private Integer duty;
    private Float Vload;
    private Float devAvgI;

    private Integer batSOC;
    private Float batV;

    private Float phIrr;
    private Integer thThot;
    private Integer thTcold;
    private Integer thGrad;
    private Integer vibAcc;
    private Integer vibFreq;

    public GasSensorHarvesterInfo(){}

    public GasSensorHarvesterInfo(String devId, String harvId, Float lowpwrI, Float activeI, Integer duty, Float Vload, Float devAvgI, Integer batSOC, Float batV, Float phIrr, Integer thThot, Integer thTcold, Integer thGrad, Integer vibAcc, Integer vibFreq) {
        this.devId = devId;
        this.harvId = harvId;
        this.lowpwrI = lowpwrI;
        this.activeI = activeI;
        this.duty = duty;
        this.Vload = Vload;
        this.devAvgI = devAvgI;
        this.batSOC = batSOC;
        BigDecimal bd = new BigDecimal(batV).setScale(2, RoundingMode.FLOOR);
        this.batV = bd.floatValue();
        this.phIrr = phIrr;
        this.thThot = thThot;
        this.thTcold = thTcold;
        this.thGrad = thGrad;
        this.vibAcc = vibAcc;
        this.vibFreq = vibFreq;
    }


    public String getDevId() {
        return this.devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getHarvId() {
        return this.harvId;
    }

    public void setHarvId(String harvId) {
        this.harvId = harvId;
    }

    @JsonProperty("Vload")
    public Float getVload() {
        return this.Vload;
    }

    public void setVload(Float Vload) {
        this.Vload = Vload;
    }

    public Float getActiveI() {
        return this.activeI;
    }

    public void setActiveI(Float activeI) {
        this.activeI = activeI;
    }

    public Integer getDuty() {
        return this.duty;
    }

    public void setDuty(Integer duty) {
        this.duty = duty;
    }

    public Float getLowpwrI() {
        return this.lowpwrI;
    }

    public void setLowpwrI(Float lowpwrI) {
        this.lowpwrI = lowpwrI;
    }

    public Float getBatV() {
        return this.batV;
    }

    public void setBatV(Float batV) {
        BigDecimal bd = new BigDecimal(batV).setScale(2, RoundingMode.FLOOR);
        this.batV = bd.floatValue();
    }

    public Integer getBatSOC() {
        return this.batSOC;
    }

    public void setBatSOC(Integer batSOC) {
        this.batSOC = batSOC;
    }

    public Float getDevAvgI() {
        return this.devAvgI;
    }

    public void setDevAvgI(Float devAvgI) {
        this.devAvgI = devAvgI;
    }

    public Float getPhIrr() {
        return this.phIrr;
    }

    public void setPhIrr(Float phIrr) {
        this.phIrr = phIrr;
    }

    public Integer getThThot() {
        return this.thThot;
    }

    public void setThThot(Integer thThot) {
        this.thThot = thThot;
    }

    public Integer getThTcold() {
        return this.thTcold;
    }

    public void setThTcold(Integer thTcold) {
        this.thTcold = thTcold;
    }

    public Integer getThGrad() {
        return this.thGrad;
    }

    public void setThGrad(Integer thGrad) {
        this.thGrad = thGrad;
    }

    public Integer getVibAcc() {
        return this.vibAcc;
    }

    public void setVibAcc(Integer vibAcc) {
        this.vibAcc = vibAcc;
    }

    public Integer getVibFreq() {
        return this.vibFreq;
    }

    public void setVibFreq(Integer vibFreq) {
        this.vibFreq = vibFreq;
    }

}
