package com.cte4.mac.example.controller;

import java.time.Duration;
import java.util.Random;

import com.e4.mac.annotation.LatencySPI;
import com.e4.mac.annotation.MonitorSPI;
import com.e4.mac.annotation.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@MonitorSPI("customer_service")
public class CustomerServiceController {

    @Autowired
    private MeterRegistry meterRegistery;

    @GetMapping(value = "/loan/{id}")
    public String getLoan(@PathVariable String id) throws ServiceException {
        Timer timer = Timer.builder("customer_service").tags("severity", "HIGH", "function", "loan")
            .publishPercentileHistogram().minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofSeconds(5))
            .publishPercentiles(0.5, 0.95).register(meterRegistery);
        Timer.Sample sample = Timer.start();
        log.info("call getLoan(): param - " + id);
        long cost = this.doMore();
        sample.stop(timer);
        return String.format("%s slept %s ms", id, cost);
    }

    @LatencySPI
    @Tag("severity='LOW'")
    @Tag("function='customer'")
    @GetMapping(value = "/customer/{path}")
    public String getCusomer(@PathVariable String path) throws ClassNotFoundException {
        log.info("call getCusomer(): param - " + path);
        try {
            this.doMore();
        } catch (Exception e) {
            log.error("fail to get customer, error=" + e);
        }
        return path;
    }

    private long doMore() throws ServiceException {
        Random random = new Random();
        long sleepy = random.nextInt(200);
        try {
            Thread.sleep(sleepy);
        } catch (InterruptedException e) {
        }
        return sleepy;
    }

}
