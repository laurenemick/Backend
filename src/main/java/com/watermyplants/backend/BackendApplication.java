package com.watermyplants.backend;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackendApplication {

    @Autowired
    private static Environment env;

    private static boolean stop = false;

    private static void checkEnvironmentVariable(String envvar)
    {
        if(System.getenv(envvar) == null)
        {
            stop = true;
        }
    }

    public static void main(String[] args) {
        checkEnvironmentVariable("OAUTHCLIENTID");
        checkEnvironmentVariable("OAUTHCLIENTSECRET");

        if(!stop)
        {
            SpringApplication.run(BackendApplication.class, args);
        }
    }

}
