package com.michal;

import com.michal.organizmy.Organizm;

public interface FormaZycia {

    void akcja();
    void kolizja(Organizm atakujacy);
    Organizm stworzPotomka(Polozenie polozenie, Swiat swiat);
    String getNazwa();
}
