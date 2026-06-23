package com.eskcti.algasensors.device.management.api.client.impl;

import java.time.Duration;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.eskcti.algasensors.device.management.api.client.SensorMonitoringClient;
import com.eskcti.algasensors.device.management.api.client.SensorMonitoringClientBadGatewayException;

import io.hypersistence.tsid.TSID;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {
    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClient.Builder builder) {
        this.restClient = builder
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

    @Override
    public void enableMonitoring(TSID sensorId) {
        this.restClient.put()
            .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        this.restClient.delete()
            .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
            .retrieve()
            .toBodilessEntity();
    }
}