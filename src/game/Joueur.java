package game;

import java.util.ArrayList;
import java.util.Random;

public class Joueur {
	private final ArrayList<Carte> deck;
	private final ArrayList<Carte> hand;
	private Carte pileAsc; // pile ascendante
	private Carte pileDsc; // pile descendante
	private final String nom;
	
	
	public Joueur(String nom) {
		this.deck= new ArrayList<>();
		for(int i=2;i<4;++i) {
			this.deck.add(new Carte(i));
		}
		this.hand= new ArrayList<>();
		this.pileAsc= new Carte(4);
		this.pileDsc= new Carte(2);
		this.nom=nom;
	}

	public String getNom(){

		return nom;

	}

	public Carte getPileAsc() {

		return pileAsc;
	}
	
	public Carte getPileDsc() {
		return pileDsc;
	}
	
	public ArrayList<Carte> getHand(){
		return hand;
	}
	
	public ArrayList<Carte> getDeck(){
		return deck;
	}
	
	public void ajouterCarte(Carte carte) {
		this.hand.add(carte);
	}
	
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
	
	public String toStringHand() {

		ArrayList<Carte> listeCroissante  = hand;

		for(int i = 0 ; i < listeCroissante.size() ; i++){

			for(int j = i+1 ; j < listeCroissante.size() ; j++){

				if(listeCroissante.get(j).getValeur() < listeCroissante.get(i).getValeur()){

					Carte tmp = new Carte(listeCroissante.get(i).getValeur());
					listeCroissante.set(i, listeCroissante.get(j));
					listeCroissante.set(j, tmp);

				}

			}

		}

		StringBuilder sb= new StringBuilder();
		sb.append("cartes ");
		sb.append(this.nom);
		sb.append(" { ");
		for(Carte c : listeCroissante) {
			sb.append(c.toString()).append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	
	public String piocher(int nb_cartes) {
		int i;
		Random pioche = new Random();
		for(i=0; i<nb_cartes && this.deck.size()>0;++i) {
			Carte carte= deck.get(pioche.nextInt(this.deck.size()));
			hand.add(carte);
			deck.remove(carte);
		}

		return i +" cartes piochees";

	}
	
	public boolean isInHand(Carte carte) {
		return hand.contains(carte);
	}

	public void placer(Joueur j, Carte carte, boolean asc){

		for(int i = 0 ; i < this.hand.size() ; i++){

			if(this.hand.get(i).getValeur() == carte.getValeur()){

				placerTest(j,carte,asc);
				this.hand.remove(i);
				break;

			}

		}

	}
	
	public void placerTest(Joueur j, Carte carte, boolean asc) {


		if(this.equals(j)) {

			if(asc)
				this.pileAsc = carte;
			else
				this.pileDsc = carte;


		}
		else {

			if(asc)
				j.pileAsc = carte;
			else
				j.pileDsc = carte;


		}

	}

	
	public boolean aPerdu(Joueur adv) {
		boolean cartesJouables=true;
		for(Carte i:hand) {
			if(cartesJouables && i.estJouable(this,adv)) {

				Carte temp;

				if(cartesJouables && i.estJouableAsc(this)) {
					temp=pileAsc;
					this.placerTest(this, i, true);
					cartesJouables=this.mainJouabletier2(adv, i);
					this.placerTest(this, temp, true);
				}


				if(cartesJouables && i.estJouableDsc(this)) {
					temp=pileDsc;
					this.placerTest(this, i, false);
					cartesJouables=this.mainJouabletier2(adv, i);
					this.placerTest(this, temp, false);
					
				}
				
				if(cartesJouables && i.estJouableAscAdv(adv)) {
					temp=adv.pileAsc;
					this.placerTest(adv, i, true);
					cartesJouables=this.mainJouabletier2(adv, i);
					this.placerTest(adv, temp, true);
					
				}
				
				if(cartesJouables && i.estJouableDscAdv(adv)) {
					temp=adv.pileDsc;
					this.placerTest(adv, i, false);
					cartesJouables=this.mainJouabletier2(adv, i);
					this.placerTest(adv, temp, false);
				}

				
			}
		}
		return cartesJouables;
	}

	
	private boolean mainJouabletier2(Joueur j, Carte i) {
		for(Carte k:hand) {
			if(i==k) {
				continue;
			}
			if(k.estJouable(this,j)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean aGagne() {
		return hand.size()==0 && deck.size()==0;
	}

	public void resetHand(){

		hand.clear();

	}
	
}
