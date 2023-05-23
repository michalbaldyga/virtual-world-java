package com.michal.organizmy.rosliny;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;
import com.michal.organizmy.zwierzeta.CyberOwca;
import com.michal.organizmy.zwierzeta.Zwierze;

import javax.swing.*;
import java.util.Vector;

public class BarszczSosnowskiego extends Roslina {

    private static final int sila = 10;
    private static final ImageIcon ikona = new ImageIcon("images/barszcz.png");

    public BarszczSosnowskiego(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, polozenie, swiat);
    }

    @Override
    public void akcja() {
        Vector<Polozenie> sasiedniePola = znajdzSasiedniePola(zasiegRuchu);
        for (Polozenie p : sasiedniePola) {
            Organizm org = swiat.getOrganizm(p);
            if ((org instanceof Zwierze) && !(org instanceof CyberOwca)) {
                    swiat.dodajDoUsuniecia(org);
                    swiat.setOrganizm(p, null);
                    swiat.getKomentator().zapiszTrucizne(org, this);
            }
        }
        super.zasiej();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        if (atakujacy instanceof CyberOwca)
            super.kolizja(atakujacy);
        else {
            super.kolizja(atakujacy);
            swiat.dodajDoUsuniecia(atakujacy);
            swiat.setOrganizm(atakujacy.getPolozenie(), null);
            swiat.getKomentator().zapiszTrucizne(atakujacy, this);
        }
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new BarszczSosnowskiego(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "BarszczSosnowskiego";
    }
}
