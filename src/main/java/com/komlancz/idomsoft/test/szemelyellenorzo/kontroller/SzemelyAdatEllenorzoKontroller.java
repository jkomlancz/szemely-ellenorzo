package com.komlancz.idomsoft.test.szemelyellenorzo.kontroller;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.OkmanyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.Statisztika;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.Valasz;
import com.komlancz.idomsoft.test.szemelyellenorzo.repository.StatisztikaRepository;
import com.komlancz.idomsoft.test.szemelyellenorzo.segito.ValidatorBean;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.OkmanyEllenoroServiceException;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.OkmanyEllenorzoRESTService;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.model.OkmanyEllenorzoValasz;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.util.CollectionUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

@RestController public class SzemelyAdatEllenorzoKontroller
{

    private static final String ADAT_ELLENORZES_PATH = "/szemely-adat-ellenorzes";
    @Autowired OkmanyEllenorzoRESTService okmanyEllenorzoRESTService;
    @Autowired StatisztikaRepository statisztikaRepository;
    @Autowired ValidatorBean validator;

    @PostMapping(path = ADAT_ELLENORZES_PATH)
    public Valasz szemelyAdatEllenorzes(@RequestBody SzemelyDTO adat)
    {
        Valasz valasz = new Valasz();
        valasz.setHttpStatus(HttpStatus.OK);

        try
        {
            valasz.setUzenet("IdomSoft validacio!");
            valasz.setHibak(validator.ellenorzes(adat));

            okmanyokEllenorzese(valasz, adat);

            valasz.setSzemelyDTO(adat);
        }
        catch (IOException e)
        {
            valasz.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        statisztikaFrissitese(ADAT_ELLENORZES_PATH, valasz);

        return valasz;
    }

    @GetMapping(path = "/")
    public String welcome()
    {
        return "POST hívással lehet használni az alkalmazást az alábbi path-on: /szemely-adat-ellenorzes";
    }

    @GetMapping(path = "/{p}")
    public String welcome2(@PathVariable("p") String path)
    {
        String rosszPath = String.format("/%s GET nem elérhető! ", path);

        return rosszPath.isEmpty()
            ? "POST hívással lehet használni az alkalmazást az alábbi path-on: /szemely-adat-ellenorzes"
            : (rosszPath + "POST hívással lehet használni az alkalmazást az alábbi path-on: /szemely-adat-ellenorzes");
    }

    private void okmanyokEllenorzese(Valasz valasz, SzemelyDTO adat)
    {
        if (!CollectionUtils.isEmpty(adat.getOkmLista()))
        {
            try
            {
                OkmanyEllenorzoValasz okmanyEllenorzoValasz = okmanyEllenorzoRESTService.okmanyokEllenorzese(adat.getOkmLista());
                adat.setOkmLista((ArrayList<OkmanyDTO>) okmanyEllenorzoValasz.getOkmanyok());

                for (String hiba : okmanyEllenorzoValasz.getHibak())
                {
                    valasz.getHibak().add("Okmany ellenörző hiba üzenet: " + hiba);
                }
            }
            catch (OkmanyEllenoroServiceException e)
            {
                valasz.setHttpStatus(HttpStatus.FAILED_DEPENDENCY);
                valasz.getHibak().add(e.getMessage());
            }
        }
    }

    private void statisztikaFrissitese(String funkcioNev, Valasz valasz)
    {
        Statisztika statisztika = statisztikaRepository.getOneByFunkcioNev(funkcioNev);

        if ((statisztika == null) || statisztika.isEmpty())
        {
            statisztika = new Statisztika();
            statisztika.setLetrehozasiDatum(new Date());
            statisztika.setFunkcioNev(funkcioNev);
            statisztika.setCounter(0);
        }

        statisztika.setCounter(statisztika.getCounter() + 1);

        StringBuilder sb = new StringBuilder();

        for (String hiba : valasz.getHibak())
        {
            sb.append(hiba);
            sb.append(", ");
        }

        statisztika.setUtolsoHibak(sb.toString());
        statisztika.setModositasiDatum(new Date());
        statisztika.setResponseStatus(valasz.getHttpStatus().getReasonPhrase());

        statisztikaRepository.save(statisztika);
    }
}
