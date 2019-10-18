package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

import com.komlancz.idomsoft.test.szemelyellenorzo.model.SzemelyDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidatorBeanImplTest {

    private static ValidatorBean validatorBean;

    private static SzemelyDTO szemelyDTO;
    private static Calendar calendar;
    private static List<String> hibak;

    @BeforeClass
    public static void init(){
        validatorBean = new ValidatorBeanImpl();
        calendar =  Calendar.getInstance();
    }

    @Before
    public void beforeTest(){
        hibak = new ArrayList<>();
        initSzemelyesAdatok();
    }

    @Test
    public void megfeleloSzemelyesAdatokTest() throws IOException {
        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(0, hibak.size());
        assertEquals("MAGYARORSZÁG ÁLLAMPOLGÁRA", szemelyDTO.getAllampDekod());
    }

    @Test
    public void hibasKarakterAVisNevbenTest() throws IOException {

        // Given
        szemelyDTO.setVisNev(szemelyDTO.getVisNev() + "*");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void uresVisNevTest() throws IOException {

        // Given
        szemelyDTO.setVisNev(null);

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void hibasKarakterASzulNevbenTest() throws IOException {

        // Given
        szemelyDTO.setSzulNev(szemelyDTO.getSzulNev() + "#");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void hibasKarakterANevbenTest() throws IOException {

        // Given
        szemelyDTO.setaNev(szemelyDTO.getaNev() + "?");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void kevesNevelemANevbenTest() throws IOException {

        // Given
        szemelyDTO.setaNev("dr. KovacsEde");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void tulHosszuNevEsRosszKarakterSzulNevbenTest() throws IOException {

        // Given
        String hosszuNev = "Kovács Ede";
        StringBuilder sb = new StringBuilder(hosszuNev);
        for (int i = 0; i <= 81; i++){
            sb.append("E");
        }
        szemelyDTO.setVisNev(sb.toString());
        szemelyDTO.setSzulNev(szemelyDTO.getSzulNev() + "?");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(2, hibak.size());
    }

    @Test
    public void hibasNemTest() throws IOException {

        // Given
        szemelyDTO.setNeme("K");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void hibasAllampolgarsagTest() throws IOException {

        // Given
        szemelyDTO.setAllampKod("BBB");

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertTrue(szemelyDTO.getAllampDekod().isEmpty());
        assertEquals(1, hibak.size());
    }

    @Test
    public void uresAllampolgarsagTest() throws IOException {

        // Given
        szemelyDTO.setAllampKod(null);

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertTrue(szemelyDTO.getAllampDekod().isEmpty());
        assertEquals(1, hibak.size());
    }

    @Test
    public void tulFiatalTest() throws IOException {

        // Given
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -17);
        Date fiatalSzul =  calendar.getTime();
        szemelyDTO.setSzulDat(fiatalSzul);

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void tulIdosTest() throws IOException {

        // Given
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -121);
        Date idoslSzul =  calendar.getTime();
        szemelyDTO.setSzulDat(idoslSzul);

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    @Test
    public void uresSzulDatTest() throws IOException {

        // Given
        szemelyDTO.setSzulDat(null);

        // When
        hibak = validatorBean.ellenorzes(szemelyDTO);

        // Then
        assertEquals(1, hibak.size());
    }

    private static void initSzemelyesAdatok(){
        szemelyDTO = new SzemelyDTO();
        szemelyDTO.setSzulNev("Születési Név");
        szemelyDTO.setVisNev("Dr. Születési Névné");

        calendar.set(Calendar.YEAR, 1992);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 28);

        szemelyDTO.setSzulDat(calendar.getTime());
        szemelyDTO.setAllampDekod(null);
        szemelyDTO.setAllampKod("HUN");
        szemelyDTO.setaNev("Anyja Neve");
        szemelyDTO.setNeme("F");
    }

}