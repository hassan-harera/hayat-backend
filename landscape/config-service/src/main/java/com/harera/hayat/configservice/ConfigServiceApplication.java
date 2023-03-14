package com.harera.hayat.configservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.Collections;

import static org.springframework.boot.SpringApplication.run;

@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication {

    public static void main(String[] args) {
        run(ConfigServiceApplication.class, args);
    }
}
