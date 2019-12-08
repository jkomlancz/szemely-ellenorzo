package com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo;

import com.komlancz.idomsoft.test.szemelyellenorzo.konfig.Beallitasok;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.OkmanyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.model.OkmanyEllenorzoValasz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OkmanyEllenorzoRESTService {

    @Autowired Beallitasok beallitasok;
    private static final String OKMANY_ELLENORES_PATH = "/okmany-ellenorzes";

    @Autowired private OkmanyEllenorzoServiceProxy proxy;

    private final RestTemplate restTemplate;

    public OkmanyEllenorzoRESTService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public OkmanyEllenorzoValasz okmanyokEllenorzese(List<OkmanyDTO> okmanyok) throws OkmanyEllenoroServiceException {

        String servicePath;

        switch (beallitasok.getServerStage()){
            case "prod":
                servicePath = beallitasok.getOkmanyEllenorzoProdURL();
                break;

            case "tomcat":
                servicePath = beallitasok.getOkmanyEllenorzoTomcatURL();
                break;

            default:
                servicePath = beallitasok.getOkmanyEllenorzoTestURL();
                break;
        }

        try {
//            OkmanyEllenorzoValasz valasz = this.restTemplate.postForObject(servicePath + OKMANY_ELLENORES_PATH , okmanyok, OkmanyEllenorzoValasz.class);
            OkmanyEllenorzoValasz valasz = proxy.okmanyokEllenorzese(okmanyok);
            if (valasz == null){
                throw new OkmanyEllenoroServiceException("Okmanyok ellenorzese közben ismeretlen hiba lépett fel!");
            }

            if (valasz.getHttpStatus().equals(HttpStatus.OK)){
                return valasz;
            }
            else
            {
                throw new OkmanyEllenoroServiceException(String.format("Okmanyok ellenorzese közben hiba lépett fel az alabbi státusszal: %s státuszkód: %s",
                    valasz.getHttpStatus().name(), valasz.getHttpStatus().value()));
            }
        }
        catch (RestClientException e){
            throw new OkmanyEllenoroServiceException("Okmány ellenorző service nem elérhető az adott URL-en: " + servicePath + OKMANY_ELLENORES_PATH);
        }
    }

}
