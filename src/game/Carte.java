package appli;

import java.util.ArrayList;

public class Cartes {

    private ArrayList<String> tab;
    private int nb;

    public Cartes(){

        tab = new ArrayList<String>();
        nb = 0;

    }

    public int getNb(){

        return nb;

    }

    public void setNb(int nb){

        this.nb = nb;

    }

    public void ajouter(String nbr){

        tab.add(nbr);
        nb++;

    }


    public String recuperer(int idx){

        String nbr = tab.get(idx);
        tab.remove(idx);
        nb--;

        return nbr;

    }

    public void afficher() {

        ArrayList<String> listeCroissante  = tab;

        for(int i = 0 ; i < listeCroissante.size() ; i++){

            for(int j = i+1 ; j < listeCroissante.size() ; j++){

                if(Integer.valueOf(listeCroissante.get(j)) < Integer.valueOf(listeCroissante.get(i))){

                    String tmp = listeCroissante.get(i);
                    listeCroissante.set(i, listeCroissante.get(j));
                    listeCroissante.set(j, tmp);

                }

            }

        }

        for(String l : listeCroissante){

            System.out.print(l + " ");


        }


    }



}
