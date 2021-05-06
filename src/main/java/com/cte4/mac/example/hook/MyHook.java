package com.cte4.mac.example.hook;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MyHook {

    private static MyHook instance;
    private static Object lock = new Object();

    public static void start() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MyHook();
                    instance.process();
                }
            }
        }
    }

    private void process() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("xxx");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        });
        t.start();
    }

    public static void main(String[] args) {
        String uri = String.format("http://localhost:7011/targets/1582");
        HttpClient client = HttpClient.newHttpClient();
        try {
            // String json = new String(Files.readAllBytes(Paths.get("/mnt/d/code/e4/temp/a.json")));
            String cfgJson = loadCfgFile("mac/rule_cfg.json");
            String requestBody = String.format("attribute=mac.init.ruleCfg&value=%s", cfgJson);
            // log.info(requestBody);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(requestBody)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(">>" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadCfgFile(String filePath) {
        String rule = "";
        try {
            URL floc = Optional.ofNullable(ClassLoader.getSystemResource(filePath)).orElseThrow(()->new IOException(String.format("unable to find the file:%s", filePath)));
            rule = Files.readString(Paths.get(floc.toURI()));
        } catch (Exception e) {
            log.error(String.format("fail to load file from %s", filePath), e);
        }
        return rule;
    }

}
