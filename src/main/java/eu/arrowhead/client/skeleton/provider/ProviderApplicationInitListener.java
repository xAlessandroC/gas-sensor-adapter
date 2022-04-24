package eu.arrowhead.client.skeleton.provider;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import eu.arrowhead.common.dto.shared.ServiceSecurityType;
import java.util.Base64;
import eu.arrowhead.common.dto.shared.SystemRequestDTO;
import java.security.PrivateKey;
import java.security.PublicKey;

import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.Utilities;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStoreException;
import java.security.KeyStore;
import eu.arrowhead.common.exception.ArrowheadException;
import eu.arrowhead.common.http.HttpService;
import eu.arrowhead.common.dto.shared.ServiceRegistryRequestDTO;
import org.springframework.http.HttpMethod;
import eu.arrowhead.common.core.CoreSystem;

import org.springframework.context.event.ContextRefreshedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import eu.arrowhead.client.skeleton.provider.security.ProviderSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import eu.arrowhead.client.library.ArrowheadService;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;

import eu.arrowhead.client.library.config.ApplicationInitListener;

@Component
public class ProviderApplicationInitListener extends ApplicationInitListener
{
    @Autowired
    private ArrowheadService arrowheadService;
    @Autowired
    private ProviderSecurityConfig providerSecurityConfig;
    @Value("${token.security.filter.enabled:true}")
    private boolean tokenSecurityFilterEnabled;
    @Value("${server.ssl.enabled:true}")
    private boolean sslEnabled;
    @Value("${client_system_name}")
    private String mySystemName;
    @Value("${server.address: localhost}")
    private String mySystemAddress;
    @Value("${server.port: 8080}")
    private int mySystemPort;
    private final Logger logger;

    @Autowired
	private HttpService httpService;
    
    public ProviderApplicationInitListener() {
        this.logger = LogManager.getLogger(ProviderApplicationInitListener.class);
    }
    
    protected void customInit(final ContextRefreshedEvent event) {
        this.checkCoreSystemReachability(CoreSystem.SERVICE_REGISTRY);
        if (this.sslEnabled && this.tokenSecurityFilterEnabled) {
            this.checkCoreSystemReachability(CoreSystem.AUTHORIZATION);
            this.arrowheadService.updateCoreServiceURIs(CoreSystem.AUTHORIZATION);
            this.setTokenSecurityFilter();
        }
        else {
            this.logger.info("TokenSecurityFilter in not active");
        }
        // final ServiceRegistryRequestDTO createGasSensorStatusServiceRequest = this.createServiceRegistryRequest("create-gas-sensor-status", "/gas-sensor-status", HttpMethod.POST);
        // this.arrowheadService.forceRegisterServiceToServiceRegistry(createGasSensorStatusServiceRequest);
        final ServiceRegistryRequestDTO getGasSensorStatusServiceRequest = this.createServiceRegistryRequest("get-gas-sensor-status", "/gas-sensor-status", HttpMethod.GET);
        getGasSensorStatusServiceRequest.getMetadata().put("request-param-humidity", "humidity");
        getGasSensorStatusServiceRequest.getMetadata().put("request-param-resistance", "resistance");
        this.arrowheadService.forceRegisterServiceToServiceRegistry(getGasSensorStatusServiceRequest);

        // Duty Cycle
        final ServiceRegistryRequestDTO createGasSensorDutyCycleServiceRequest = this.createServiceRegistryRequest("set-duty-cycle", "/gas-sensor-dutycycle", HttpMethod.POST);
        this.arrowheadService.forceRegisterServiceToServiceRegistry(createGasSensorDutyCycleServiceRequest);
    }
    
    public void customDestroy() {
        this.arrowheadService.unregisterServiceFromServiceRegistry("get-gas-sensor-status");
        this.arrowheadService.unregisterServiceFromServiceRegistry("set-duty-cycle");
        // this.unregisterServiceFromServiceRegistry("get-gas-sensor-status", "/gas-sensor-status");
        // this.unregisterServiceFromServiceRegistry("set-duty-cycle", "/gas-sensor-dutycycle");

    }
    
    private void setTokenSecurityFilter() {
        final PublicKey authorizationPublicKey = this.arrowheadService.queryAuthorizationPublicKey();
        if (authorizationPublicKey == null) {
            throw new ArrowheadException("Authorization public key is null");
        }
        KeyStore keystore;
        try {
            keystore = KeyStore.getInstance(this.sslProperties.getKeyStoreType());
            keystore.load(this.sslProperties.getKeyStore().getInputStream(), this.sslProperties.getKeyStorePassword().toCharArray());
        }
        catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex3) {
            // final Exception ex2;
            final Exception ex = ex3;
            throw new ArrowheadException(ex.getMessage());
        }
        final PrivateKey providerPrivateKey = Utilities.getPrivateKey(keystore, this.sslProperties.getKeyPassword());
        this.providerSecurityConfig.getTokenSecurityFilter().setAuthorizationPublicKey(authorizationPublicKey);
        this.providerSecurityConfig.getTokenSecurityFilter().setMyPrivateKey(providerPrivateKey);
    }
    
    private ServiceRegistryRequestDTO createServiceRegistryRequest(final String serviceDefinition, final String serviceUri, final HttpMethod httpMethod) {
        final ServiceRegistryRequestDTO serviceRegistryRequest = new ServiceRegistryRequestDTO();
        serviceRegistryRequest.setServiceDefinition(serviceDefinition);
        final SystemRequestDTO systemRequest = new SystemRequestDTO();
        systemRequest.setSystemName(this.mySystemName);
        systemRequest.setAddress(this.mySystemAddress);
        systemRequest.setPort(Integer.valueOf(this.mySystemPort));
        if (this.tokenSecurityFilterEnabled) {
            systemRequest.setAuthenticationInfo(Base64.getEncoder().encodeToString(this.arrowheadService.getMyPublicKey().getEncoded()));
            serviceRegistryRequest.setSecure(ServiceSecurityType.TOKEN.name());
            serviceRegistryRequest.setInterfaces((List<String>)List.of("HTTPS-SECURE-JSON"));
        }
        else if (this.sslEnabled) {
            systemRequest.setAuthenticationInfo(Base64.getEncoder().encodeToString(this.arrowheadService.getMyPublicKey().getEncoded()));
            serviceRegistryRequest.setSecure(ServiceSecurityType.CERTIFICATE.name());
            serviceRegistryRequest.setInterfaces((List<String>)List.of("HTTPS-SECURE-JSON"));
        }
        else {
            serviceRegistryRequest.setSecure(ServiceSecurityType.NOT_SECURE.name());
            serviceRegistryRequest.setInterfaces((List<String>)List.of("HTTP-INSECURE-JSON"));
        }
        serviceRegistryRequest.setProviderSystem(systemRequest);
        serviceRegistryRequest.setServiceUri(serviceUri);
        serviceRegistryRequest.setMetadata((Map<String,String>)new HashMap<String, String>());
        serviceRegistryRequest.getMetadata().put("http-method", httpMethod.name());
        return serviceRegistryRequest;
    }

    private void unregisterServiceFromServiceRegistry(final String serviceDefinition, final String serviceUri) {
		final String unregisterUriStr = CommonConstants.SERVICE_REGISTRY_URI + CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_URI;
		final MultiValueMap<String,String> queryMap = new LinkedMultiValueMap<>(4);
		queryMap.put(CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_REQUEST_PARAM_PROVIDER_SYSTEM_NAME, List.of(mySystemName));
		queryMap.put(CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_REQUEST_PARAM_PROVIDER_ADDRESS, List.of(mySystemAddress));
		queryMap.put(CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_REQUEST_PARAM_PROVIDER_PORT, List.of(String.valueOf(mySystemPort)));
		queryMap.put(CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_REQUEST_PARAM_SERVICE_DEFINITION, List.of(serviceDefinition));
        queryMap.put("service_uri", List.of(serviceUri));
		final UriComponents unregisterUri = Utilities.createURI(CommonConstants.HTTP, "192.168.10.70", 8443, queryMap, unregisterUriStr);
		
		httpService.sendRequest(unregisterUri, HttpMethod.DELETE, Void.class);
	}
}
