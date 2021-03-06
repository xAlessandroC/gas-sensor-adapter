package it.unibo.disi.gassensoradapter.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.arrowhead.common.exception.BadPayloadException;
import it.unibo.disi.gassensoradapter.database.InMemoryGasSensorStatusDB;
import it.unibo.disi.gassensoradapter.database.MQTTProducer.GasSensorMQTTProducer;
import it.unibo.disi.gassensoradapter.entity.GasSensorDutyCycle;
import it.unibo.disi.gassensoradapter.observerPattern.Observer;

@CrossOrigin
@RestController
@RequestMapping({ "/gas-sensor-dutycycle" })
public class GasSensorDutyCycleController {

    private static Logger logger = LoggerFactory.getLogger(GasSensorDutyCycleController.class);

    @Autowired
    // @Qualifier("prod-mock")
    @Resource(name="${producer.type}")
    private GasSensorMQTTProducer mqttProducer;

    @PostConstruct
    private void initAutowiredObjects(){
        this.mqttProducer.ClientInit();
    }
    
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public GasSensorDutyCycle createGasSensorStatus(@RequestBody final GasSensorDutyCycle duty_cycle){
        
        if(duty_cycle.getDutyCycle() < 10 || duty_cycle.getDutyCycle() > 40){
            throw new BadPayloadException("Duty cycle must be inside [10 - 40] range!");
        }

        try{
            int sleeptime = (int) (1000/duty_cycle.getDutyCycle());
            logger.info("Computed sleeptime is " + sleeptime);

            byte[] arr = ByteBuffer.allocate(4).putInt(sleeptime).array();
            String base64encoded = new String(Base64.getEncoder().encode(new byte[]{arr[arr.length - 1]}));
            // String arces_payload = "{\"reference\": \"reset\", \"confirmed\": false, \"fPort\": 50, \"data\": \"" + base64encoded + "\" }";
            String ttn_payload = "{\"downlinks\": [{\"f_port\": 50,\"frm_payload\": \"" + base64encoded + "\",\"priority\": \"NORMAL\"}]}";
            this.mqttProducer.publishMessage(ttn_payload);

        }
        catch (Exception e) {
            logger.info("Error during MQTT publishing!");
            e.printStackTrace();
            return new GasSensorDutyCycle(-1);
        }

        this.update(duty_cycle.getDutyCycle());

        return duty_cycle;
    }


    // Observer pattern
    private static List<Observer> observers = new ArrayList<>();

    public static void addObservable(Observer observer){
        observers.add(observer);
    }

    private void update(int new_duty){

        for (Observer observer : observers) {
            observer.update("" + new_duty);    
        }
    }

}
