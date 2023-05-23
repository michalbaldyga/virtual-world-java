package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;

public class Trawa extends Roslina {

    private static final int sila = 0;
    private static final ImageIcon ikona = new ImageIcon("images/trawa.png");

    public Trawa(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, polozenie, swiat);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Trawa(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Trawa";
    }
}
