package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;
import com.michal.organizmy.rosliny.BarszczSosnowskiego;

import javax.swing.*;
import java.util.Vector;

public class CyberOwca extends Zwierze {

    private static final int sila = 11;
    private static final int inicjatywa = 4;
    private static final ImageIcon ikona = new ImageIcon("images/cyberowca.png");

    public CyberOwca(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public void akcja() {
        BarszczSosnowskiego barszcz = szukajBarszczu();
        if (barszcz == null)
            super.akcja();
        else {
            int x = barszcz.getX();
            int y = barszcz.getY();
            if (polozenie.x > x)
                sprobujWykonacRuch(new Polozenie(polozenie.x-1, polozenie.y));
            else if (polozenie.x < x)
                sprobujWykonacRuch(new Polozenie(polozenie.x+1, polozenie.y));
            else if (polozenie.y > y)
                sprobujWykonacRuch(new Polozenie(polozenie.x, polozenie.y-1));
            else
                sprobujWykonacRuch(new Polozenie(polozenie.x, polozenie.y+1));
        }

    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new CyberOwca(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "CyberOwca";
    }

    private BarszczSosnowskiego szukajBarszczu() {
        Vector<Organizm> rosliny = new Vector<>();
        Vector<Integer> dystans = new Vector<>();

        for (Organizm org : swiat.getKolejka())
            if (org instanceof BarszczSosnowskiego) {
                rosliny.add(org);
                int d = Math.abs(getX()-org.getX())+Math.abs(getY()-org.getY());
                dystans.add(d);
            }

        if (dystans.size()>0) {
            int najblizszy = znajdzNajblizszy(dystans);
            return (BarszczSosnowskiego)rosliny.get(najblizszy);
        }

        return null;
    }

    private int znajdzNajblizszy(Vector<Integer> tab) {
        int minIndeks = 0;
        for (int i = 0; i < tab.size(); i++)
            if (tab.get(i) < tab.get(minIndeks))
                minIndeks = i;

        return minIndeks;
    }
}
