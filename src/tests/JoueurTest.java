package tests;

import game.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

        @Test
        void test() {

            Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD");

            assertEquals("NORD ^[1] v[60] (m0p58)", nord.toString());
            assertEquals("SUD ^[1] v[60] (m0p58)", sud.toString());

            assertEquals("58 cartes piochées", nord.piocher(58));

            assertEquals("NORD ^[1] v[60] (m58p0)", nord.toString());

            nord.placer(nord, new Carte(2), true);

            assertEquals("NORD ^[2] v[60] (m57p0)", nord.toString());

            assertEquals("58 cartes piochées", sud.piocher(58));

            assertEquals("SUD ^[1] v[60] (m58p0)", sud.toString());

            sud.placer(nord, new Carte(5), false);

            assertEquals("SUD ^[1] v[60] (m57p0)", sud.toString());

            assertEquals("NORD ^[2] v[5] (m57p0)", nord.toString());




        }

}