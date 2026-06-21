package com.eskcti.algasensors.device.management.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.eskcti.algasensors.device.management.api.model.SensorInput;
import com.eskcti.algasensors.device.management.api.model.SensorOutput;
import com.eskcti.algasensors.device.management.common.IdGenerator;
import com.eskcti.algasensors.device.management.domain.model.Sensor;
import com.eskcti.algasensors.device.management.domain.model.SensorId;
import com.eskcti.algasensors.device.management.domain.repository.SensorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput create(@RequestBody SensorInput input) {
        Sensor sensor = Sensor.builder()
            .id(new SensorId(IdGenerator.generateTSID()))
            .name(input.getName())
            .ip(input.getIp())
            .location(input.getLocation())
            .protocol(input.getProtocol())
            .model(input.getModel())
            .enabled(false)
            .build();

        sensor = sensorRepository.saveAndFlush(sensor);
        return SensorOutput.builder()
            .id(sensor.getId().getValue())
            .name(sensor.getName())
            .ip(sensor.getIp())
            .location(sensor.getLocation())
            .protocol(sensor.getProtocol())
            .model(sensor.getModel())
            .enabled(sensor.getEnabled())
            .build();
    }
}
