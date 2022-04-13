package it.unibo.disi.gassensoradapter.database;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;

public class DTOConverter
{
    public static GasSensorStatusResponseDTO convertGasSensorStatusToGasSensorStatusResponseDTO(final GasSensorStatus gas) {
        Assert.notNull((Object)gas, "gas sensor status is null");
        return new GasSensorStatusResponseDTO(gas.getId(), gas.getHumidity(), gas.getResistance(), gas.getTemperature(), gas.getVoltage(), gas.getIrradiance());
    }
    
    public static List<GasSensorStatusResponseDTO> convertGasSensorStatusListToGasSensorStatusResponseDTOList(final List<GasSensorStatus> sensors) {
        Assert.notNull((Object)sensors, "gas sensor list is null");
        final List<GasSensorStatusResponseDTO> carResponse = new ArrayList<GasSensorStatusResponseDTO>(sensors.size());
        for (final GasSensorStatus gas : sensors) {
            carResponse.add(convertGasSensorStatusToGasSensorStatusResponseDTO(gas));
        }
        return carResponse;
    }
    
    public DTOConverter() {
        throw new UnsupportedOperationException();
    }
}
