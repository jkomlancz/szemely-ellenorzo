package com.komlancz.idomsoft.test.szemelyellenorzo.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class Valasz {
    private String uzenet;
    private List<String> hibak;
    private HttpStatus httpStatus;

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }

    public List<String> getHibak() {
        return hibak;
    }

    public void setHibak(List<String> hibak) {
        this.hibak = hibak;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
