package it.unibo.disi.gassensoradapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.unibo.disi.gassensoradapter.entity.GasSensorHarvesterInfo;

@RestController
@RequestMapping({ "/gas-sensor-harvester-info" })
public class GasSensorHarvesterInfoController {

    private static Logger logger = LoggerFactory.getLogger(GasSensorHarvesterInfoController.class);

    @GetMapping(produces = { "application/json" })
    @ResponseBody
	public GasSensorHarvesterInfo getHarvesterInfo() {
        logger.info("[GAS SENSOR ADAPTER]: Received harvester info request");  

        return new GasSensorHarvesterInfo("GasSensor", "SolarLightLoad", 1.0f, 20.0f, 20, 3.3f, null, null, 3.82f, 700.0f, null, null, null, null, null);
    }

}
