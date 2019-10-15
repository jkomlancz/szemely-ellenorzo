package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;

import java.util.ArrayList;
import java.util.List;

public class ValidatorBeanImpl implements ValidatorBean {

    public List<String> ellenorzes(SzemelyDTO adat){
        List<String> hibak = new ArrayList<>();

        visNevEllenorzes(hibak, adat.getVisNev());

        return hibak;
    }

    private void visNevEllenorzes(List<String> hibak, String visNev){
        if (!nevelemekSzamanakEllenorzese(visNev)) hibak.add("Viselt név elemek száma nem megfelelő");
    }

    private boolean nevelemekSzamanakEllenorzese(String nev){
        String nevTmp = nev.toLowerCase();

        if (nevTmp.startsWith("dr ")) nevTmp = nevTmp.replace("dr ", "");
        if (nevTmp.startsWith("dr. ")) nevTmp = nevTmp.replace("dr. ", "");

        int szokozIndex = nevTmp.indexOf(" ");

        if (szokozIndex > 0){
            String masodikNevelem = nev.substring(szokozIndex);

            return !masodikNevelem.isEmpty();
        }
        else {
            return false;
        }
    }
}
