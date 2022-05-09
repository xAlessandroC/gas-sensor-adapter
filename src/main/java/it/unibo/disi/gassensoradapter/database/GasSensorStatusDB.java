package it.unibo.disi.gassensoradapter.database;

import java.util.List;

import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;

public interface GasSensorStatusDB
{
    
    public GasSensorStatus readDataFromMQTT();
    
    public GasSensorStatus create(final double humidity, final double resistance, final double temperature, final double voltage, final double irradiance, final int dutycycle, final String timestamp);
    
    public List<GasSensorStatus> getAll();
    
    public GasSensorStatus getById(final int id);
    
    public GasSensorStatus updateById(final int id, final double humidity, final double resistance, final double temperature, final double voltage, final double irradiance);
    
    public void removeById(final int id);
}
