package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.util.Random;

public class Zolw extends Zwierze {

    private static final int sila = 2;
    private static final int inicjatywa = 1;
    private static final ImageIcon ikona = new ImageIcon("images/zolw.png");

    public Zolw(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public void akcja() {
        int szansaNaRuch = 25; // 25%
        int los = new Random().nextInt(100);
        if (los < szansaNaRuch)
            super.akcja();
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return (atakujacy.getSila() < 5);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Zolw(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Zolw";
    }
}
