package com.cte4.mic.appclient.controller;

import java.util.Random;

import com.e4.maclient.annotation.Counted;
import com.e4.maclient.annotation.Timed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class DemoController {

    @GetMapping(value = "/hello/{name}")
    @Timed(extraTags = {"host", "module"})
    public String sayHello(@PathVariable String name) {
        Random random = new Random();
        long sleepy = random.nextInt(200);
        try {
            Thread.sleep(sleepy);
        } catch (InterruptedException e) {
        }
        return String.format("%s slept %s ms",name, sleepy);
    }

    @GetMapping(value = "/class/{path}")
    public String getClazz(@PathVariable String path) {
        String msg = String.format("Good to find class: %s", path);
        try {
            Class.forName(path);
        } catch (ClassNotFoundException e) {
            msg = e.toString();
        }
        return msg;
    }

    @GetMapping(value = "/env/{property}")
    public String getProperty(@PathVariable String property) {
        String msg = String.format("key=%s, value=%s", property, System.getProperty(property));
        try {
            log.info(System.getProperties().propertyNames());
        } catch (Exception e) {
            msg = e.toString();
        }
        return msg;
    }

    @PostMapping(value = "/env")
    @Counted
    public String putProperty(@RequestParam("name") String name, @RequestParam("value") String value) {
        String msg = String.format("key=%s, value=%s", name, value);
        try {
            System.setProperty(name, value);
        } catch (Exception e) {
            msg = e.toString();
        }
        return msg;
    }

    
}
