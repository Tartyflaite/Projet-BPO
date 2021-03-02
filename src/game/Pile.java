package appli;

import java.util.ArrayList;
import java.util.Random;

public class Pile {

    private int ascendante;
    private int descendante;
    private Cartes pioche;
    private Cartes main;
    private String nomJ;

    public Pile(String nomJ){

        ascendante = 1;
        descendante = 60;
        this.nomJ = nomJ;
        pioche = new Cartes();
        initialiserPioche();
        main = new Cartes();
        piocher(6);

    }

    public void afficher(){

        String asc = String.valueOf(ascendante);
        String desc = String.valueOf(descendante);

        if(ascendante < 10)
            asc = "0" + asc;
        if(descendante < 10)
            desc = "0" + desc;
        System.out.println(nomJ + " ^[" + asc + "] v[" + desc + "]" + " (m" + main.getNb() + "p" + pioche.getNb() + ")");

        System.out.print("cartes" + nomJ + " { ");
        main.afficher();
        System.out.println("}");

    }

    private void initialiserPioche() {

        Random rand = new Random();
        ArrayList<String> tab = new ArrayList<String>();

        int nb = 0;
        boolean nouveau;

        while(nb < 58){

            int randint = rand.nextInt(58) + 2;

            if(!tab.isEmpty()){

                nouveau = true;

                for(String y : tab){

                    if(randint == Integer.valueOf(y))
                       nouveau = false;

                }

                if(!nouveau)
                    continue;

            }

            String nbr = String.valueOf(randint);

            if(randint < 10)
                nbr = "0" + nbr;

            tab.add(nbr);

            pioche.ajouter(nbr);

            nb++;



        }



    }

    private void piocher(int nbr){

        for(int i = 0 ; i < nbr ; i++){

            main.ajouter(pioche.recuperer(pioche.getNb() - 1));
            main.setNb(main.getNb() + 1);

        }

    }


}
