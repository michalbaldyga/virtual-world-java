package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class Antylopa extends Zwierze {

    private static final int sila = 4;
    private static final int inicjatywa = 4;
    private static final ImageIcon ikona = new ImageIcon("images/antylopa.png");

    public Antylopa(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public void akcja() {
        int zasiegAntylopy = 2*zasiegRuchu;

        Vector<Polozenie> mozliwosci = znajdzSasiedniePola(zasiegRuchu);
        Vector<Polozenie> dodatkoweMozliwosci = znajdzSasiedniePola(zasiegAntylopy);
        mozliwosci.addAll(dodatkoweMozliwosci);

        int losowePole = new Random().nextInt(mozliwosci.size());
        Polozenie nowePolozenie = (mozliwosci.isEmpty() ? polozenie : mozliwosci.elementAt(losowePole));
        sprobujWykonacRuch(nowePolozenie);
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Antylopa(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Antylopa";
    }

    @Override
    public boolean czyZrobilUnik() {
        int szansaNaUnik = 50; // 50%
        int los = new Random().nextInt(100);
        return (los < szansaNaUnik);
    }
}
