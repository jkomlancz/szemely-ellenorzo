package com.komlancz.idomsoft.test.szemelyellenorzo.segito;

public enum NevTipus {
    VISELT("Viselt név"),
    SZUL_NEV("Születési név"),
    ANYJA_NEVE("Anyja neve");

    private String name;

    NevTipus(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
