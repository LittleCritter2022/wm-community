package com.wm.wmcommunity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class WmCommunityApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(WmCommunityApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n-------------------------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        ">Active: \t{}\n\t" +
                        ">Local: \t{}://{}:{}\n\t" +
                        ">External: \t{}://{}:{}\n\t" +
                        ">Doc: \t\t{}://{}:{}{}/swagger-ui.html\n" +
                        "-------------------------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("spring.profiles.active"),
                "http", "localhost", env.getProperty("server.port"),
                "http", InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
                "http", InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path")
        );
    }

}
