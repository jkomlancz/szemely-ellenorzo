package com.komlancz.idomsoft.test.szemelyellenorzo.kontroller;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.Valasz;
import com.komlancz.idomsoft.test.szemelyellenorzo.segito.ValidatorBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SzemelyAdatEllenorzo {

    @Autowired
    ValidatorBean validator;

    @PostMapping(path = "/ellenorzes")
    public Valasz szemelyAdatEllenorzes(@RequestBody SzemelyDTO adat){
        Valasz valasz = new Valasz();

        valasz.setHibak(validator.ellenorzes(adat));

        valasz.setUzenet("Hello world!");
        valasz.setHttpStatus(HttpStatus.OK);

        return valasz;
    }
}
