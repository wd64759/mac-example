package com.cte4.mac.example;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MicrometerTest {

    MeterRegistry registry = new SimpleMeterRegistry();
    
    MicrometerTest() {
        log.info("init common tags");
        registry.config().commonTags("app","MicrometerTest");
    }

    @Test
    public void testGauge() {

        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        Gauge.builder("jvm.threads.peak", threadBean, ThreadMXBean::getPeakThreadCount)
                .description("The peak live thread count since the Java virtual machine started or peak was reset")
                .baseUnit(BaseUnits.THREADS).register(registry);

        log.info(registry.get("jvm.threads.peak").gauge().measure());
    }

    @Test
    public void testHistogram() {
        Timer timer = Timer.builder("api.latency").publishPercentileHistogram().publishPercentiles(0.95, 0.99)
                .register(registry);

        IntStream.range(1, 100).forEach(i -> {
            timer.record(Duration.ofMillis(ThreadLocalRandom.current().nextInt(200)));
            registry.getMeters().forEach(m->log.info(m.getId() + ":" + m.measure()));
        });
        // log.info(registry.get("api.latency").meter().measure());
    }

    @Test
    public void testTimer() {
        String metricName = "http,request";
        Timer timer = Timer.builder(metricName).tags("uri", "pull", "app", "local").publishPercentileHistogram().publishPercentiles(0.6,0.95).register(this.registry);

        IntStream.range(1, 10).forEach(i->{
            timer.record(Duration.ofMillis(ThreadLocalRandom.current().nextInt(200)));
        });
        registry.getMeters().forEach(m->log.info(m.getId() + ":" + m.measure() + ":" + m.getId().getType()));
    }
}
