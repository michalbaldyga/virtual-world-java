package com.michal;

import com.michal.organizmy.Organizm;

import javax.swing.*;
import java.awt.*;

public class Komentator {

    private String informacje;
    private static final int liczbaRoslin = 5;
    private boolean czyRoslinaSieje[] = new boolean[liczbaRoslin];
    private static final int liczbaZwierzat = 6;
    private boolean czyZwierzeRozmnaza[] = new boolean[liczbaZwierzat];

    Komentator() {
        informacje = "";
        for (int i = 0; i < liczbaRoslin; i++)
            czyRoslinaSieje[i] = false;
        for (int i = 0; i < liczbaZwierzat; i++)
            czyZwierzeRozmnaza[i] = false;
    }

    public void zapiszAtak(Organizm atakujacy, Organizm atakowany) {
        informacje += atakujacy.getNazwa() + " atakuje " + atakowany.getNazwa() + ". ";
    }

    public void zapiszWygrana(Organizm wygrany) {
        informacje += wygrany.getNazwa() + " wygrywa walke.\n";
    }

    public void zapiszUnik(Organizm atakowany) {
        informacje += atakowany.getNazwa() + " robi unik.\n";
    }

    public void zapiszOdbicie(Organizm atakowany) {
        informacje += atakowany.getNazwa() + " odbil atak.\n";
    }

    public void zapiszRozmnazanie(Organizm rodzic) {
        switch(rodzic.getNazwa())
        {
            case "Antylopa":
                czyZwierzeRozmnaza[0] = true;
                break;
            case "CyberOwca":
                czyZwierzeRozmnaza[1] = true;
                break;
            case "Lis":
                czyZwierzeRozmnaza[2] = true;
                break;
            case "Owca":
                czyZwierzeRozmnaza[3] = true;
                break;
            case "Wilk":
                czyZwierzeRozmnaza[4] = true;
                break;
            case "Zolw":
                czyZwierzeRozmnaza[5] = true;
                break;
        }
    }

    public void zapiszRuchCzlowieka(String kierunek) {
        switch(kierunek) {
            case "gora":
                informacje += "Czlowiek poruszyl sie w gore.\n";
                break;
            case "dol":
                informacje += "Czlowiek poruszyl sie w dol.\n";
                break;
            case "lewo":
                informacje += "Czlowiek poruszyl sie w lewo.\n";
                break;
            case "prawo":
                informacje += "Czlowiek poruszyl sie w prawo.\n";
                break;
        }
    }

    public void zapiszSianie(Organizm rodzic) {
        switch(rodzic.getNazwa())
        {
            case "BarszczSosnowskiego":
                czyRoslinaSieje[0] = true;
                break;
            case "Guarana":
                czyRoslinaSieje[1] = true;
                break;
            case "Mlecz":
                czyRoslinaSieje[2] = true;
                break;
            case "Trawa":
                czyRoslinaSieje[3] = true;
                break;
            case "WilczeJagody":
                czyRoslinaSieje[4] = true;
                break;
        }
    }

    public void zapiszJedzenie(Organizm atakujacy, Organizm zjedzony) {
        informacje += atakujacy.getNazwa() + " zjadl " + zjedzony.getNazwa() + ".\n";
    }

    public void zapiszWzmocnienie(Organizm atakujacy, Organizm zjedzony) {
        informacje += zjedzony.getNazwa() + " dodaje sily " + atakujacy.getNazwa() + ".\n";
    }

    public void zapiszTrucizne(Organizm atakujacy, Organizm zjedzony) {
        informacje += zjedzony.getNazwa() + " zatrul " + atakujacy.getNazwa() + ".\n";
    }

    public void podsumujRozmanazanie() {
        if (czyZwierzeRozmnaza[0])
            informacje += "Antylopy sie rozmnozyly.\n";
        if (czyZwierzeRozmnaza[1])
            informacje += "CyberOwce sie rozmnozyly.\n";
        if (czyZwierzeRozmnaza[2])
            informacje += "Lisy sie rozmnozyly.\n";
        if (czyZwierzeRozmnaza[3])
            informacje += "Owce sie rozmnozyly.\n";
        if (czyZwierzeRozmnaza[4])
            informacje += "Wilki sie rozmnozyly.\n";
        if (czyZwierzeRozmnaza[5])
            informacje += "Zolwie sie rozmnozyly.\n";

        for (int i = 0; i < liczbaZwierzat; i++) {
            czyZwierzeRozmnaza[i] = false;
        }
    }

    public void podsumujSianie() {
        if (czyRoslinaSieje[0])
            informacje += "BarszczSosnowskiego sie rozprzestrzenil.\n";
        if (czyRoslinaSieje[1])
            informacje += "Guarana sie rozprzestrzenila.\n";
        if (czyRoslinaSieje[2])
            informacje += "Mlecz sie rozprzestrzenil.\n";
        if (czyRoslinaSieje[3])
            informacje += "Trawa sie rozprzestrzenila.\n";
        if (czyRoslinaSieje[4])
            informacje += "WilczeJagody sie rozprzestrzenily.\n";

        for (int i = 0; i < liczbaRoslin; i++) {
            czyRoslinaSieje[i] = false;
        }
    }

    public void wypiszWyniki() {

        podsumujRozmanazanie();
        podsumujSianie();

        System.out.println("PODSUMOWANIE TURY:");
        System.out.println("=============================================");

        if (informacje.isEmpty())
            System.out.println("Nie wykryto zadnych zdarzen.");
        else
            System.out.println(informacje);

        System.out.println("=============================================");
        informacje = "";
    }
}
