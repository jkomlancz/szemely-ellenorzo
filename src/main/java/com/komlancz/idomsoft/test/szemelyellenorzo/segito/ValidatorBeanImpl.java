package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorBeanImpl implements ValidatorBean
{

    private static final List<String> VALID_KARAKTEREK = Arrays.asList("-", "/", ".", "\'", " ", "Ä", "A", "Á", "B", "C", "D", "E", "É",
            "F", "G", "H", "I", "Í", "J", "K", "L", "M", "N", "O", "Ó", "Ö", "Ő", "P", "R", "S", "T", "U", "Ú", "Ü", "Ű", "V", "Z");
    private static final String[] VALID_NEMEK = new String[] { "F", "N" };
    private static final int MAX_NEV_HOSSZ = 80;

    public List<String> ellenorzes(SzemelyDTO adat)
    {
        List<String> hibak = new ArrayList<>();

        nevEllenorzes(NevTipus.VISELT, hibak, adat.getVisNev());
        nevEllenorzes(NevTipus.SZUL_NEV, hibak, adat.getSzulNev());
        nevEllenorzes(NevTipus.ANYJA_NEVE, hibak, adat.getaNev());
        nemEllenorzes(hibak, adat.getNeme());

        return hibak;
    }

    private void hosszEllenorzes(NevTipus nevTipus, List<String> hibak, String nev)
    {

        if (nev.length() > MAX_NEV_HOSSZ)
        {
            hibak.add(String.format("A %s túl hosszú (%s karakter)! Maximum hossz 80 karakter", nevTipus.getName(), nev.length()));
        }
    }

    private void karakterEllenorzes(NevTipus nevTipus, List<String> hibak, String nev)
    {
        char[] karakterek = nev.toCharArray();

        for (char k : karakterek)
        {
            if (!VALID_KARAKTEREK.contains(String.valueOf(k).toUpperCase()))
            {
                hibak.add(String.format("%s esetében invalid karakter található (%s)", nevTipus.getName(), k));
            }
        }
    }

    private void nemEllenorzes(List<String> hibak, String neme)
    {
        if (!neme.isEmpty())
        {
            if (!neme.equals("F") && !neme.equals("N"))
            {
                hibak.add("Nem megfelelő nem azonosító: " + neme);
            }
        }
        else
        {
            hibak.add("Nem paraméter üres!");
        }
    }

    private void nevelemekSzamanakEllenorzese(NevTipus nevTipus, List<String> hibak, String nev)
    {
        String nevTmp = nev.toLowerCase();

        if (nevTmp.startsWith("dr "))
        {
            nevTmp = nevTmp.replace("dr ", "");
        }

        if (nevTmp.startsWith("dr. "))
        {
            nevTmp = nevTmp.replace("dr. ", "");
        }

        if (nevTmp.endsWith(" dr"))
        {
            nevTmp = nevTmp.replace(" dr", "");
        }

        if (nevTmp.endsWith(" dr."))
        {
            nevTmp = nevTmp.replace(" dr.", "");
        }

        int szokozIndex = nevTmp.indexOf(" ");

        if (szokozIndex > 0)
        {
            String masodikNevelem = nev.substring(szokozIndex);

            if (masodikNevelem.isEmpty())
            {
                hibak.add(String.format("%s elemek száma nem megfelelő", nevTipus.getName()));
            }
        }
        else
        {
            hibak.add(String.format("%s elemek száma nem megfelelő", nevTipus.getName()));
        }
    }

    private void nevEllenorzes(NevTipus nevTipus, List<String> hibak, String visNev)
    {
        hosszEllenorzes(nevTipus, hibak, visNev);
        karakterEllenorzes(nevTipus, hibak, visNev);
        nevelemekSzamanakEllenorzese(nevTipus, hibak, visNev);
    }
}
