package game;
public class Carte {
	
	private int valeur;
	
	
	public int getValeur() {
		return valeur;
	}
	
	public Carte(int val) {
		this.valeur=val;
	}
	
	public boolean estJouable(Joueur j,Joueur adv) {
		
		return this.estJouableAsc(j) || this.estJouableDsc(j) || this.estJouableAscAdv(adv) || this.estJouableDscAdv(adv);
	}
	
	
	
	public boolean estJouableAsc(Joueur j) {
		return (this.valeur>j.getPileAsc().valeur) || (this.valeur==j.getPileAsc().valeur-10);
	}
	
	public boolean estJouableDsc(Joueur j) {
		return (this.valeur<j.getPileDsc().valeur) || (this.valeur==j.getPileDsc().valeur+10);
	}
	
	public boolean estJouableAscAdv(Joueur j) {
		return (this.valeur<j.getPileAsc().valeur);
	}
	
	public boolean estJouableDscAdv(Joueur j) {
		return (this.valeur>j.getPileDsc().valeur);
	}
}
