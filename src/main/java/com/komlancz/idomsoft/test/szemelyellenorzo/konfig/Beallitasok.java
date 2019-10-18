package com.komlancz.idomsoft.test.szemelyellenorzo.konfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
public class Beallitasok {
    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.profiles.active}")
    private String serverStage;

    @Value("${data.okmanyellenorzo.test.url}")
    private String okmanyEllenorzoTestURL;

    @Value("${data.okmanyellenorzo.prod.url}")
    private String okmanyEllenorzoProdURL;

    @Value("${data.okmanyellenorzo.tomcat.url}")
    private String okmanyEllenorzoTomcatURL;

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setServerStage(String serverStage) {
        this.serverStage = serverStage;
    }

    public void setOkmanyEllenorzoTestURL(String okmanyEllenorzoTestURL) {
        this.okmanyEllenorzoTestURL = okmanyEllenorzoTestURL;
    }

    public void setOkmanyEllenorzoProdURL(String okmanyEllenorzoProdURL) {
        this.okmanyEllenorzoProdURL = okmanyEllenorzoProdURL;
    }

    public void setOkmanyEllenorzoTomcatURL(String okmanyEllenorzoTomcatURL) {
        this.okmanyEllenorzoTomcatURL = okmanyEllenorzoTomcatURL;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerStage() {
        return serverStage;
    }

    public String getOkmanyEllenorzoTestURL() {
        return okmanyEllenorzoTestURL;
    }

    public String getOkmanyEllenorzoProdURL() {
        return okmanyEllenorzoProdURL;
    }

    public String getOkmanyEllenorzoTomcatURL() {
        return okmanyEllenorzoTomcatURL;
    }
}
