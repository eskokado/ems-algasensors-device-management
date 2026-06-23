package com.eskcti.algasensors.device.management.api.client;

import java.time.Duration;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestClientFactory {
    private final RestClient.Builder builder;

    public RestClient temperatureMonitoringRestClient() {
        return builder
            .baseUrl("http://localhost:8082")
            .requestFactory(generateClienteHttpRequestFactory())
            .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                throw new SensorMonitoringClientBadGatewayException("Failed to communicate with the Sensor Monitoring service");
            })
            .build();
    }

    private ClientHttpRequestFactory generateClienteHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(5));
        return factory;
    }
}
