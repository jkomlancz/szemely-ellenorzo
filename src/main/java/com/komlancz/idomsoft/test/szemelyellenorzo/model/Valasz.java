package com.komlancz.idomsoft.test.szemelyellenorzo.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class Valasz
{
    private List<String> hibak;
    private HttpStatus httpStatus;
    private SzemelyDTO szemelyDTO;
    private String uzenet;

    public List<String> getHibak()
    {
        return hibak;
    }

    public void setHibak(List<String> hibak)
    {
        this.hibak = hibak;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus)
    {
        this.httpStatus = httpStatus;
    }

    public SzemelyDTO getSzemelyDTO()
    {
        return szemelyDTO;
    }

    public void setSzemelyDTO(SzemelyDTO szemelyDTO)
    {
        this.szemelyDTO = szemelyDTO;
    }

    public String getUzenet()
    {
        return uzenet;
    }

    public void setUzenet(String uzenet)
    {
        this.uzenet = uzenet;
    }
}
