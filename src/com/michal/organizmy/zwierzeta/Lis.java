package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class Lis extends Zwierze {

    private static final int sila = 3;
    private static final int inicjatywa = 7;
    private static final ImageIcon ikona = new ImageIcon("images/lis.png");

    public Lis(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public void akcja() {
        Vector<Polozenie> mozliwosci = znajdzSasiedniePola(zasiegRuchu);
        Polozenie nowePolozenie;

        if (mozliwosci.isEmpty()) // Jeżeli lis nie może się nigdzie ruszyć
            return; // Zostaje na swoim miejscu
        else {
            Vector<Polozenie> bezpiecznePola = new Vector<>();
            for (Polozenie p : mozliwosci) {
                Organizm atakowany = swiat.getOrganizm(p);
                if (atakowany == null)
                    bezpiecznePola.add(p);
                else
                    if (getSila() >= atakowany.getSila())
                        bezpiecznePola.add(p);
            }
            if (bezpiecznePola.isEmpty()) // Jeżeli na sąsiednich polach są same silniejsze organizmy
                return; // Lis zostaje na swoim miejscu
            else {
                int los = new Random().nextInt(bezpiecznePola.size());
                sprobujWykonacRuch(bezpiecznePola.elementAt(los));
            }
        }
    }

    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return new Lis(polozenie, swiat);
    }

    @Override
    public String getNazwa() {
        return "Lis";
    }
}
