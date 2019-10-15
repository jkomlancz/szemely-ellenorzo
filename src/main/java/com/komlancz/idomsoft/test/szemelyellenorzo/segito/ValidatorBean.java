package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;

import java.util.List;

public interface ValidatorBean {

    List<String> ellenorzes(SzemelyDTO adat);

}
