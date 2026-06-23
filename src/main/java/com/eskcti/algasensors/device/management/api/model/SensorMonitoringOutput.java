package com.eskcti.algasensors.device.management.api.model;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorMonitoringOutput {
    // The monitoring service serializes the id as a string (TSID value).
    // Use String here to avoid deserialization issues and convert to TSID
    // where needed.
    private String id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;
}
