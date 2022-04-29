package it.unibo.disi.gassensoradapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.unibo.disi.gassensoradapter.database.InMemoryGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.database.MockGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.entity.GasSensorHarvesterInfo;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;

@CrossOrigin
@RestController
@RequestMapping({ "/gas-sensor-harvester-info" })
public class GasSensorHarvesterInfoController {

    private static Logger logger = LoggerFactory.getLogger(GasSensorHarvesterInfoController.class);

    @Autowired
    private InMemoryGasSensorStatusDB gasSensorStatusDB;
    // private MockGasSensorStatusDB gasSensorStatusDB;

    @GetMapping(produces = { "application/json" })
    @ResponseBody
	public GasSensorHarvesterInfo getHarvesterInfo() {
        logger.info("[GAS SENSOR ADAPTER]: Received harvester info request");
        
        GasSensorStatus status = this.gasSensorStatusDB.readDataFromMQTT();

        if(InMemoryGasSensorStatusDB.generalDutyCycle == -1)
            InMemoryGasSensorStatusDB.generalDutyCycle = status.getDutyCycle();

        return new GasSensorHarvesterInfo("GasSensor", "SolarLightLoad", 3.0f, 10.0f, InMemoryGasSensorStatusDB.generalDutyCycle, 3.3f, null, null, (float)(status.getVoltage()/1000), 700.0f, null, null, null, null, null);
    }

}
