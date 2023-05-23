package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;
import com.michal.organizmy.zwierzeta.Zwierze;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public abstract class Roslina extends Organizm {

    public Roslina(int sila, ImageIcon ikona, Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, 0, polozenie, swiat);
    }

    @Override
    public void akcja() {
        zasiej();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        ((Zwierze)atakujacy).zmienPolozenie(getPolozenie());
        swiat.dodajDoUsuniecia(this);
        swiat.getKomentator().zapiszJedzenie(atakujacy, this);
    }

    protected boolean czySieje() {
        int szansaNaZasianie = 5; // 5%
        int los = new Random().nextInt(100);
        return (los < szansaNaZasianie);
    }

    protected void zasiej() {
        if (czySieje()) {
            Vector<Polozenie> mozliwosci = znajdzWolnePola(this);
            if (!mozliwosci.isEmpty()) {
                int los = new Random().nextInt(mozliwosci.size());
                Polozenie pole = mozliwosci.elementAt(los);
                Organizm potomek = stworzPotomka(pole, swiat);
                swiat.dodajOrganizm(potomek);
                swiat.getKomentator().zapiszSianie(this);
            }
        }
    }
}
