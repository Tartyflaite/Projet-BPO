package game;

import java.util.ArrayList;
import java.util.Random;

public class Joueur {
	private final ArrayList<Carte> deck; // ArrayList du type Carte correspondant à la pioche
	private final ArrayList<Carte> hand; // ArrayList du type Carte correspondant à la main
	private Carte pileAsc; // dernière carte posée sur la pile ascendante
	private Carte pileDsc; // dernière carte posée sur la pile descendante
	private final String nom; // nom du joueur
	static Random pioche = new Random();
	
	public Joueur(String nom) {
		this.deck= new ArrayList<>();
		for(int i=2;i<60;++i) { // Initialisation de la pioche
			this.deck.add(new Carte(i));
		}
		this.hand= new ArrayList<>(); // Initialisation de la main
		this.pileAsc= new Carte(1); // Initialisation de la pile Ascendante
		this.pileDsc= new Carte(60); // Initialisation de la pile Descendante
		this.nom=nom; // Initialisation du nom du joueur à partir du String passé en paramètre
	}


	/*
	[brief] : Renvoie le nom du joueur
	return : Renvoie un String
	 */
	public String getNom(){

		return nom;

	}

	/*
	[brief] : Renvoie la carte sur la pile ascendante du joueur
	return : Renvoie un type Carte
	 */
	public Carte getPileAsc() {

		return pileAsc;
	}

	/*
	[brief] : Renvoie la carte sur la pile descendante du joueur
	return : Renvoie un type Carte
	 */
	public Carte getPileDsc() {
		return pileDsc;
	}

	/*
	[brief] : Renvoie la main du joueur sous forme d'une liste de cartes
	return : Renvoie une ArrayList du type Carte
	 */
	public ArrayList<Carte> getHand(){
		return hand;
	}

	/*
	[brief] : Renvoie la pioche du joueur sous forme d'une liste de cartes
	return : Renvoie une ArrayList du type Carte
	 */
	public ArrayList<Carte> getDeck(){
		return deck;
	}

	/*
	[brief] : Ajoute une carte à la main du joueur
	Carte [in] : Carte à ajouter
	 */
	public void ajouterCarte(Carte carte) {
		this.hand.add(carte);
	}

	/*
	[brief] : Renvoie une chaîne de caractères correspondant à
	l'affichage du joueur (piles + nombre de cartes dans la main et dans la pioche)
	return : Renvoie un String
	 */
	public String toString() {

		String asc = "";
		String dsc = "";

		if(pileAsc.getValeur() < 10)
			asc += "0";
		if(pileDsc.getValeur() < 10)
			dsc += "0";

		asc += String.valueOf(pileAsc.getValeur());
		dsc += String.valueOf(pileDsc.getValeur());

		return this.nom + " " +
				"^[" + asc + "] " +
				"v[" + dsc + "] " +
				"(m" + this.hand.size() +
				"p" + this.deck.size() + ")";
	}

	/*
	[brief] : Renvoie une chaîne de caractères correspondant à l'affichage de la main du joueur
	return : Renvoie un String
	 */
	public String toStringHand() {

		ArrayList<Carte> listeCroissante  = hand; // ArrayList de Cartes temporaire pour afficher la main dans l'ordre croissant

		for(int i = 0 ; i < listeCroissante.size() ; i++){ // Pour trier l'ArrayList précédemment créée

			for(int j = i+1 ; j < listeCroissante.size() ; j++){

				if(listeCroissante.get(j).getValeur() < listeCroissante.get(i).getValeur()){

					Carte tmp = new Carte(listeCroissante.get(i).getValeur());
					listeCroissante.set(i, listeCroissante.get(j));
					listeCroissante.set(j, tmp);

				}

			}

		}

		StringBuilder sb= new StringBuilder(); // Utilisation de StringBuilder pour la création du String
		sb.append("cartes ");
		sb.append(this.nom);
		sb.append(" { ");
		for(Carte c : listeCroissante) {
			sb.append(c.toString()).append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	/*
	[brief] : Récupère une carte dans la pioche et l'ajoute à la main du joueur (et l'enlève de la pioche)
	int [in] : le nombre de cartes à piocher
	return : Renvoie un String corrrespondant au nombre de cartes piochées
	 */
	public String piocher(int nb_cartes) {
		int i;
		 // La fonction piocher tire aléatoirement une carte dans la pioche ordonnée
		for(i=0; i<nb_cartes && this.deck.size()>0;++i) {
			Carte carte= deck.get(pioche.nextInt(this.deck.size()));
			hand.add(carte);
			deck.remove(carte);
		}

		return ", " + i +" cartes piochées";

	}

	/*
	[brief] : Pour vérifier si une carte est dans la main du joueur
	Carte [in] : La carte à vérifier
	return : Renvoie true si la carte est présente, false dans le cas contraire
	 */
	public boolean isInHand(Carte carte) {
		
		for(Carte c:hand) {
			
			if(c.getValeur()==carte.getValeur()) {
				return true;
			}
			
		}
		
		return false;
	}

	/*
	[brief] : Pour placer une carte sur une pile (du joueur ou de l'adversaire)
	en vérifiant ou non la main du joueur
	Joueur [in-out] : sur les piles de quel joueur va-t-on placer la carte
	Carte [in] : carte à placer
	Boolean [in] : true si on joue sur la pile ascendante, false pour la pile descendante
	Boolean [in] : true si l'on souhaite vérifier si le joueur possède la carte dans sa main, false dans le cas contraire
	 */
	public void placer(Joueur j, Carte carte, boolean asc, boolean verifHand){

		boolean trouve = false;

		for(int i = 0 ; i < this.hand.size() ; i++){

			if(verifHand) {

				if (this.hand.get(i).getValeur() == carte.getValeur()) {
					trouve = true;
					this.hand.remove(i);
					break;

				}
			}

		}

		if(verifHand && !trouve)
			return;

		if(asc)
			j.pileAsc = carte;
		else
			j.pileDsc = carte;

	}

	/*
	[brief] : Pour verifier si le joueur a perdu
	Joueur [in] : on inclut l'adversaire afin de pouvoir accèder à ses piles et vérifier si le joueur peut jouer dessus
	return : renvoie true si le joueur a perdu, false dans le cas contraire
	 */
	public boolean aPerdu(Joueur adv) {
		boolean cartesJouables=true;
		for(Carte i:hand) {
			if(cartesJouables && i.estJouable(this,adv)) {

				Carte temp;

				if(cartesJouables && i.estJouableAsc(this)) {
					temp=pileAsc;
					this.placer(this, i, true, false);
					cartesJouables=this.mainJouabletier2(adv, i,false);
					this.placer(this, temp, true, false); // Restaure l'état de la pile ascendante
				}


				if(cartesJouables && i.estJouableDsc(this)) {
					temp=pileDsc;
					this.placer(this, i, false, false);
					cartesJouables=this.mainJouabletier2(adv, i,false);
					this.placer(this, temp, false, false); // Restaure l'état de la pile descendante
					
				}
				
				if(cartesJouables && i.estJouableAscAdv(adv)) {
					temp=adv.pileAsc;
					this.placer(adv, i, true, false);
					cartesJouables=this.mainJouabletier2(adv, i,true);
					this.placer(adv, temp, true, false); // Restaure l'état de la pile ascendante de l'adversaire
					
				}
				
				if(cartesJouables && i.estJouableDscAdv(adv)) {
					temp=adv.pileDsc;
					this.placer(adv, i, false, false);
					cartesJouables=this.mainJouabletier2(adv, i,true);
					this.placer(adv, temp, false, false); // Restaure l'état de la pile descendante de l'adversaire
				}

				
			}
		}
		return cartesJouables;
	}

	/*
	[brief] : Fonction utilisée uniquement dans aPerdu(), elle sert à vérifier que le joueur peut bien jouer deux coups
	Joueur [in] : on inclut l'adversaire afin de pouvoir accèder à ses piles et vérifier si le joueur peut jouer dessus
	Carte [in] : la carte déjà vérifiée dans la fonction aPerdu(), on l'inclut ici pour ne pas la vérifier deux fois
	Boolean [in] : true si le coup testé précédent est sur la base adverse, false dans le cas contraire,
	cela permet à la fonction aPerdu() de renvoyer false si jamais les deux seuls coups jouables sont sur les piles adverses
	return : renvoie true si le joueur ne peut jouer aucun coup, false dans le cas contraire
	 */
	private boolean mainJouabletier2(Joueur adv, Carte i, boolean jouerAdv) {
		for(Carte k:hand) {
			if(i==k) {
				continue;
			}
			if(jouerAdv && (k.estJouableAsc(this) || k.estJouableDsc(this)))
				return false;
			if(!jouerAdv && k.estJouable(this,adv)) {
				return false;
			}
		}
		return true;
	}

	/*
	[brief] : pour vérifier si le joueur a gagné
	return : renvoie true si le joueur a gagné, false dans le cas contraire
	 */
	public boolean aGagne() {
		return hand.size()==0 && deck.size()==0;
	}

	/*
	[brief] : pour enlever toutes les cartes de la main du joueur (à des fins de tests uniquement)
	 */
	public void resetHand(){

		hand.clear();

	}
	
}
