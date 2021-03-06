package com.cte4.mac.example.health;

import java.util.Random;

import javax.annotation.PostConstruct;

import com.cte4.mac.example.controller.CustomerServiceController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DummyRunner {

    private boolean endSignal;

    @Autowired CustomerServiceController csController;

    @PostConstruct
    public void start() {
        log.info("kick off simulate requests");
        Thread runner = new Thread(new Runnable(){
            @Override
            public void run() {
                while(!endSignal) {
                    try {
                        csController.getLoan(getChaoes());
                        csController.getCusomer(getChaoes());
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                }
            }
        });
        runner.start();
    }

    public void stop() {
        endSignal = true;
    }

    private String getChaoes() {
        Random random = new Random();
        return Integer.toString(random.nextInt(100000));
    }
 
    
}
