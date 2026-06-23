package com.eskcti.algasensors.device.management.api.client.impl;

import org.springframework.http.HttpStatusCode;
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
            .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                throw new SensorMonitoringClientBadGatewayException("Failed to communicate with the Sensor Monitoring service");
            })
            .build();
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