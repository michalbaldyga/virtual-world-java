package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;

public class WilczeJagody extends Roslina {

    private static final int sila = 99;
    private static final ImageIcon ikona = new ImageIcon("images/jagody.png");

    public WilczeJagody(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, polozenie, swiat);
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        swiat.setOrganizm(atakujacy.getPolozenie(), null);
        swiat.setOrganizm(getPolozenie(), null);

        swiat.dodajDoUsuniecia(atakujacy);
        swiat.dodajDoUsuniecia(this);

        swiat.getKomentator().zapiszJedzenie(atakujacy, this);
        swiat.getKomentator().zapiszTrucizne(atakujacy, this);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new WilczeJagody(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "WilczeJagody";
    }
}
