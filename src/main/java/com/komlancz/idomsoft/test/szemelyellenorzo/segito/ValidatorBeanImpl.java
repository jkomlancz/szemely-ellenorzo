package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;
import com.komlancz.idomsoft.test.szemelyellenorzo.segito.entitas.Allampolgarsag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ValidatorBeanImpl implements ValidatorBean
{
    private static final Logger logger = LoggerFactory.getLogger(ValidatorBean.class);
    private static final List<String> VALID_KARAKTEREK = Arrays.asList("-", "/", ".", "\'", " ", "Ä", "A", "Á", "B", "C", "D", "E", "É",
            "F", "G", "H", "I", "Í", "J", "K", "L", "M", "N", "O", "Ó", "Ö", "Ő", "P", "R", "S", "T", "U", "Ú", "Ü", "Ű", "V", "Z");

    private static final String ALLAMPOLGARSAG_KODSZOTAR_FAJLNEV = "kodszotar21_allampolg";
    private static final int MAX_NEV_HOSSZ = 80;

    public List<String> ellenorzes(SzemelyDTO adat) throws IOException
    {
        List<String> hibak = new ArrayList<>();

        nevEllenorzes(NevTipus.VISELT, hibak, adat.getVisNev());
        nevEllenorzes(NevTipus.SZUL_NEV, hibak, adat.getSzulNev());
        nevEllenorzes(NevTipus.ANYJA_NEVE, hibak, adat.getaNev());
        nemEllenorzes(hibak, adat.getNeme());
        korEllenorzes(hibak, adat.getSzulDat());

        String allampolgarsag = allampolgarsagEllenorzese(hibak, adat.getAllampKod());
        adat.setAllampDekod(allampolgarsag);

        return hibak;
    }

    private void korEllenorzes(List<String> hibak, Date szulDat){
        if (szulDat != null){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -18);

            Date minimumKorDate = c.getTime();

            c.setTime(new Date());
            c.add(Calendar.YEAR, -120);

            Date maximumKorDate =  c.getTime();

            if (szulDat.after(minimumKorDate)){
                hibak.add("A személy még nem múlt el 18 éves");
            }
            if (szulDat.before(maximumKorDate)){
                hibak.add("A személy több mint 120 éves");
            }
        }else {
            hibak.add("Nincsen születési dátum megadva!");
        }
    }

    private String allampolgarsagEllenorzese(List<String> hibak, String orszagKod) throws IOException
    {
        try
        {
            String allampolgarsag = "";

            JsonNode allampolgarsagKodszotar = kodszotarFelolvasasa(ALLAMPOLGARSAG_KODSZOTAR_FAJLNEV);

            List<Allampolgarsag> allampolgarsagok = allampolgarsagsagokMappalese(allampolgarsagKodszotar);

            Optional<Allampolgarsag> talalat = allampolgarsagok.stream().filter(a -> a.getKod().equals(orszagKod)).findFirst();

            if (talalat.isPresent())
            {
                allampolgarsag = talalat.get().getAllampolgarsag();
            }
            else
            {
                hibak.add("Invalid ország kód: " + orszagKod);
            }

            return allampolgarsag;
        }
        catch (IOException e)
        {
            logger.error("Allamplgarsag kodszotar fajl felolvasasi hiba!", e);
            throw e;
        }
    }

    private List<Allampolgarsag> allampolgarsagsagokMappalese(JsonNode allampolgNode) throws JsonProcessingException
    {
        List<Allampolgarsag> allampolgarsagok = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (JsonNode node : allampolgNode.get("rows"))
        {
            Allampolgarsag allampolgElem = objectMapper.treeToValue(node, Allampolgarsag.class);
            allampolgarsagok.add(allampolgElem);
        }

        return allampolgarsagok;
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

    private JsonNode kodszotarFelolvasasa(String fajlNev) throws IOException
    {
        if (!fajlNev.endsWith(".json"))
        {
            fajlNev += ".json";
        }

        File fajl = new ClassPathResource(fajlNev, this.getClass().getClassLoader()).getFile();
//        File fajl = new File(resource.getPath());

        return JsonLoader.fromFile(fajl);
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

        if (nevTmp.startsWith(" "))
        {
            nevTmp = nevTmp.substring(1);
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
