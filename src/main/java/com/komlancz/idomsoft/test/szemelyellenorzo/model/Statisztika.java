package com.komlancz.idomsoft.test.szemelyellenorzo.model;

import org.springframework.util.StringUtils;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity public class Statisztika
{

    @Column private int counter;
    @Column(name = "funkcio_nev")
    private String funkcioNev;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "letrehozasi_datum")
    private Date letrehozasiDatum;

    @Column(name = "modositasi_datum")
    private Date modositasiDatum;

    @Column private String responseStatus;

    @Column private String utolsoHibak;

    public int getCounter()
    {
        return counter;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public String getFunkcioNev()
    {
        return funkcioNev;
    }

    public void setFunkcioNev(String funkcioNev)
    {
        this.funkcioNev = funkcioNev;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getLetrehozasiDatum()
    {
        return letrehozasiDatum;
    }

    public void setLetrehozasiDatum(Date letrehozasiDatum)
    {
        this.letrehozasiDatum = letrehozasiDatum;
    }

    public Date getModositasiDatum()
    {
        return modositasiDatum;
    }

    public void setModositasiDatum(Date modositasiDatum)
    {
        this.modositasiDatum = modositasiDatum;
    }

    public String getResponseStatus()
    {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus)
    {
        this.responseStatus = responseStatus;
    }

    public String getUtolsoHibak()
    {
        return utolsoHibak;
    }

    public void setUtolsoHibak(String utolsoHibak)
    {
        this.utolsoHibak = utolsoHibak;
    }

    public boolean isEmpty()
    {
        return (id == null) || StringUtils.isEmpty(funkcioNev);
    }
}
