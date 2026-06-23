package com.eskcti.algasensors.device.management.api.client.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.eskcti.algasensors.device.management.api.client.RestClientFactory;
import com.eskcti.algasensors.device.management.api.client.SensorMonitoringClient;

import io.hypersistence.tsid.TSID;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {
    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
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