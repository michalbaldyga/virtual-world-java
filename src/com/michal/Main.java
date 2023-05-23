package com.michal;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {

        Swiat swiat = new Swiat();
        swiat.inicjalizujSwiat();
        swiat.generujOrganizmy();
        JFrame frame = new MyFrame(swiat);
        swiat.rysujSwiat(frame);
    }
}
