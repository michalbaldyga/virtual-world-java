package com.michal.organizmy;

import javax.swing.*;

import com.michal.FormaZycia;
import com.michal.Polozenie;
import com.michal.Swiat;

import java.util.Vector;

public abstract class Organizm implements FormaZycia {

    // Wymiary obszaru wyświetlania (label)
    private static final int wysokosc = 32;
    private static final int szerokosc = 32;

    // Atrybuty
    protected int sila;
    protected ImageIcon ikona;
    protected JLabel obszarWyswietlania; // Obszar wyświetlania organizmu na planszy
    protected int inicjatywa;
    protected Polozenie polozenie;
    protected Swiat swiat;
    protected static final int zasiegRuchu = 1;

    // Metody chronione
    protected boolean czyPoleWolne(Polozenie p) {
        return (swiat.getOrganizm(p) == null);
    }
    protected Vector<Polozenie> znajdzSasiedniePola(int zasieg) {
        Vector<Polozenie> mozliwosci = new Vector<Polozenie>();

        if (polozenie.x >= zasieg) // Lewo
            mozliwosci.add(new Polozenie(polozenie.x-zasieg, polozenie.y));
        if (polozenie.x < swiat.getWysokosc() - zasieg) // Prawo
            mozliwosci.add(new Polozenie(polozenie.x+zasieg, polozenie.y));
        if (polozenie.y >= zasieg) // Góra
            mozliwosci.add(new Polozenie(polozenie.x, polozenie.y-zasieg));
        if (polozenie.y < swiat.getSzerokosc() - zasieg) // Dół
            mozliwosci.add(new Polozenie(polozenie.x, polozenie.y+zasieg));

        return mozliwosci;
    }
    protected Vector<Polozenie> znajdzWolnePola(Organizm org) {
        Vector<Polozenie> mozliwosci = org.znajdzSasiedniePola(zasiegRuchu);
        Vector<Polozenie> wolnePola = new Vector<Polozenie>();

        for (Polozenie p : mozliwosci)
            if (czyPoleWolne(p))
                wolnePola.add(p);

        return wolnePola;
    }

    // Metody publiczne
    public Organizm(int sila, ImageIcon ikona, int inicjatywa, Polozenie polozenie, Swiat swiat) {
        this.sila = sila;
        this.ikona = ikona;
        this.inicjatywa = inicjatywa;
        this.polozenie = polozenie;
        this.swiat = swiat;
        this.obszarWyswietlania = new JLabel();
    }
    public void rysujOrganizm(JFrame frame) {
        obszarWyswietlania.setBounds(polozenie.x*szerokosc,polozenie.y*wysokosc,szerokosc,wysokosc);
        obszarWyswietlania.setIcon(ikona);
        frame.add(obszarWyswietlania);
    }
    public String przygotujDoZapisu() {
        return (getNazwa() + ";" + getSila() + ";" + getX() + ";" + getY() + "\n");
    }

    // Getery
    public int getSila() {
        return sila;
    }
    public ImageIcon getIkona() {
        return ikona;
    }
    public int getInicjatywa() {
        return inicjatywa;
    }
    public Polozenie getPolozenie() {
        return polozenie;
    }
    public int getX() {
        return polozenie.x;
    }
    public int getY() {
        return polozenie.y;
    }
    public JLabel getObszarWyswietlania() {
        return obszarWyswietlania;
    }
    public int getZasiegRuchu() {
        return zasiegRuchu;
    }

    // Setery
    public void setPolozenie(Polozenie polozenie) {
        this.polozenie = polozenie;
    }
    public void setSila(int sila) {
        this.sila = sila;
    }
    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }
    public void setObszarWyswietlania(int x, int y) {
        obszarWyswietlania.setLocation(x, y);
    }
}
