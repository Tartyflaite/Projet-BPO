package tests;

import game.Carte;
import game.Joueur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    void testEstJouableAsc() {

        Joueur nord = new Joueur("NORD");

        Carte c = new Carte(12);

        assertTrue(c.estJouableAsc(nord));

        nord.ajouterCarte(c);

        nord.placer(nord, c, true);

        Carte c2 = new Carte(5);

        assertFalse(c2.estJouableAsc(nord));

        Carte c3 = new Carte(2);

        assertTrue(c3.estJouableAsc(nord)); // Test dizaine en dessous



    }

    @Test
    void testEstJouableDsc() {

        Joueur nord = new Joueur("NORD");

        Carte c = new Carte(40);

        assertTrue(c.estJouableDsc(nord));

        nord.ajouterCarte(c);

        nord.placer(nord, c, false);

        Carte c2 = new Carte(52);

        assertFalse(c2.estJouableDsc(nord));

        Carte c3 = new Carte(50);

        assertTrue(c3.estJouableDsc(nord)); // Test dizaine au dessus


    }

    @Test
    void testEstJouableAscAdv() {

        Joueur nord = new Joueur("NORD");
        Joueur sud = new Joueur("SUD");

        Carte c = new Carte(12);

        sud.ajouterCarte(c);

        sud.placer(sud, c, true);

        Carte c2 = new Carte(14);

        assertFalse(c2.estJouableAscAdv(sud));

        Carte c3 = new Carte(8);

        assertTrue(c3.estJouableAscAdv(sud));



    }

    @Test
    void testEstJouableDscAdv() {

        Joueur nord = new Joueur("NORD");
        Joueur sud = new Joueur("SUD");

        Carte c = new Carte(43);

        sud.ajouterCarte(c);

        sud.placer(sud, c, false);

        Carte c2 = new Carte(32);

        assertFalse(c2.estJouableDscAdv(sud));

        Carte c3 = new Carte(53);

        assertTrue(c3.estJouableDscAdv(sud));

    }

    @Test
    void testEstToString() {

        Carte c = new Carte(12);

        assertEquals("12", c.toString());

        Carte c2 = new Carte(9);

        assertEquals("09", c2.toString());

    }
}