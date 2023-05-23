package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;

public class Guarana extends Roslina {

    private static final int sila = 0;
    private static final ImageIcon ikona = new ImageIcon("images/guarana.png");

    public Guarana(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, polozenie, swiat);
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        int wzmocnienie = 3;
        atakujacy.setSila(atakujacy.getSila() + wzmocnienie);
        super.kolizja(atakujacy);
        swiat.getKomentator().zapiszWzmocnienie(atakujacy, this);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Guarana(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Guarana";
    }
}
