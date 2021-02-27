package appli;

import java.util.Scanner;

import game.Joueur;

public class Application {

	public static void main(String[] args) {
		Joueur nord= new Joueur("NORD");
		Joueur sud= new Joueur("SUD");
		
		
//			for (int i = 1; i < 20; ++i)
//			System.out.println(String.format("%02d", i));
		
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
