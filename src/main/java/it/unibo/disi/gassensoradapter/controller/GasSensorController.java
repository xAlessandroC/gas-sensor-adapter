package it.unibo.disi.gassensoradapter.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.arrowhead.common.exception.BadPayloadException;
import it.unibo.disi.gassensoradapter.database.DTOConverter;
import it.unibo.disi.gassensoradapter.database.GasSensorStatusResponseDTO;
import it.unibo.disi.gassensoradapter.database.GasSensorStatusRequestDTO;
import it.unibo.disi.gassensoradapter.database.InMemoryGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.database.MockGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;

@CrossOrigin
@RestController
@RequestMapping({ "/gas-sensor-status" })
public class GasSensorController {

    private static Logger logger = LoggerFactory.getLogger(GasSensorController.class);

    @Autowired
    // private InMemoryGasSensorStatusDB gasSensorStatusDB;
    private MockGasSensorStatusDB gasSensorStatusDB;


	@GetMapping(produces = { "application/json" })
    @ResponseBody
	public List<GasSensorStatusResponseDTO> getAllGasSensorStatus() {
        final List<GasSensorStatusResponseDTO> response = new ArrayList<GasSensorStatusResponseDTO>();
        try{
            this.gasSensorStatusDB.readDataFromMQTT();
            for (final GasSensorStatus gas : this.gasSensorStatusDB.getAll()) {
                response.add(DTOConverter.convertGasSensorStatusToGasSensorStatusResponseDTO(gas));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return response;
    }

    @GetMapping(path = { "/{id}" }, produces = { "application/json" })
    @ResponseBody
    public GasSensorStatusResponseDTO getCarById(@PathVariable("id") final int id) {
        return DTOConverter.convertGasSensorStatusToGasSensorStatusResponseDTO(this.gasSensorStatusDB.getById(id));
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public GasSensorStatusResponseDTO createGasSensorStatus(@RequestBody final GasSensorStatusRequestDTO dto){
        if (dto == null){
            throw new BadPayloadException("Gas Sensor Status is null");
        }
        if (dto.getHumidity() < 0.0){
            throw new BadPayloadException("Humidity is negative");
        }
        if (dto.getTemperature() < 0.0){
            throw new BadPayloadException("Temperature is negative");
        }
        if (dto.getVoltage() < 0.0){
            throw new BadPayloadException("Voltage is negative");
        }
        if (dto.getIrradiance() < 0.0){
            throw new BadPayloadException("Irradiance is negative");
        }

        final GasSensorStatus gas = this.gasSensorStatusDB.create(dto.getHumidity(), dto.getResistance(), dto.getTemperature(), dto.getVoltage(), dto.getIrradiance(), dto.getDutyCycle());
        return DTOConverter.convertGasSensorStatusToGasSensorStatusResponseDTO(gas);
    }

    @PutMapping(path = { "/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
    @ResponseBody
    public GasSensorStatusResponseDTO updateGasSensorStatus(@PathVariable(name = "id") final int id, @RequestBody final GasSensorStatusRequestDTO dto) {
        if (dto == null) {
            throw new BadPayloadException("Gas Sensor Status is null");
        }
        if (dto.getHumidity() < 0.0) {
            throw new BadPayloadException("Humidity is negative");
        }
        if (dto.getTemperature() < 0.0) {
            throw new BadPayloadException("Temperature is negative");
        }
        if (dto.getVoltage() < 0.0) {
            throw new BadPayloadException("Voltage is negative");
        }
        if (dto.getIrradiance() < 0.0) {
            throw new BadPayloadException("Irradiance is negative");
        }
        final GasSensorStatus gas = this.gasSensorStatusDB.updateById(id, dto.getHumidity(), dto.getResistance(), dto.getTemperature(), dto.getVoltage(), dto.getIrradiance());
        return DTOConverter.convertGasSensorStatusToGasSensorStatusResponseDTO(gas);
    }
    
    @DeleteMapping(path = { "/{id}" })
    public void removeGasSensorStatusById(@PathVariable("id") final int id) {
        this.gasSensorStatusDB.removeById(id);
    }
}