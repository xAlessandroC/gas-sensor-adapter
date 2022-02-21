package it.unibo.disi.gassensoradapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import eu.arrowhead.common.CommonConstants;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, "eu.arrowhead.common", "it.unibo"})
public class GasSensorAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasSensorAdapterApplication.class, args);
	}

}
