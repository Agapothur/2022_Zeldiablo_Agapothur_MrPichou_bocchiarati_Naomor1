package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

/**
 * Classe LabyDessin
 */
public class LabyDessin implements DessinJeu {

    public static final int TAILLE = 50;

    public Canvas c;


    /**
     *
     * @param jeu le jeu
     * @param canvas canvas représentant l'état du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        this.c = canvas;
        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // fond
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // dessin Labyrinthe
        gc.setFill(Color.rgb(30, 30, 30));
        Labyrinthe laby = labyrinthe.getLabyrinthe();
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        //sortie
        if (labyrinthe.getLabyrinthe().sortie != null) {
            double sortiex = labyrinthe.getLabyrinthe().sortie.getX();
            double sortiey = labyrinthe.getLabyrinthe().sortie.getY();
            gc.setFill(Color.rgb(86,72,23));
            gc.fillOval(sortiex * TAILLE, sortiey * TAILLE, TAILLE, TAILLE);
        }

        // perso
        double persox = labyrinthe.getLabyrinthe().pj.getX();
        double persoy = labyrinthe.getLabyrinthe().pj.getY();
        gc.setFill(labyrinthe.getLabyrinthe().pj.getCouleur());
        gc.fillOval(persox * TAILLE, persoy * TAILLE, TAILLE, TAILLE);

        // monstre
        if (labyrinthe.getLabyrinthe().monstre != null) {
            double monstrex = labyrinthe.getLabyrinthe().monstre.getX();
            double monstrey = labyrinthe.getLabyrinthe().monstre.getY();
            gc.setFill(Color.rgb(127,0,255));
            gc.fillOval(monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
        }
        //amulette
        if (labyrinthe.getLabyrinthe().amulette != null) {
            if (!labyrinthe.getLabyrinthe().pj.getAmulette()) {
                double amulettex = labyrinthe.getLabyrinthe().amulette.getX();
                double amulettey = labyrinthe.getLabyrinthe().amulette.getY();
                gc.setFill(Color.rgb(255, 190, 0));
                //Image img = new Image("projet_zeldiablo/image/bob.jpg");
                //gc.setFill(new ImagePattern(img));
                gc.fillOval(amulettex * TAILLE, amulettey * TAILLE, TAILLE, TAILLE);
            }
        }
    }

//    public void Hit(Jeu jeu, Canvas canvas) {
//        LabyJeu labyrinthe = (LabyJeu) jeu;
//
//        final GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        // fond
//        gc.setFill(Color.WHITE);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        // dessin Labyrinthe
//        gc.setFill(Color.rgb(30, 30, 30));
//        Labyrinthe laby = labyrinthe.getLabyrinthe();
//        for (int j = 0; j < laby.getLength(); j++) {
//            for (int i = 0; i < laby.getLengthY(); i++) {
//                if (laby.getMur(j, i)) {
//                    gc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
//                }
//            }
//        }
//
//        //sortie
//        if (labyrinthe.getLabyrinthe().sortie != null) {
//            double sortiex = labyrinthe.getLabyrinthe().sortie.getX();
//            double sortiey = labyrinthe.getLabyrinthe().sortie.getY();
//            gc.setFill(Color.rgb(86,72,23));
//            gc.fillOval(sortiex * TAILLE, sortiey * TAILLE, TAILLE, TAILLE);
//        }
//
//        // perso
//        double persox = labyrinthe.getLabyrinthe().pj.getX();
//        double persoy = labyrinthe.getLabyrinthe().pj.getY();
//        gc.setFill(Color.YELLOW);
//        gc.fillOval(persox * TAILLE, persoy * TAILLE, TAILLE, TAILLE);
//
//        // monstre
//        if (labyrinthe.getLabyrinthe().monstre != null) {
//            double monstrex = labyrinthe.getLabyrinthe().monstre.getX();
//            double monstrey = labyrinthe.getLabyrinthe().monstre.getY();
//            gc.setFill(Color.rgb(127,0,255));
//            gc.fillOval(monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
//        }
//        //amulette
//        if (labyrinthe.getLabyrinthe().amulette != null) {
//            if (!labyrinthe.getLabyrinthe().pj.getAmulette()) {
//                double amulettex = labyrinthe.getLabyrinthe().amulette.getX();
//                double amulettey = labyrinthe.getLabyrinthe().amulette.getY();
//                gc.setFill(Color.rgb(255, 190, 0));
//                //Image img = new Image("projet_zeldiablo/image/bob.jpg");
//                //gc.setFill(new ImagePattern(img));
//                gc.fillOval(amulettex * TAILLE, amulettey * TAILLE, TAILLE, TAILLE);
//            }
//        }
//    }


}
