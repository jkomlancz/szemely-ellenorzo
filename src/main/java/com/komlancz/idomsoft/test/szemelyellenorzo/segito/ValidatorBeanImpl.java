package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorBeanImpl implements ValidatorBean {

    private static final List<String> VALID_KARAKTEREK = Arrays.asList("-", "/", ".", "\'", " ", "Ä", "A", "Á", "B", "C", "D", "E", "É", "F", "G", "H", "I", "Í", "J", "K", "L", "M", "N", "O", "Ó", "Ö", "Ő", "P", "R", "S", "T", "U", "Ú", "Ü", "Ű", "V", "Z");
    private static final int MAX_NEV_HOSSZ = 80;

    public List<String> ellenorzes(SzemelyDTO adat){
        List<String> hibak = new ArrayList<>();

        visNevEllenorzes(hibak, adat.getVisNev());

        return hibak;
    }

    private void visNevEllenorzes(List<String> hibak, String visNev){

        hosszEllenorzes(hibak, visNev);
        karakterEllenorzes(hibak, visNev);
        nevelemekSzamanakEllenorzese(hibak, visNev);
    }

    private void hosszEllenorzes(List<String> hibak, String nev){

        if (nev.length() > MAX_NEV_HOSSZ){
            hibak.add(String.format("A név túl hosszú (%s karakter)! Maximum hossz 80 karakter", nev.length()));
        }
    }

    private void karakterEllenorzes(List<String> hibak, String nev){
        char[] karakterek = nev.toCharArray();

        for (char k : karakterek){
            if (!VALID_KARAKTEREK.contains(String.valueOf(k).toUpperCase())){
                hibak.add("Invalid karakter: " + k);
            }
        }
    }

    private void nevelemekSzamanakEllenorzese(List<String> hibak, String nev){
        String nevTmp = nev.toLowerCase();

        if (nevTmp.startsWith("dr ")) nevTmp = nevTmp.replace("dr ", "");
        if (nevTmp.startsWith("dr. ")) nevTmp = nevTmp.replace("dr. ", "");

        int szokozIndex = nevTmp.indexOf(" ");

        if (szokozIndex > 0){
            String masodikNevelem = nev.substring(szokozIndex);

            if (masodikNevelem.isEmpty()){
                hibak.add("Név elemek száma nem megfelelő");
            }
        }
        else {
            hibak.add("Név elemek száma nem megfelelő");
        }
    }
}
