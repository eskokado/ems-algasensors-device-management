package com.eskcti.algasensors.device.management.domain.repository;

import org.springframework.data.repository.Repository;

import com.eskcti.algasensors.device.management.domain.model.Sensor;
import com.eskcti.algasensors.device.management.domain.model.SensorId;

public interface SensorRepository extends Repository<Sensor, SensorId> {
    Sensor saveAndFlush(Sensor sensor);
}
