package it.unibo.disi.gassensoradapter.database.MQTTConsumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.disi.gassensoradapter.entity.GasSensorStatusMQTTReadData;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;

public abstract class GasSensorStatusMQTTConsumer implements MqttCallback{
    protected Logger logger = LoggerFactory.getLogger(GasSensorStatusMQTTConsumer.class);

    protected String topic;
    protected String port;
    protected String address;
    protected String username;
    protected String password;

    protected MqttClient client;
    protected final String clientId;
    protected GasSensorStatusMQTTReadData data;
    
    protected Gson gson;
    
    public GasSensorStatusMQTTConsumer(final String topic, final String address, final String port, final String username, final String password) {
        this.address = address;
        this.topic = topic;
        this.port = port;
        this.username = username;
        this.password = password;
        this.data = null;
        this.gson = new Gson();
        this.clientId = MqttClient.generateClientId();
    }
    
    public void ClientInit() {
        if (this.client != null) {
            return;
        }
        try {
            logger.info("Trying to connect with ID " + this.clientId);
            (this.client = new MqttClient("ssl://" + this.address + ":" + this.port, this.clientId, (MqttClientPersistence)new MemoryPersistence())).setCallback((MqttCallback)this);
            final MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setMqttVersion(3);
            options.setUserName(this.username);
            options.setPassword(this.password.toCharArray());
            options.setAutomaticReconnect(true);
            this.client.connect(options);
            this.client.subscribe(this.topic, 1);
            
            logger.info("MQTT client connected succesfully!");
            logger.info("INFO Client [" + this.client.isConnected() + ", " + this.client.getTopic(topic) + ", " + this.client.getServerURI() + "]");
        }
        catch (MqttException e) {
            logger.info("Error during MQTT client connection!");
            e.printStackTrace();
        }
    }
    
    public GasSensorStatusMQTTReadData getData() {
        return this.data;
    }
    
    public void Destroy() {
        try {
            this.client.unsubscribe(this.topic);
            this.client.disconnect();
            this.client = null;
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    public void connectionLost(final Throwable cause) {
        logger.info("Connection to MQTT Broker lost " + cause.getMessage());
        try {
            this.client.unsubscribe(this.topic);
            this.client.disconnect();
            this.client = null;
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    public void messageArrived(final String topic, final MqttMessage message) {
        try{
            final String textMessage = new String(message.getPayload());
            logger.info("Message from topic " + topic + ": " + textMessage);
            final String parsedPayload = this.parsePayload(textMessage);
            logger.info("Payload:" + parsedPayload);
            final GasSensorStatusMQTTReadData readData = (GasSensorStatusMQTTReadData)this.gson.fromJson(parsedPayload, GasSensorStatusMQTTReadData.class);
            this.setData(readData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deliveryComplete(final IMqttDeliveryToken token) {
    }
    
    protected void setData(final GasSensorStatusMQTTReadData data) {
        this.data = data;
    }

    abstract String parsePayload(final String fullMessage);
}
