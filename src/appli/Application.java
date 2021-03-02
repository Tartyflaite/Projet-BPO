package appli;

import java.util.Scanner;

import game.Joueur;

public class Application {

	public static void main(String[] args) {
		Joueur nord= new Joueur("NORD");
		Joueur sud= new Joueur("SUD ");

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String s;
		System.out.print("> ");
		s = sc.nextLine();
		while (!s.equals("fin")) {
			traitement(s);
			System.out.print("> ");
			s = sc.nextLine();
		}
	}


	private static void traitement(String s) {
		String[] tab = s.split("\\s+");
		for (String mot : tab) {
			if (mot.length()==3 || mot.length()==4) {

			}
		}

	}

}
