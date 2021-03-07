package game;

public class Carte {
	
	private final int valeur; // Valeur de la carte (1-60)

	/*
	[brief] : retourne la valeur de la Carte
	return : renvoie un int
	 */
	public int getValeur() {
		return valeur;
	}

	public Carte(int val) {
		this.valeur=val; // Initialisation de la valeur de la carte
	}

	/*
	[brief] : vérifie si la carte est jouable sur l'entièreté des piles
	Joueur [in] : Joueur 1 pour accèder à ses piles
	Joueur [in] : Joueur 2 pour accèder à ses piles
	return : renvoie true si la carte est jouable, false dans le cas contraire
	 */
	public boolean estJouable(Joueur j1,Joueur j2) {
		
		return this.estJouableAsc(j1) || this.estJouableDsc(j1) || this.estJouableAscAdv(j2) || this.estJouableDscAdv(j2);
	}

	/*
	[brief] : vérifie si la carte est jouable sur la pile ascendante d'un joueur
	Joueur [in] : pour récupérer la pile ascendante du joueur en question
	return : renvoie true si le coup est jouable, false dans le cas contraire
	 */
	public boolean estJouableAsc(Joueur j) {
		return (this.valeur>j.getPileAsc().valeur) || (this.valeur==j.getPileAsc().valeur-10);
	}

	/*
	[brief] : vérifie si la carte est jouable sur la pile descendante d'un joueur
	Joueur [in] : pour récupérer la pile descendante du joueur en question
	return : renvoie true si le coup est jouable, false dans le cas contraire
	 */
	public boolean estJouableDsc(Joueur j) {
		return (this.valeur<j.getPileDsc().valeur) || (this.valeur==j.getPileDsc().valeur+10);
	}

	/*
	[brief] : vérifie si la carte est jouable sur la pile ascendante d'un joueur considéré comme l'adversaire
	c'est à dire que la valeur de la carte doit être plus petite que la dernière carte posée sur cette pile
	Joueur [in] : pour récupérer la pile ascendante du joueur en question
	return : renvoie true si le coup est jouable, false dans le cas contraire
	 */
	public boolean estJouableAscAdv(Joueur j) {
		return (this.valeur<j.getPileAsc().valeur);
	}

	/*
	[brief] : vérifie si la carte est jouable sur la pile descendante d'un joueur considéré comme l'adversaire
	c'est à dire que la valeur de la carte doit être plus grande que la dernière carte posée sur cette pile
	Joueur [in] : pour récupérer la pile descendante du joueur en question
	return : renvoie true si le coup est jouable, false dans le cas contraire
	 */
	public boolean estJouableDscAdv(Joueur j) {
		return (this.valeur>j.getPileDsc().valeur);
	}

	/*
	[brief] : renvoie une chaîne de caractères correspondant à la valeur de la carte
	return : renvoie un String
	 */
	public String toString() {

		String val = "";

		if(valeur < 10){ // Si la valeur de la carte est inférieur à 10 on formate l'affichage pour qu'un 0 soit présent

			val = "0";

		}

		val += String.valueOf(valeur);

		return val;


	}

}
