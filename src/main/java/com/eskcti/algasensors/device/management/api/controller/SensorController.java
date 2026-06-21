package com.eskcti.algasensors.device.management.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.eskcti.algasensors.device.management.api.model.SensorInput;
import com.eskcti.algasensors.device.management.api.model.SensorOutput;
import com.eskcti.algasensors.device.management.common.IdGenerator;
import com.eskcti.algasensors.device.management.domain.model.Sensor;
import com.eskcti.algasensors.device.management.domain.model.SensorId;
import com.eskcti.algasensors.device.management.domain.repository.SensorRepository;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @GetMapping
    public Page<SensorOutput> getAll(Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(this::convertToModel);
    }

    @GetMapping("/{sensorId}")
    public SensorOutput getById(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        return convertToModel(sensor);
    }

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
        return convertToModel(sensor);
    }

    @PutMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput update(@PathVariable("sensorId") TSID sensorId, @RequestBody SensorInput input) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        sensor.setName(input.getName());
        sensor.setIp(input.getIp());
        sensor.setLocation(input.getLocation());
        sensor.setProtocol(input.getProtocol());
        sensor.setModel(input.getModel());

        sensor = sensorRepository.saveAndFlush(sensor);
        return convertToModel(sensor);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("sensorId") TSID sensorId) {
        sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        sensorRepository.deleteById(new SensorId(sensorId));
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        sensor.setEnabled(true);
        sensorRepository.saveAndFlush(sensor);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));

        sensor.setEnabled(false);
        sensorRepository.saveAndFlush(sensor);
    }

    private SensorOutput convertToModel(Sensor sensor) {
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
