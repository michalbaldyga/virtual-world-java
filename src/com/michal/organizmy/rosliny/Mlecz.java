package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;

public class Mlecz extends Roslina {

    private static final int sila = 0;
    private static final ImageIcon ikona = new ImageIcon("images/mlecz.png");

    public Mlecz(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, polozenie, swiat);
    }

    @Override
    public void akcja() {
            int liczbaProbZasiania = 3;
            for (int i = 0; i < liczbaProbZasiania; i++)
                super.zasiej();
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Mlecz(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Mlecz";
    }
}
