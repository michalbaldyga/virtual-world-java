package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.util.Vector;
import java.util.Random;

public abstract class Zwierze extends Organizm {

    // Metody publiczne

    public Zwierze(int sila, ImageIcon ikona, int inicjatywa, Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
    }

    @Override
    public void akcja() {
        Vector<Polozenie> mozliwosci = znajdzSasiedniePola(zasiegRuchu);
        int losowePole = new Random().nextInt(mozliwosci.size());
        Polozenie nowePolozenie = (mozliwosci.isEmpty() ? polozenie : mozliwosci.elementAt(losowePole));
        sprobujWykonacRuch(nowePolozenie);
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        if(atakujacy.getClass().equals(this.getClass()))
            rozmnoz(atakujacy);
        else
            walcz(atakujacy);
    }

    public void zmienPolozenie(Polozenie nowePolozenie) {
        swiat.setOrganizm(polozenie, null);
        polozenie = nowePolozenie;
        setObszarWyswietlania(nowePolozenie.x, nowePolozenie.y);
        swiat.setOrganizm(polozenie, this);
    }

    // Metody chronione

    protected void sprobujWykonacRuch(Polozenie nowePolozenie) {
        Organizm org = swiat.getOrganizm(nowePolozenie);
        if (polozenie == nowePolozenie) // Jeżeli zwierzę nie może wykonać ruchu
            return;
        else if (org != null)
            org.kolizja(this);
        else
            zmienPolozenie(nowePolozenie);
    }

    protected void rozmnoz(Organizm partner) {
        Vector<Polozenie> mozliwosci = znajdzWolnePola(this);
        if (mozliwosci.isEmpty())
            mozliwosci = znajdzWolnePola(partner);
        if (mozliwosci.isEmpty())
            return;
        else {
            int losowePole = new Random().nextInt(mozliwosci.size());
            Polozenie p = mozliwosci.elementAt(losowePole);
            Organizm potomek = stworzPotomka(p, swiat);
            swiat.dodajOrganizm(potomek);
            swiat.getKomentator().zapiszRozmnazanie(this);
        }
    }

    protected void walcz(Organizm atakujacy) {
        swiat.getKomentator().zapiszAtak(atakujacy, this);

        if (atakujacy.getSila() >= this.getSila()) { // Jeżeli atakujący jest silniejszy
            if (czyOdbilAtak(atakujacy)) {
                swiat.getKomentator().zapiszOdbicie(this);
                return;
            }
            else if(czyZrobilUnik()) {
                Polozenie tmp = polozenie;
                zrobUnik(atakujacy);
                ((Zwierze)atakujacy).zmienPolozenie(tmp);
            }
            else { // Jeżeli odbicie oraz unik nie powiodły się
                swiat.dodajDoUsuniecia(this);
                ((Zwierze)atakujacy).zmienPolozenie(polozenie);
                swiat.getKomentator().zapiszWygrana(atakujacy);
            }
        }
        else { // Jeżeli atakujący jest słabszy
            swiat.dodajDoUsuniecia(atakujacy);
            swiat.setOrganizm(atakujacy.getPolozenie(), null);
            swiat.getKomentator().zapiszWygrana(this);
        }
    }

    protected boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }

    protected boolean czyZrobilUnik() {
        return false;
    }

    // Metody prywatne

    private void zrobUnik(Organizm atakujacy) {
        Vector<Polozenie> mozliwosci = znajdzWolnePola(this);
        if (mozliwosci.isEmpty())
            zmienPolozenie(atakujacy.getPolozenie());
        else {
            int los = new Random().nextInt(mozliwosci.size());
            zmienPolozenie(mozliwosci.elementAt(los));
        }
        swiat.getKomentator().zapiszUnik(this);
    }
}
