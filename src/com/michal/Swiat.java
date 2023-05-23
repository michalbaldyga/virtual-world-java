package com.michal;

import com.michal.organizmy.Organizm;
import com.michal.organizmy.rosliny.*;
import com.michal.organizmy.zwierzeta.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Swiat {

    // Atrybuty

    private static final int szerokosc = 20;
    private static final int wysokosc = 20;
    private static final int poczatkowaIloscGatunku = 2;
    private final Organizm organizmy[][] = new Organizm[szerokosc][wysokosc];
    private final List<Organizm>  kolejka = new LinkedList<Organizm>();
    private final List<Organizm> martweOrganizmy = new LinkedList<Organizm>();
    private final Komentator komentator = new Komentator();

    // Metody prywatne

    private void sortujKolejke(List<Organizm>  kol) {
        Collections.sort(kol, (Organizm a, Organizm b) -> {
            return b.getInicjatywa() - a.getInicjatywa();
        });
    }

    private Vector<Polozenie> znajdzPolaGenerowania() {
        Vector<Polozenie> pola = new Vector<Polozenie>();
        for (int i = 0; i < wysokosc; i++) {
            for (int j = 0; j < szerokosc; j++) {
                Polozenie p = new Polozenie(i,j);
                if (getOrganizm(p)==null)
                    pola.add(p);
            }
        }
        Collections.shuffle(pola);

        return pola;
    }

    private void usunOrganizmy(JFrame frame) {
        if (!martweOrganizmy.isEmpty()) {
            for (Organizm org: martweOrganizmy) {
                frame.remove(org.getObszarWyswietlania());
                kolejka.remove(org);
            }
            martweOrganizmy.clear();
        }
    }

    private void wczytajOrganizm(String dane) {
        String[] tokeny = dane.split(";");
        String gatunek = tokeny[0];
        int sila = Integer.parseInt(tokeny[1]);
        int x = Integer.parseInt(tokeny[2]);
        int y = Integer.parseInt(tokeny[3]);

        if (gatunek.equals("Czlowiek")) {
            int czasUmiejetnosci = Integer.parseInt(tokeny[4]);
            int czasLadowania = Integer.parseInt(tokeny[5]);
            stworzCzlowieka(sila, x, y, czasUmiejetnosci, czasLadowania);
        }
        else
            stworzOrganizm(gatunek, sila, x, y);

    }

    private void stworzOrganizm(String gatunek, int sila, int x, int y) {
        Organizm org;
        if (gatunek.equals("Antylopa"))
            org = new Antylopa(new Polozenie(x,y), this);
        else if (gatunek.equals("CyberOwca"))
            org = new CyberOwca(new Polozenie(x,y), this);
        else if (gatunek.equals("Lis"))
            org = new Lis(new Polozenie(x,y), this);
        else if (gatunek.equals("Owca"))
            org = new Owca(new Polozenie(x,y), this);
        else if (gatunek.equals("Wilk"))
            org = new Wilk(new Polozenie(x,y), this);
        else if (gatunek.equals("Zolw"))
            org = new Zolw(new Polozenie(x,y), this);
        else if (gatunek.equals("BarszczSosnowskiego"))
            org = new BarszczSosnowskiego(new Polozenie(x,y), this);
        else if (gatunek.equals("Guarana"))
            org = new Guarana(new Polozenie(x,y), this);
        else if (gatunek.equals("Mlecz"))
            org = new Mlecz(new Polozenie(x,y), this);
        else if (gatunek.equals("Trawa"))
            org = new Trawa(new Polozenie(x,y), this);
        else
            org = new WilczeJagody(new Polozenie(x,y), this);

        org.setSila(sila);
        dodajOrganizm(org);
    }

    private void stworzCzlowieka(int sila, int x, int y, int czasUmiejetnosci, int czasLadowania) {
        Czlowiek c = new Czlowiek(new Polozenie(x,y), this);
        c.setSila(sila);
        c.setPozostalyCzasUmiejetnosci(czasUmiejetnosci);
        c.setPozostalyCzasLadowania(czasLadowania);
        dodajOrganizm(c);
    }

    private void wyczyscSwiat(JFrame frame) {
        inicjalizujSwiat();
        martweOrganizmy.addAll(kolejka);
        usunOrganizmy(frame);
        kolejka.clear();
    }

    // Metody publiczne

    public void inicjalizujSwiat() {
        for (int i = 0; i < wysokosc; i++)
            for (int j = 0; j < szerokosc; j++)
                organizmy[i][j] = null;
    }

    public void rysujSwiat(JFrame frame) {
        for (int i = 0; i < wysokosc; i++)
            for (int j = 0; j < szerokosc; j++)
                if (organizmy[i][j] != null)
                    organizmy[i][j].rysujOrganizm(frame);

        SwingUtilities.updateComponentTreeUI(frame); // Odświeżenie frame'a po rysowaniu

        Czlowiek c = znajdzCzlowieka();
        if (c != null)
            System.out.println("Wybierz kierunek ruchu czlowieka:");
    }

    public void wykonajTure(JFrame frame) {

        Czlowiek c = znajdzCzlowieka();
        if (c != null)
            if (c.getKierunekRuchu().equals("")) {
                System.out.println("Przed rozpoczeciem tury wybierz kierunek ruchu czlowieka.");
                return;
            }

        List<Organizm> dotychczasowaKolejka = new LinkedList<Organizm>();
        dotychczasowaKolejka.addAll(kolejka);
        sortujKolejke(dotychczasowaKolejka);

        for (Organizm org : dotychczasowaKolejka)
            if (org.getInicjatywa() >= 0)
                org.akcja();

        komentator.wypiszWyniki();

        usunOrganizmy(frame);
        rysujSwiat(frame);

        if (c != null)
            c.aktualizujUmiejetnosc();
    }

    public void generujOrganizmy() {
        Vector<Polozenie> pola = znajdzPolaGenerowania();
        if (!pola.isEmpty()) {
            dodajOrganizm(new Czlowiek(pola.lastElement(), this));
            pola.remove(pola.lastElement());
        }
        if (!pola.isEmpty()) {
            for (int i = 0; i < poczatkowaIloscGatunku; i++) {
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Antylopa(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new CyberOwca(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Lis(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Owca(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Wilk(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Zolw(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new BarszczSosnowskiego(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Guarana(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Mlecz(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new Trawa(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
                if (!pola.isEmpty()) {
                    dodajOrganizm(new WilczeJagody(pola.lastElement(), this));
                    pola.removeElement(pola.lastElement());
                }
            }
            sortujKolejke(kolejka);
        }
    }

    public void dodajOrganizm(Organizm org) {
        this.organizmy[org.getX()][org.getY()] = org;
        kolejka.add(org);
    }

    public void dodajDoUsuniecia(Organizm org) {
        martweOrganizmy.add(org);
        final int organizm_zginal = -1;
        org.setInicjatywa(organizm_zginal);
    }

    public Czlowiek znajdzCzlowieka() {
        for (Organizm org : kolejka)
            if (org instanceof Czlowiek)
                return (Czlowiek)org;

        return null;
    }

    public void aktywujUmiejetnosc() {
        Czlowiek c = znajdzCzlowieka();
        if (c != null)
            c.aktywujUmiejetnosc();
    }

    public void zapiszStan() {
        try {
            BufferedWriter plik = new BufferedWriter(new FileWriter("zapis.txt"));
            for (Organizm org : kolejka)
                plik.write(org.przygotujDoZapisu());
            plik.close();
            System.out.println("Zapisano stan gry.\n");
        } catch(Exception e) {
            System.out.println("Nie udalo sie zapisac stanu gry.\n");
            e.printStackTrace();
        }
    }

    public void wczytajStan(JFrame frame) {
        try {
            File plik = new File("zapis.txt");
            Scanner skaner = new Scanner(plik);

            wyczyscSwiat(frame);

            while (skaner.hasNextLine()) {
                String linia = skaner.nextLine();
                wczytajOrganizm(linia);
            }

            skaner.close();
            rysujSwiat(frame);
            System.out.println("Wczytano stan gry.\n");

        } catch (FileNotFoundException e) {
            System.out.println("Nie udalo sie wczytac stanu gry.\n");
            e.printStackTrace();
        }
    }

    public void zakonczGre() {
        System.out.println("Koniec gry");
        System.exit(0);
    }

    // Getery
    public Organizm[][] getOrganizmy() {
        return organizmy;
    }
    public Organizm getOrganizm(Polozenie polozenie) {
        return organizmy[polozenie.x][polozenie.y];
    }
    public List<Organizm> getKolejka() {
        return kolejka;
    }
    public int getSzerokosc() {
        return szerokosc;
    }
    public int getWysokosc() {
        return wysokosc;
    }
    public Komentator getKomentator() {
        return komentator;
    }

    // Setery
    public void setOrganizm(Polozenie p, Organizm org) {
        organizmy[p.x][p.y] = org;
    }
}