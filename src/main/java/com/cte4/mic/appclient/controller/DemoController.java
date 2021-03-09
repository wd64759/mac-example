package com.cte4.mic.appclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "hello " + name;
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
    
}
