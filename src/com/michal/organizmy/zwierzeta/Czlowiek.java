package com.michal.organizmy.zwierzeta;

import com.michal.Polozenie;
import com.michal.Swiat;
import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class Czlowiek extends Zwierze {

    private static final int sila = 5;
    private static final int inicjatywa = 4;
    private static final ImageIcon ikona = new ImageIcon("images/czlowiek.png");
    private int pozostalyCzasUmiejetnosci;
    private int pozostalyCzasLadowania;
    private String kierunekRuchu;

    public Czlowiek(Polozenie polozenie, Swiat swiat) {
        super(sila, ikona, inicjatywa, polozenie, swiat);
        pozostalyCzasUmiejetnosci = 0;
        pozostalyCzasLadowania = 0;
        kierunekRuchu = "";
    }
    @Override
    public void akcja() {
        if (kierunekRuchu != "") {
            swiat.getKomentator().zapiszRuchCzlowieka(kierunekRuchu);
            if (kierunekRuchu == "gora")
                sprobujWykonacRuch(new Polozenie(polozenie.x,polozenie.y-1));
            else if (kierunekRuchu == "dol")
                sprobujWykonacRuch(new Polozenie(polozenie.x,polozenie.y+1));
            else if (kierunekRuchu == "lewo")
                sprobujWykonacRuch(new Polozenie(polozenie.x-1,polozenie.y));
            else
                sprobujWykonacRuch(new Polozenie(polozenie.x+1,polozenie.y));
            kierunekRuchu="";
        }
    }
    @Override
    public void kolizja(Organizm atakujacy) {
        if (czyOdbilAtak(atakujacy)) {
            Vector<Polozenie> wolnePola = znajdzWolnePola(this);
            if (!wolnePola.isEmpty()) {
                int los = new Random().nextInt(wolnePola.size());
                ((Zwierze)atakujacy).zmienPolozenie(wolnePola.elementAt(los));
            }
            swiat.getKomentator().zapiszAtak(atakujacy, this);
            swiat.getKomentator().zapiszOdbicie(this);
        }
        else
            super.walcz(atakujacy);
    }
    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return (pozostalyCzasUmiejetnosci > 0);
    }
    @Override
    public Organizm stworzPotomka(Polozenie polozenie, Swiat swiat) {
        return null;
    }
    @Override
    public String przygotujDoZapisu() {
        return (getNazwa() + ";" + getSila() + ";" + getX() + ";" + getY() + ";" +
                pozostalyCzasUmiejetnosci + ";" + pozostalyCzasLadowania + "\n");
    }
    public void aktywujUmiejetnosc() {
        if (pozostalyCzasLadowania == 0 && pozostalyCzasUmiejetnosci == 0) {
            int czasTrwaniaUmiejetnosci = 5;
            pozostalyCzasUmiejetnosci = czasTrwaniaUmiejetnosci;
            pozostalyCzasLadowania = czasTrwaniaUmiejetnosci;
            System.out.println("Umiejetnosc czlowieka zostala aktywowana.");
        }
        else if (pozostalyCzasUmiejetnosci > 0)
            System.out.println("Umiejetnosc jest juz aktywna (" + pozostalyCzasUmiejetnosci + ").");
        else
            System.out.println("Musisz odczekac " + pozostalyCzasLadowania + " tur, aby ponownie uruchomic umiejetnosc.");
    }
    public void aktualizujUmiejetnosc() {
        if (pozostalyCzasUmiejetnosci > 0)
            pozostalyCzasUmiejetnosci--;
        else if (pozostalyCzasLadowania > 0)
            pozostalyCzasLadowania--;
    }

    //Getery
    @Override
    public String getNazwa() {
        return "Czlowiek";
    }
    public String getKierunekRuchu() {
        return kierunekRuchu;
    }

    // Setery
    public void setKierunekRuchu(String kierunek) {
        this.kierunekRuchu = kierunek;
    }
    public void setPozostalyCzasUmiejetnosci(int czas) {
        pozostalyCzasUmiejetnosci = czas;
    }
    public void setPozostalyCzasLadowania(int czas) {
        pozostalyCzasLadowania = czas;
    }
}
