package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;

public class Owca extends Zwierze {

    private static final int sila = 4;
    private static final int inicjatywa = 4;
    private static final ImageIcon ikona = new ImageIcon("images/owca.png");

    public Owca(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Owca(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Owca";
    }
}
