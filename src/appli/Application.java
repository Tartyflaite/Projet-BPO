package appli;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import game.Carte;
import game.Joueur;

public class Application {

	public static void main(String[] args) {
		Joueur nord = new Joueur("NORD");
		Joueur sud = new Joueur("SUD");
		
		Application.Partie(nord, sud);
		
	}
	
	private static void Partie( Joueur j, Joueur adv) {
		
		boolean partieEnCours = true;
		
		j.piocher(6);
		adv.piocher(6);
		
		while (partieEnCours) {
			
			System.out.println(j.toString());
			System.out.println(adv.toString());
			
			partieEnCours=Application.tourJoueur(j, adv);
			
			if(!partieEnCours) {
				break;
			}
			
			System.out.println(j.toString());
			System.out.println(adv.toString());
			
			partieEnCours=Application.tourJoueur(adv, j);


		}
	}
	
	
	private static boolean tourJoueur(Joueur j, Joueur adv) {
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String s;
		boolean coupValide=true;
		
		System.out.println(j.toStringHand());
		
		if(j.aPerdu(adv)) {
			
			System.out.println("Partie finie, " + adv.getNom() + " a gagne");
			
			return false;
		}
		
		System.out.print("> ");
		
		while(true) {
			
			if(!coupValide) {
				System.out.print("#> ");
			}
			s = sc.nextLine();
			coupValide = traitement(s, j, adv);
			if(!coupValide)
				continue;
			
			if(j.aGagne()){

				System.out.println("partie finie, " + j.getNom() + " a gagne");
				return false;
			}
			return true;
			
		}
		
		
		
	}

	
	private static boolean checkRedondance(String[] tab){

		ArrayList<Integer> nbs = new ArrayList<>();

		for(String mot : tab){

			nbs.add(Integer.parseInt(String.valueOf(mot.charAt(0)) + String.valueOf(mot.charAt(1))));

		}

		Set<Integer> set = new HashSet<>(nbs);

		if(set.size() < nbs.size())
			return true;

		return false;

	}


	private static boolean traitement(String s, Joueur j, Joueur adv) {
		
		String[] tab = s.split("\\s+");
		Carte tempAsc = j.getPileAsc();
		Carte tempDsc = j.getPileDsc();
		Carte tempAscAdv = adv.getPileAsc();
		Carte tempDscAdv = adv.getPileDsc();
		
		
		boolean commandeValide;
		boolean jouerAdv = false;
		boolean completerHand = false;
		
		commandeValide = Application.commandeValide(tab, j, adv);

		j.placerTest(j, tempAsc, true);
		j.placerTest(j, tempDsc, false);
		j.placerTest(adv, tempAscAdv, true);
		j.placerTest(adv, tempDscAdv, false);

		if (!commandeValide) {
			return false;
		}
		
		for (String mot : tab) {

			int nb = Integer.parseInt(String.valueOf(mot.charAt(0)) + String.valueOf(mot.charAt(1)));

			Carte c = new Carte(nb);

			if(mot.length() == 4) {
				jouerAdv = true;
				completerHand = true;
			}
				
			else {
				jouerAdv = false;
			}

			if(!placer(String.valueOf(mot.charAt(2)), c, j, adv, jouerAdv, false)) // en soit c'est pas nécéssaire mais bon je laisse ca quand meme aucazou
				return false;

		}

		System.out.print(tab.length + " cartes posées");
		
		

		if(completerHand && j.getDeck().size() >= 0) {
			
			
			if(j.getDeck().size() <= 6 - j.getHand().size()) {
				
				System.out.println(j.piocher(j.getDeck().size()));
			}
			else {
				
				System.out.println(j.piocher(6 - j.getHand().size()));
			}
		}
		
		else if( j.getDeck().size() >= 0) {
			
			if(j.getDeck().size() < 2) {
				System.out.println(j.piocher(j.getDeck().size()));
			}
				
			else {
				System.out.println(j.piocher(2));
			}
				
		}

		return true;

	}

	
	private static boolean commandeValide(String[] tab, Joueur j, Joueur adv) {
		
		int cpt = 0;
		boolean jouerAdv = false;
		
		if(tab.length < 2)
			return false;
		
		for (String mot : tab) {
			if((mot.length() == 3 || mot.length() == 4) && (mot.charAt(2) == 'v' || mot.charAt(2) == '^')){

				if(mot.length() == 4 && mot.charAt(3) == '\''){
					jouerAdv = true;
					
					cpt++;
				}
				else if(mot.length() == 4 && mot.charAt(3) != '\'') {
					
					return false;

				}
				else {
					jouerAdv = false;
				}
				if(cpt > 1) {
					return false;
				}


				String unite = String.valueOf(mot.charAt(0));
				String dizaine = String.valueOf(mot.charAt(1));

				if(!(Character.isDigit(unite.charAt(0)) && Character.isDigit(dizaine.charAt(0)))) {
					
					return false;
					
				}

				int nb = Integer.parseInt(unite + dizaine);

				if(nb < 2 || nb > 59)
					return false;

				Carte c = new Carte(nb);

				if(!j.isInHand(c) || !c.estJouable(j, adv)) {
					return false;
				}



				if(!placer(String.valueOf(mot.charAt(2)), c, j, adv, jouerAdv, true))
					return false;

			}
			else
				return false;
		}

		if(checkRedondance(tab)) {
			return false;
		}
		
		return true;
		
	}
	
	
	private static boolean placer(String sens, Carte c, Joueur j, Joueur a, boolean adv, boolean test) {

		if(!adv){

			if (sens.equals("^")) {
				if(c.estJouableAsc(j)) {
					if(test)
						j.placerTest(j, c, true);
					else
						j.placer(j,c,true);
				}
				else
					return false;
			}
			else {
				if (c.estJouableDsc(j)) {
					
					if(test)
						j.placerTest(j,c,false);
					else
						j.placer(j, c, false);
					
				}
				else
					return false;

			}

		} else {

			if (sens.equals("^")) {
				if(c.estJouableAscAdv(a)) {
					if(test)
						j.placerTest(a,c,true);
					else
						j.placer(a, c, true);
				}
				else
					return false;
			}
			else {
				if(c.estJouableDscAdv(a)) {
					if(test)
						j.placerTest(a,c,false);
					else
						j.placer(a, c, false);
				}
				else
					return false;
			}
		}

		return true;

	}
	
	

}
