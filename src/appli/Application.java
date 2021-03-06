package appli;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import game.Carte;
import game.Joueur;

public class Application {

	public static void main(String[] args) {
		Joueur nord = new Joueur("NORD");
		Joueur sud = new Joueur("SUD ");

		nord.piocher(6);
		sud.piocher(6);

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String s = "";
		boolean tourNord = true;
		boolean etat = true;
		while (!s.equals("fin")) {
			if(etat && !(tourNord && nord.aPerdu(sud)) && !(!tourNord && sud.aPerdu(nord))) {
				System.out.println(nord.toString());
				System.out.println(sud.toString());
				if (tourNord)
					System.out.println(nord.toStringHand());
				else
					System.out.println(sud.toStringHand());
				System.out.print("> ");
			}
			else if(!etat) {
				System.out.print("#> ");
			}
			if(tourNord && nord.aPerdu(sud)){

				System.out.println(nord.getNom() + " a perdu");
				break;

			}
			if(!tourNord && sud.aPerdu(nord)){

				System.out.println(sud.getNom() + "a perdu");
				break;

			}
			s = sc.nextLine();
			if(tourNord) {
				etat = traitement(s, nord, sud);
				if(!etat)
					continue;
				tourNord = false;
				if(nord.aGagne()){

					System.out.println(nord.getNom() + " a gagné");
					break;

				}
			}
			else {
				etat = traitement(s, sud, nord);
				if(!etat)
					continue;
				tourNord = true;
				if(sud.aGagne()){

					System.out.println(sud.getNom() + "a gagné");
					break;


				}
			}

		}
	}

	public static boolean checkRedondance(String[] tab){

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
		int cpt = 0;
		boolean a = false;
		boolean completerHand = false;
		if(tab.length < 2)
			return false;
		for (String mot : tab) {
			if((mot.length() == 3 || mot.length() == 4) && (mot.charAt(2) == 'v' || mot.charAt(2) == '^')){

				if(mot.length() == 4 && mot.charAt(3) == '\''){
					a = true;
					completerHand = true;
					cpt++;
				}
				else if(mot.length() == 4 && mot.charAt(3) != '\'')
					return false;
				else
					a = false;

				if(cpt > 1)
					return false;

				String unite = String.valueOf(mot.charAt(0));
				String dizaine = String.valueOf(mot.charAt(1));

				if(!(estNumerique(unite) && estNumerique(dizaine)))
					return false;

				int nb = Integer.parseInt(unite + dizaine);

				if(nb < 2 || nb > 59)
					return false;

				Carte c = new Carte(nb);

				boolean present = false;

				for(Carte i : j.getHand()){

					if(c.getValeur() == i.getValeur())
						present = true;

				}

				if(!present || !c.estJouable(j, adv))
					return false;


				if(!placer(String.valueOf(mot.charAt(2)), c, j, adv, a, true))
					return false;

			}
			else
				return false;
		}

		if(checkRedondance(tab))
			return false;

		j.placerTest(j, tempAsc, true);
		j.placerTest(j, tempDsc, false);
		j.placerTest(adv, tempAscAdv, true);
		j.placerTest(adv, tempDscAdv, false);

		int i = 0;

		for (String mot : tab) {

			int nb = Integer.parseInt(String.valueOf(mot.charAt(0)) + String.valueOf(mot.charAt(1)));

			Carte c = new Carte(nb);

			if(mot.length() == 4)
				a = true;
			else
				a = false;

			if(!placer(String.valueOf(mot.charAt(2)), c, j, adv, a, false))
				return false;

			i++;

		}

		System.out.print(i + " cartes posees");

		if(j.getDeck().size() == 0)
			System.out.println("");

		if(completerHand && !j.aGagne() && j.getDeck().size() > 0) {
			if(j.getDeck().size() < 6 - j.getHand().size())
				System.out.println(j.piocher(j.getDeck().size()));
			else
				System.out.println(j.piocher(6 - j.getHand().size()));
		}
		else if(!j.aGagne() && j.getDeck().size() > 0) {
			if(j.getDeck().size() < 2)
				System.out.println(j.piocher(j.getDeck().size()));
			else
				System.out.println(j.piocher(2));
		}

		return true;

	}

	private static boolean estNumerique(String s){

		switch(s){

			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			case "8":
			case "9": return true;
			default: return false;

		}

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
