package it.unibo.disi.gassensoradapter.database.MQTTConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import it.unibo.disi.gassensoradapter.entity.GasSensorStatusMQTTReadData;

public abstract class GasSensorStatusConsumer{

    @Value("${influx.address}")
    protected String influx_address;

    @Value("${influx.port}")
    protected int influx_port;

    @Value("${influx.dbname}")
    protected String influx_db_name;

    @Value("${influx.schema}")
    protected String schema;

    protected Logger logger = LoggerFactory.getLogger(GasSensorStatusConsumer.class);
    protected GasSensorStatusMQTTReadData data;

    public abstract void ClientInit();

    public GasSensorStatusMQTTReadData getData(){
        return this.data;
    }
    
    protected void setData(GasSensorStatusMQTTReadData data){
        this.data = data;
    }

    protected void writeOnInflux(){
        try {
            GasSensorStatusMQTTReadData readData = this.getData();
            Instant timestamp_istant = Instant.parse(readData.getTimestamp());
            long nanos = timestamp_istant.getEpochSecond() * 1000000000L + timestamp_istant.getNano();

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(this.schema + "://" + this.influx_address + ":" + this.influx_port +"/write?db=" + this.influx_db_name);
			String humidity_entry = "gas_sensor" + ",version=0.1 humidity=" + readData.getHumidity() + " " + nanos;
			String temperature_entry = "gas_sensor" + ",version=0.1 temperature=" + readData.getTemperature() + " " + nanos;
			String resistance_entry = "gas_sensor" + ",version=0.1 resistance=" + readData.getResistance() + " " + nanos;
			String voltage_entry = "gas_sensor" + ",version=0.1 voltage=" + readData.getVoltage() + " " + nanos;
			String irradiance_entry = "gas_sensor" + ",version=0.1 irradiance=" + readData.getIrradiance() + " " + nanos;
			httppost.setEntity(new StringEntity(String.join("\n", humidity_entry, temperature_entry, resistance_entry, voltage_entry, irradiance_entry)));
			HttpResponse httpresponse = httpclient.execute(httppost);
			
			if(httpresponse.getStatusLine().getStatusCode() == 204) {
				logger.info("Bundle : Data written on influx!");
			}else {
				logger.info("Bundle : Something went wrong!");
			}
			
		} catch (Exception e) {
			logger.info("Bundle : Error during writing on influx");
			e.printStackTrace();
		}
    }
    
    abstract String parsePayload(String fullMessage);
}
