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
		Joueur sud = new Joueur("SUD ");
		
		Partie(nord, sud);
		
	}

	/*
	[brief] : Déroulement d'une partie
	Joueur [in-out] : Joueur 1
	Joueur [in-out] : Joueur 2
	 */
	private static void Partie(Joueur j, Joueur adv) {
		
		boolean partieEnCours;
		
		j.piocher(6);  // Les deux joueurs piochent
		adv.piocher(6);// 6 cartes chacuns
		
		do {
			
			System.out.println(j.toString());
			System.out.println(adv.toString());
			
			partieEnCours = tourJoueur(j, adv);
			
			if(!partieEnCours) {
				break;
			}
			
			System.out.println(j.toString());
			System.out.println(adv.toString());
			
			partieEnCours = tourJoueur(adv, j);


		} while(partieEnCours);
	}
	
	/*
	[brief] : tour d'un joueur
	Joueur [in-out] : joueur dont c'est le tour
	Joueur [in-out] : joueur adverse
	return : renvoie true si la partie est encore en cours (donc si aucun joueur n'a perdu ni gagné),
	false dans le cas contraire
	 */
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
			
			if(!coupValide) { // Si le coup n'est pas valide on affiche "#> " au lieu de "> "
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

	/*
	[brief] : Permet de vérifier si une carte est spécifiée plusieurs fois lors d'un coup
	String[] [in] : commande lié au coup
	return : renvoie true si il y'a une redondance, false dans le cas contraire
	 */
	private static boolean checkRedondance(String[] tab){

		ArrayList<Integer> nbs = new ArrayList<>();

		for(String mot : tab){

			nbs.add(Integer.parseInt(String.valueOf(mot.charAt(0)) + String.valueOf(mot.charAt(1))));

		}

		Set<Integer> set = new HashSet<>(nbs);

		return set.size() < nbs.size();

	}

	/*
	[brief] : permet de traiter un coup
	String [in] : commande lié au coup
	Joueur [in-out] : joueur en cours
	Joueur [in-out] : adversaire
	return : renvoie true si le coup est valide, false dans le cas contraire
	 */
	private static boolean traitement(String s, Joueur j, Joueur adv) {
		
		String[] tab = s.split("\\s+");
		Carte tempAsc = j.getPileAsc();
		Carte tempDsc = j.getPileDsc();
		Carte tempAscAdv = adv.getPileAsc();
		Carte tempDscAdv = adv.getPileDsc();
		
		
		boolean commandeV;
		boolean jouerAdv;
		boolean completerHand = false;
		
		commandeV = commandeValide(tab, j, adv);

		j.placer(j, tempAsc, true, false);		// On restaure
		j.placer(j, tempDsc, false, false);		// l'état de
		j.placer(adv, tempAscAdv, true, false);	// toutes les
		j.placer(adv, tempDscAdv, false, false);	// piles (adversaire et joueur)

		if (!commandeV) {
			return false;
		}
		
		for (String mot : tab) {

			int nb = Integer.parseInt(String.valueOf(mot.charAt(0)) + String.valueOf(mot.charAt(1)));

			Carte c = new Carte(nb);

			if(mot.length() == 4) { // Si le mot fait 4 caractères c'est que l'on souhaite jouer sur une des piles de l'adversaire
				jouerAdv = true;
				completerHand = true;
			}
				
			else {
				jouerAdv = false;
			}

			placer(String.valueOf(mot.charAt(2)), c, j, adv, jouerAdv, false);

		}

		System.out.print(tab.length + " cartes posées");

		if(completerHand && j.getDeck().size() > 0) { // Si la main doit être completer (coup sur une pile adverse)
													   // et qu'il reste bien des cartes dans la pioche
			if(j.getDeck().size() <= 6 - j.getHand().size()) {     // Si le nombre de cartes dans la pioche est inférieur au
															       // nombre de cartes à piocher alors on pioche toutes
				System.out.println(j.piocher(j.getDeck().size())); // les cartes situées dans la pioche
			}
			else {
				
				System.out.println(j.piocher(6 - j.getHand().size())); // Sinon on pioche le nombre de cartes nécessaires
			}																	// pour atteindre une main de 6 cartes
		}
		
		else if(j.getDeck().size() > 0) { // On vérifie bien que la pioche n'est pas vide
			
			if(j.getDeck().size() < 2) { // Si la pioche est composée d'une seule carte alors on pioche la carte
				System.out.println(j.piocher(1));
			}
				
			else { // Sinon on pioche deux cartes
				System.out.println(j.piocher(2));
			}
				
		}

		return true;

	}

	/*
	[brief] : Vérifie que la commande est valide
	String[] [in] : commande à vérifier
	Joueur [in-out] : joueur en cours
	Joueur [in-out] : joueur adverse
	return : renvoie true si la commande est valide, false dans le cas contraire
	 */
	private static boolean commandeValide(String[] tab, Joueur j, Joueur adv) {
		
		int cpt = 0;
		boolean jouerAdv;
		
		if(tab.length < 2) // Si la commande est inférieur à 2 coups alors on renvoie directement false
			return false;  // puisque un tour doit contenir au moins deux coups joués
		
		for (String mot : tab) { // On parcourt la commande, où chaque mot est un coup
			if((mot.length() == 3 || mot.length() == 4) && (mot.charAt(2) == 'v' || mot.charAt(2) == '^')){ // On vérifie la syntaxe du mot

				if(mot.length() == 4 && mot.charAt(3) == '\''){ // Si le coup concerne une pile adverse
					jouerAdv = true; // Pour signifier que le coup se portera sur une pile adverse
					cpt++;
				}
				else if(mot.length() == 4) { // Sinon on renvoie false
					return false;

				}
				else {
					jouerAdv = false; // Pour signifier que le coup se portera sur une pile du joueur et non de l'adversaire
				}
				if(cpt > 1) { // Si il existe plus d'un coup sur une pile adverse, on renvoie false
					return false;
				}

				String dizaine = String.valueOf(mot.charAt(0)); // On récupère la dizaine de la carte à jouer
				String unite = String.valueOf(mot.charAt(1));  // On récupère l'unité de la carte à jouer

				if(!(Character.isDigit(unite.charAt(0)) && Character.isDigit(dizaine.charAt(0)))) { // Si l'unité et la
																									// dizaine ne sont pas
					return false; 																	// numériques
																									// on renvoie false
				}

				int nb = Integer.parseInt(dizaine + unite); // On converti les deux String en un int

				if(nb < 2 || nb > 59) // Si le nombre n'est pas compris entre 2 et 59, on renvoie false
					return false;

				Carte c = new Carte(nb);

				if(!j.isInHand(c) || !c.estJouable(j, adv)) { // Si la carte n'est pas jouable ou que le joueur
					return false;							  // ne possède pas la carte dans sa main, on renvoie false
				}

				if(placer(String.valueOf(mot.charAt(2)), c, j, adv, jouerAdv, true)) // On teste si l'on peut placer la carte
																						 // sur la pile voulue grâce à la fonction
					return false;													    // placer et à l'argument test
			}
			else
				return false;
		}

		return !checkRedondance(tab); // On vérifie qu'il n'y ait pas de redondance

	}
	
	/*
	[brief] : placer une carte sur une pile
	String [in] : la pile sur laquelle placer la carte (ascendante ou descendante)
	Carte [in] : la carte à placer
	Joueur [in-out] : le joueur en cours
	Joueur [in-out] : le joueur adverse
	Boolean [in] : pour savoir si l'on joue sur une pile du joueur ou de l'adversaire
	Boolean [in] : pour savoir si l'on fait un test ou si l'on place
	return : renvoie true si la carte ne peut être posée, renvoie false dans le cas contraire
	 */
	private static boolean placer(String sens, Carte c, Joueur j, Joueur a, boolean adv, boolean test) {

		if(!adv){ // Si on joue sur une pile du joueur

			if (sens.equals("^")) {
				if(c.estJouableAsc(j)) // Si la carte est jouable sur la pile ascendante on la place
					j.placer(j, c, true, !test); // On place la carte en vérifiant ou non la main du joueur (en fonction de test)
				else
					return true; // sinon on renvoie true
			}
			else {
				if (c.estJouableDsc(j)) // Si la carte est jouable sur la pile descendante on la place
					j.placer(j,c,false, !test); // On place la carte en vérifiant ou non la main du joueur (en fonction de test)
				else
					return true; // sinon on renvoie true

			}

		} else { // Sinon on joue sur une pile adverse

			if (sens.equals("^")) {
				if(c.estJouableAscAdv(a))  // Si la carte est jouable sur la pile ascendante adverse on la place
					j.placer(a,c,true, !test); // On place la carte en vérifiant ou non la main du joueur (en fonction de test)
				else
					return true; // sinon on renvoie true
			}
			else {
				if(c.estJouableDscAdv(a))  // Si la carte est jouable sur la pile descendante adverse on la place
					j.placer(a,c,false, !test); // On place la carte en vérifiant ou non la main du joueur (en fonction de test)
				else
					return true; // sinon on renvoie true
			}
		}

		return false; // Si tout s'est bien passé on renvoie false

	}

}