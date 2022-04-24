# Gas Sensor Adapter
The gas sensor adapter enables the registration of the gas sensor node functionalities in the Arrowhead Framework. This is done by mean of a Java Spring applicatiton which exposes a REST API.

## Content
* [Requirements](#requirements)
* [Application setup](#application-set-up)
* [Build and start](#build-and-start-the-adapter)
* [API](#apis)
    * [Get Gas Sensor Status](#get-gas-sensor-status)
    * [Set Dutycycle](#set-duty-cycle)
    * [Get Harvester info](#get-harvester-info)
---

## Requirements
* Java 11
* Maven 3.8.4

## Application set up
All the configuration for the java application are contained inside the [application.properties](src/main/resources/application.properties)

* **server.address** - IP Address where the service runs
* **server.port** - Port of the service
* **sr_address** - IP Address of the AF service registry instance
* **sr_port** - Port of the AF service registry

## Build and start the adapter
Build the project with maven

```java
mvn clean install
```

and run the jar

```java
java -jar .\target\gas-sensor-adapter-0.0.1-SNAPSHOT.jar
```

## APIs
### Get Gas Sensor Status
| Function | URL subpath | Method | Input | Output |
| -------- | ----------- | ------ | ----- | ------ |
| Get status     | /gas-sensor-status       | GET    | - | [GasSensorStatus](#gassensor-status)     |

#### GasSensor Status
```json
{
 "id": 0,
 "humidity": 0.0,
 "resistance": 0.0,
 "temperature": 0.0,
 "voltage": 0.0,
 "irradiance": 0.0
}
```

| Field | Description | Unit |
| ----- | ----------- | --------- |
| `id` | Id of data  | - | 
| `humidity` | Value measured for humidity | *%* |
| `resistance` | Value measured for resistance | *Ohm* |
| `temperature` | Value measured for temperature | *°C* | 
| `voltage` | Voltage of the sensor | *mv* |
| `irradiance` | Value measured for irradiance | *W/m<sup>2</sup>* |

</br>

### Set duty cycle
| Function | URL subpath | Method | Input | Output |
| -------- | ----------- | ------ | ----- | ------ |
|   Set duty cycle   | /gas-sensor-dutycycle       | POST    | [DutyCycle](#dutycycle) | [DutyCycle]()     |

#### DutyCycle
```json
{
 "duty_cycle": 0
}
```

| Field | Description | Unit |
| ----- | ----------- | --------- |
| `duty_cycle` | Value of duty cycle to set in [10 - 33] range  | *%* | 

### Get harvester info
| Function | URL subpath | Method | Input | Output |
| -------- | ----------- | ------ | ----- | ------ |
|   Get harvester info   | /gas-sensor-harvester-info       | GET    | - | [SensorHarevsterInfo](#get-harvester-info)     |

#### Gas Harvester Info
```json
{
 "devId": "string",
 "harvId": "string",

 "lowpwrI": 0.0,
 "activeI": 0.0,
 "duty": 0,
 "Vload": 0.0,
 "devAvgI": 0.0,
 "batSOC": 0.0,
 "batV": 0.0,

 "phIrr": 0.0,
 "thThot": 0,
 "thTcold": 0,
 "thGrad": 0,
 "vibAcc": 0,
 "vibFreq": 0
}
```
| Field | Description | Unit |
| ----- | ----------- | --------- |
| `devId` | Name of the device  | - |
| `harvId` | Name od the energy harvesting model one of "SolarHeavyLoad", "SolarLightLoad", "TEG", "Piezo" | - |
| `lowpwrI` | Device current consumption in low power (idle) state | *mA* |
| `activeI` | Device current consumption in active state | *mA* | yes - Alternative to devAvgI |
| `duty` | Duty cycle expressed as percentage | *%* |
| `devAvgI` | Average current consumption of the device | *mA* | 
| `Vload` | Nominal power supply voltage of the load | *V* | 
| `batSOC` | Actual state of charge | *%* |
| `batV` | Actual battery voltage | *V* |
| `phIrr` | Irradiance | *W/m<sup>2</sup>* | 
| `thThot` | Hot side TEG's temperature | *°C* | 
| `thTcold` | Cold side TEG's temperature | *°C* | 
| `thGrad` | Thermal gradient | *°C* | 
| `vibAcc` | Amount of acceleration vibrations | *mG* |
| `vibFreq` | Fundamental frequency | *Hz* | 

</br>
