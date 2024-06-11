package gameLaby.laby;

import moteurJeu.MoteurJeu;

public class MainLaby {

    public static void main(String[] args) {

        MoteurJeu.setFPS(5);

        try {
            String[] noms = new String[4];
            for(int i = 0; i<noms.length; i++){
                noms[i] = "projet_zeldiablo/labySimple/e0" + i + ".txt";
            }
            LabyJeu jeu = new LabyJeu(noms);

            MoteurJeu.setTaille( jeu.getLabyrinthe().getLength()*LabyDessin.TAILLE,  jeu.getLabyrinthe().getLengthY()*LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
