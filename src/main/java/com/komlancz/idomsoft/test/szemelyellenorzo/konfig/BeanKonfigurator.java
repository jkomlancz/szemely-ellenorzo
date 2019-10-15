package com.komlancz.idomsoft.test.szemelyellenorzo.konfig;

import com.komlancz.idomsoft.test.szemelyellenorzo.segito.ValidatorBean;
import com.komlancz.idomsoft.test.szemelyellenorzo.segito.ValidatorBeanImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanKonfigurator {

    @Bean public ValidatorBean validatorBean(){
        return new ValidatorBeanImpl();
    }
}
