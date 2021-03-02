package tests;

import game.Joueur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

        @Test
        void test() {

            Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD ");

            assertEquals("NORD ^[1] v[60] (m0p58)", nord.toString());
            assertEquals("SUD  ^[1] v[60] (m0p58)", sud.toString());

            assertEquals("6 cartes piochées", nord.piocher(6));

            assertEquals("NORD ^[1] v[60] (m6p52)", nord.toString());


        }

}