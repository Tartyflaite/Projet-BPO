package tests;

import game.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

        @Test
        void testToString() {

            Joueur nord = new Joueur("NORD");
            Joueur sud = new Joueur("SUD");

            assertEquals("NORD ^[01] v[60] (m0p58)", nord.toString());
            assertEquals("SUD ^[01] v[60] (m0p58)", sud.toString());

            assertEquals(", 58 cartes piochées", nord.piocher(58));

            assertEquals("NORD ^[01] v[60] (m58p0)", nord.toString());

            nord.placer(nord, new Carte(2), true, true);

            assertEquals("NORD ^[02] v[60] (m57p0)", nord.toString());

            assertEquals(", 58 cartes piochées", sud.piocher(58));

            assertEquals("SUD ^[01] v[60] (m58p0)", sud.toString());

            sud.placer(nord, new Carte(5), false, true);

            assertEquals("SUD ^[01] v[60] (m57p0)", sud.toString());

            assertEquals("NORD ^[02] v[05] (m57p0)", nord.toString());

        }
        
        @Test
        void testPiocher() {
        	
        	Joueur test= new Joueur("TEST");
        	
        	assertEquals(0,test.getHand().size());
        	assertEquals(58,test.getDeck().size());
        	
        	test.piocher(4);
        	
        	assertEquals(4,test.getHand().size());
        	assertEquals(54,test.getDeck().size());
        	
        	test.piocher(0);
        	
        	
        }
        
        @Test
        void testIsInHand() {
        	
        	Joueur test= new Joueur("TEST");
        	Carte ctest= new Carte(15);
        	Carte ctest2=new Carte(15);
        	
        	test.ajouterCarte(ctest);
        	
        	assertTrue(test.isInHand(ctest2));
        	
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
            nord.placer(nord, test, true, true);
            
            assertEquals(15,nord.getPileAsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(nord, test, false, true);
            
            assertEquals(15,nord.getPileDsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(sud, test, true, true);
            
            assertEquals(15,sud.getPileAsc().getValeur());
            assertFalse(nord.isInHand(test));
            
            nord.ajouterCarte(test);
            nord.placer(sud, test, false, true);
            
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

            nord.placer(nord, new Carte(2), false, true);

            nord.placer(nord, new Carte(4), true, true);
            
            assertTrue(nord.aPerdu(sud));


            sud.ajouterCarte(new Carte(12));
            sud.ajouterCarte(new Carte(2));
            sud.ajouterCarte(new Carte(58));
            sud.ajouterCarte(new Carte(4));

            sud.placer(sud, new Carte(2), true, true);

            assertTrue(nord.aPerdu(sud));

            sud.placer(sud, new Carte(58), false, true);

            assertTrue(nord.aPerdu(sud));


            sud.placer(sud, new Carte(4), true, true);


            assertFalse(nord.aPerdu(sud));
        	
        }

        @Test
        void testAGagne(){

            Joueur nord = new Joueur("NORD");

            nord.piocher(58);

            nord.resetHand();

            assertTrue(nord.aGagne());

        }
        

}






