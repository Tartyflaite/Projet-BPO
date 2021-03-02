package game;

import java.util.ArrayList;
import java.util.Random;

public class Joueur {
	private ArrayList<Carte> deck;
	private ArrayList<Carte> hand;
	private Carte pileAsc; // pile ascendante
	private Carte pileDsc; // pile descendante
	private String nom;
	
	
	public Joueur(String nom) {
		this.deck= new ArrayList<>();
		for(int i=2;i<60;++i) {
			this.deck.add(new Carte(i));
		}
		this.hand= new ArrayList<>();
		this.pileAsc= new Carte(1);
		this.pileDsc= new Carte(60);
		this.nom=nom;
	}
	
	public Carte getPileAsc() {
		return pileAsc;
	}
	
	public Carte getPileDsc() {
		return pileDsc;
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append(this.nom+" ");
		sb.append("^["+pileAsc.getValeur()+"] ");
		sb.append("v["+pileDsc.getValeur()+"] ");
		sb.append("(m"+this.hand.size());
		sb.append("p"+this.deck.size()+")");
		return sb.toString();
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
			sb.append(c.toString()+" ");
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
		return i +" cartes piochées";

	}
	
	public boolean isInHand(Carte carte) {
		return hand.contains(carte);
	}
	

	
	public void placer(Joueur j, Carte carte, boolean asc) {

		if(this.equals(j)) {


			for(int i = 0 ; i < this.hand.size() ; i++){

				if(this.hand.get(i).getValeur() == carte.getValeur()) {
					if(asc)
						this.pileAsc = carte;
					else
						this.pileDsc = carte;
					this.hand.remove(i);
				}

			}


		}
		else {

			for(int i = 0 ; i < this.hand.size() ; i++){

				if(this.hand.get(i).getValeur() == carte.getValeur()) {
					if(asc)
						j.pileAsc = carte;
					else
						j.pileDsc = carte;
					this.hand.remove(i);
				}

			}


		}
	}

	
	public boolean aPerdu(Joueur j) {
		boolean cartesJouables=true;
		Carte temp;
		for(Carte i:hand) {
			if(i.estJouable(this,j)) {
				if(cartesJouables && i.estJouableAsc(this)) {
					temp=pileAsc;
					this.placer(this, i, true);
					cartesJouables=this.mainJouabletier2(j, i);
					this.placer(this, temp, true);
				}
				if(cartesJouables && i.estJouableDsc(this)) {
					temp=pileDsc;
					this.placer(this, i, false);
					cartesJouables=this.mainJouabletier2(j, i);
					this.placer(this, temp, false);
					
				}
				if(cartesJouables && i.estJouableAscAdv(j)) {
					temp=j.pileAsc;
					this.placer(j, i, true);
					cartesJouables=this.mainJouabletier2(j, i);
					this.placer(j, temp, true);
					
				}
				if(cartesJouables && i.estJouableDscAdv(j)) {
					temp=j.pileDsc;
					this.placer(j, i, true);
					cartesJouables=this.mainJouabletier2(j, i);
					this.placer(j, temp, true);
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
	
}
