package com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.model;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.OkmanyDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public class OkmanyEllenorzoValasz
{
    private List<String> hibak;
    private HttpStatus httpStatus;
    private List<OkmanyDTO> okmanyok;
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

    public List<OkmanyDTO> getOkmanyok() {
        return okmanyok;
    }

    public void setOkmanyok(List<OkmanyDTO> okmanyok) {
        this.okmanyok = okmanyok;
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
