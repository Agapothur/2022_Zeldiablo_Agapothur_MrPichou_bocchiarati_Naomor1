package gameLaby.laby;

import moteurJeu.MoteurJeu;

public class MainLaby {

    public static void main(String[] args) {

        MoteurJeu.setFPS(15);

        try {

            String numLaby = "01";
            LabyJeu jeu = new LabyJeu("projet_zeldiablo/labySimple/e" + numLaby + ".txt");
            MoteurJeu.setTaille( jeu.getLabyrinthe().getLength()*LabyDessin.TAILLE,  jeu.getLabyrinthe().getLengthY()*LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
