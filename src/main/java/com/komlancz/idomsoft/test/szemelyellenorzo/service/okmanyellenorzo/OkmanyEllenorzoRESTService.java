package com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.OkmanyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.model.OkmanyEllenorzoValasz;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OkmanyEllenorzoRESTService {

    private static final String OKMANY_ELLENORZO_SERVICE_URL = "http://localhost:5502";
    private static final String OKMANY_ELLENORES_PATH = "/okmany-ellenorzes";

    private final RestTemplate restTemplate;

    public OkmanyEllenorzoRESTService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public OkmanyEllenorzoValasz okmanyokEllenorzese(List<OkmanyDTO> okmanyok) throws OkmanyEllenoroServiceException {

        try {
            OkmanyEllenorzoValasz valasz = this.restTemplate.postForObject(OKMANY_ELLENORZO_SERVICE_URL + OKMANY_ELLENORES_PATH , okmanyok, OkmanyEllenorzoValasz.class);
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
            throw new OkmanyEllenoroServiceException("Okmány ellenorző service nem elérhető az adott URL-en: " + OKMANY_ELLENORZO_SERVICE_URL + OKMANY_ELLENORES_PATH);
        }
    }

}
