package com.michal;

import com.michal.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener {

    private final Swiat swiat;
    private static final int szerokosc = 656;
    private static final int wysokosc = 706;

    public MyFrame(Swiat swiat) {
        this.swiat = swiat;

        ustawFrame();
        stworzMenu();

        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Czlowiek c = swiat.znajdzCzlowieka();
        if (c != null) {
            switch (e.getKeyCode()) {
                case 37: // lewo
                    if (c.getX() >= c.getZasiegRuchu()) { // Lewo
                        c.setKierunekRuchu("lewo");
                        System.out.println("Czlowiek poruszy sie w lewo.");
                    }
                    else
                        System.out.println("Czlowiek nie moze poruszyc sie w tym kierunku.");
                    break;
                case 38: // góra
                    if (c.getY() >= c.getZasiegRuchu()) { // Góra
                        c.setKierunekRuchu("gora");
                        System.out.println("Czlowiek poruszy sie w gore.");
                    }
                    else
                        System.out.println("Czlowiek nie moze poruszyc sie w tym kierunku.");
                    break;
                case 39: // prawo
                    if (c.getX() < swiat.getWysokosc() - c.getZasiegRuchu()) { // Prawo
                        c.setKierunekRuchu("prawo");
                        System.out.println("Czlowiek poruszy sie w prawo.");
                    }
                    else
                        System.out.println("Czlowiek nie moze poruszyc sie w tym kierunku.");
                    break;
                case 40: // dół
                    if (c.getY() < swiat.getSzerokosc() - c.getZasiegRuchu()) { // Dół
                        c.setKierunekRuchu("dol");
                        System.out.println("Czlowiek poruszy sie w dol.");
                    }
                    else
                        System.out.println("Czlowiek nie moze poruszyc sie w tym kierunku.");
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void stworzMenu() {
        JMenuItem zapiszGre = new JMenuItem("Zapisz Gre");
        JMenuItem wczytajGre = new JMenuItem("Wczytaj Gre");
        JMenuItem rozegrajTure = new JMenuItem("Rozegraj Ture");
        JMenuItem umiejtnosc = new JMenuItem("Umiejetnosc");
        JMenuItem wyjscie = new JMenuItem("Wyjdz");

        zapiszGre.addActionListener(e -> swiat.zapiszStan());
        wczytajGre.addActionListener(e -> swiat.wczytajStan(this));
        rozegrajTure.addActionListener(e -> swiat.wykonajTure(this));
        umiejtnosc.addActionListener(e -> swiat.aktywujUmiejetnosc());
        wyjscie.addActionListener(e -> swiat.zakonczGre());

        JMenuBar menu = new JMenuBar();
        menu.add(zapiszGre);
        menu.add(wczytajGre);
        menu.add(rozegrajTure);
        menu.add(umiejtnosc);
        menu.add(wyjscie);

        this.setJMenuBar(menu);
    }

    private void ustawFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(szerokosc,wysokosc);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.black);

        this.addKeyListener(this);
        this.setTitle("Wirtualny Świat - Michał Bałdyga 184523");
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("images/swiat.png");
        this.setIconImage(img.getImage());
    }
}
