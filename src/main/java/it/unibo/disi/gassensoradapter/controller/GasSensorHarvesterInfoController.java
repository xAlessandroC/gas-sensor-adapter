package it.unibo.disi.gassensoradapter.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.unibo.disi.gassensoradapter.database.GasSensorStatusDB;
import it.unibo.disi.gassensoradapter.database.InMemoryGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.entity.GasSensorHarvesterInfo;
import it.unibo.disi.gassensoradapter.entity.GasSensorStatus;
import it.unibo.disi.gassensoradapter.observerPattern.Observer;

@CrossOrigin
@RestController
@RequestMapping({ "/gas-sensor-harvester-info" })
public class GasSensorHarvesterInfoController implements Observer{

    private static Logger logger = LoggerFactory.getLogger(GasSensorHarvesterInfoController.class);
    private int generalDutyCycle = -1;

    @Autowired
    // @Qualifier("mock-db")
    @Resource(name="${db.type}")
    private GasSensorStatusDB gasSensorStatusDB;

    @PostConstruct
    public void subscribeToDutyUpdate(){
        logger.info("[GAS SENSOR ADAPTER]: Subscription to duty update");
        GasSensorDutyCycleController.addObservable(this);
    }

    @GetMapping(produces = { "application/json" })
    @ResponseBody
	public GasSensorHarvesterInfo getHarvesterInfo() {
        logger.info("[GAS SENSOR ADAPTER]: Received harvester info request");
        
        GasSensorStatus status = this.gasSensorStatusDB.readDataFromMQTT();

        if(generalDutyCycle == -1)
            generalDutyCycle = status.getDutyCycle();

        return new GasSensorHarvesterInfo("GasSensor", "SolarLightLoad", 3.0f, 10.0f, generalDutyCycle, 3.3f, null, null, (float)(status.getVoltage()/1000), 700.0f, null, null, null, null, null);
    }

    @Override
    public void update(String message) {
        try{
            this.generalDutyCycle = Integer.parseInt(message);
        }catch(Exception e){
            logger.info("[GAS SENSOR ADAPTER]: Error during duty cycle update");
        }
    }

}
