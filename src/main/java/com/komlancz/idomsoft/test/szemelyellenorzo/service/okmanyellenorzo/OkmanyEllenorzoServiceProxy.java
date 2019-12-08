package com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.OkmanyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.service.okmanyellenorzo.model.OkmanyEllenorzoValasz;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "okmany-ellenorzo-service", url = "localhost:5502")
@FeignClient(name = "okmany-ellenorzo-service")
@RibbonClient(name = "okmany-ellenorzo-service")
public interface OkmanyEllenorzoServiceProxy {

    @PostMapping(path = "/okmany-ellenorzes")
    OkmanyEllenorzoValasz okmanyokEllenorzese(@RequestBody List<OkmanyDTO> okmanyok);
}
