package com.eskcti.algasensors.device.management;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.eskcti.algasensors.device.management.common.IdGenerator;

import io.hypersistence.tsid.TSID;

class TSIDTest {
    @Test
    void shouldGenerateTSID() {
        TSID tsid = IdGenerator.generateTSID();
        Assertions.assertThat(tsid.getInstant()).isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));   
        System.out.println("Generated TSID: " + tsid);
        System.out.println("Generated TSID Long: " + tsid.toLong());
        System.out.println("Generated TSID Instant: " + tsid.getInstant());
    }
}
