package tests;

import game.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

        @Test
        void testToString() {

            Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD");

            assertEquals("NORD ^[1] v[60] (m0p58)", nord.toString());
            assertEquals("SUD ^[1] v[60] (m0p58)", sud.toString());

            assertEquals("58 cartes piochees", nord.piocher(58));

            assertEquals("NORD ^[1] v[60] (m58p0)", nord.toString());

            nord.placer(nord, new Carte(2), true);

            assertEquals("NORD ^[2] v[60] (m57p0)", nord.toString());

            assertEquals("58 cartes piochees", sud.piocher(58));

            assertEquals("SUD ^[1] v[60] (m58p0)", sud.toString());

            sud.placer(nord, new Carte(5), false);

            assertEquals("SUD ^[1] v[60] (m57p0)", sud.toString());

            assertEquals("NORD ^[2] v[5] (m57p0)", nord.toString());

        }
        
        @Test
        void testPiocher() {
        	
        	Joueur test= new Joueur("TEST");
        	
        	assertEquals(0,test.getHand().size());
        	assertEquals(58,test.getDeck().size());
        	
        	test.piocher(4);
        	
        	assertEquals(4,test.getHand().size());
        	assertEquals(54,test.getDeck().size());
        	
        	
        }
        
        @Test
        void testIsInHand() {
        	
        	Joueur test= new Joueur("TEST");
        	Carte ctest= new Carte(15);
        	
        	test.ajouterCarte(ctest);
        	
        	assertTrue(test.isInHand(ctest));
        	
        }
        
        @Test
        void testTosStringHand() {
        	
        	Joueur test= new Joueur("TEST");
        	Carte ctest1= new Carte(15);
        	Carte ctest2= new Carte(8);
        	
        	test.ajouterCarte(ctest1);
        	
        	assertEquals("cartes TEST { 15 }", test.toStringHand());
        	
        	test.ajouterCarte(ctest2);
        	
        	assertEquals("cartes TEST { 08 15 }", test.toStringHand());
        }
        
        @Test 
        void testPlacer() {
        	
            Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD");
            Carte test= new Carte(15);
            
            nord.ajouterCarte(test);
            nord.placer(nord, test, true);
            
            assertEquals(15,nord.getPileAsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(nord, test, false);
            
            assertEquals(15,nord.getPileDsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(sud, test, true);
            
            assertEquals(15,sud.getPileAsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(sud, test, false);
            
            assertEquals(15,sud.getPileDsc().getValeur());
            assertFalse(nord.isInHand(test));
            
        }
        
        @Test
        void testAPerdu() {
        	
        	Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD");
            
            nord.ajouterCarte(new Carte(2));
            nord.ajouterCarte(new Carte(3));
            nord.ajouterCarte(new Carte(4));
            nord.ajouterCarte(new Carte(59));
            
            assertFalse(nord.aPerdu(sud));
            
            nord.placer(nord, new Carte(3), false);
            
            nord.placer(nord, new Carte(59), true);
            
            assertTrue(nord.aPerdu(sud));
            
        	
        }
        

}






