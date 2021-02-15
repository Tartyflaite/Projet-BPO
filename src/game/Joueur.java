package game;

import java.util.ArrayList;
import java.util.Random;

public class Joueur {
	private ArrayList<Integer> deck;
	private ArrayList<Integer> hand;
	private int pileAsc; // pile ascendante
	private int pileDsc; // pile descendante
	private String nom;
	
	
	
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append(this.nom+" ");
		sb.append("^["+pileAsc+"] ");
		sb.append("v["+pileDsc+"] ");
		sb.append("(m"+this.hand.size());
		sb.append("p"+this.deck.size()+")");
		return sb.toString();
	}
	
	public Joueur(String nom) {
		this.deck= new ArrayList<>();
		for(int i=2;i<60;++i) {
			this.deck.add(i);
		}
		this.hand= new ArrayList<>();
		this.pileAsc=1;
		this.pileDsc=60;
		this.nom=nom;
	}
	
	public String piocher(int nb_cartes) {
		int i;
		Random pioche = new Random();
		for(i=0; i<nb_cartes && this.deck.size()>0;++i) {
			int carte= deck.get(pioche.nextInt(this.deck.size()));
			hand.add(carte);
			deck.remove(carte);
		}
		return i +" cartes piochées";

	}
	
	public boolean isInHand(int carte) {
		return hand.contains(carte);
	}
	
	public boolean estJouable(Joueur j,int carte) {
		
		return this.estJouableAsc(carte) || this.estJouableDsc(carte) || this.estJouableAscAdv(j, carte) || this.estJouableDscAdv(j, carte);
	}
	
	public boolean estJouableAsc(int carte) {
		return (carte>pileAsc) || (carte==pileAsc-10);
	}
	
	public boolean estJouableDsc(int carte) {
		return (carte<pileDsc) || (carte==pileDsc+10);
	}
	
	public boolean estJouableAscAdv(Joueur j,int carte) {
		return (carte<j.pileAsc);
	}
	
	public boolean estJouableDscAdv(Joueur j,int carte) {
		return (carte>j.pileDsc);
	}
	
	public void placerAsc(Joueur j, int carte) {
		if(this.equals(j)) {
			this.pileAsc=carte;
			this.hand.remove(carte);
		}
		else {
			j.pileAsc=carte;
			this.hand.remove(carte);
		}
	}
	
	public void placerDsc(Joueur j, int carte) {
		if(this.equals(j)) {
			this.pileDsc=carte;
			this.hand.remove(carte);
		}
		else {
			j.pileDsc=carte;
			this.hand.remove(carte);
		}
	}
	
	public boolean aPerdu(Joueur j) {
		int cartesJouables=0;
		for(int i:hand) {
			if(!this.estJouable(j,i)) {
				++cartesJouables;
			}
		}
		return cartesJouables<2;
	}
	
	public boolean aGagné() {
		return hand.size()==0 && deck.size()==0;
	}
	
}
