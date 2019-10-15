package com.komlancz.idomsoft.test.szemelyellenorzo.kontroller;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.Valasz;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SzemelyAdatEllenorzo {

    @PostMapping(path = "/ellenorzes")
    public Valasz szemelyAdatEllenorzes(@RequestBody SzemelyDTO adat){
        Valasz valasz = new Valasz();
        valasz.setUzenet("Hello world!");
        valasz.setHibak(Arrays.asList("Van egy hiba"));
        valasz.setHttpStatus(HttpStatus.OK);

        return valasz;
    }
}
