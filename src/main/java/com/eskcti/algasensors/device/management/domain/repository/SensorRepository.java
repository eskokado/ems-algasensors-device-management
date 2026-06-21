package com.eskcti.algasensors.device.management.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

import com.eskcti.algasensors.device.management.domain.model.Sensor;
import com.eskcti.algasensors.device.management.domain.model.SensorId;

public interface SensorRepository extends Repository<Sensor, SensorId> {
    Sensor saveAndFlush(Sensor sensor);

    Optional<Sensor> findById(SensorId sensorId);

    Page<Sensor> findAll(Pageable pageable);
}
