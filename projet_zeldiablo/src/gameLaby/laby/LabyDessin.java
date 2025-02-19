package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.util.Objects;

/**
 * Classe LabyDessin
 */


public class LabyDessin implements DessinJeu {

    public static final int TAILLE = 50;

    public Canvas c;
    private boolean first = true;
    private final ImagePattern[] ip2 = new ImagePattern[255];



    private int nbframe = 0;


    /**
     *
     * @param jeu le jeu
     * @param canvas canvas représentant l'état du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas, SpriteLoader loader) {

        this.c = canvas;
        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        //tout les sols


        // fond
        if(first) {
            ImagePattern[] ip = new ImagePattern[7];

            for(int i = 0; i<7;i++){
                ip[i] = loader.getImage("floor_"+(i+1)+".png");
            }

            for (int i = 0; i < 255; i++) {
                ip2[i] = (ip[(int)(Math.random()*7)]);
            }
            this.first = false;
        }
        int u = 0;
        for(int i =0;i<14;i++){
            for(int y=0;y<20;y++){
                gc.setFill(ip2[u]);
                gc.fillRect(y*TAILLE,i*TAILLE, TAILLE, TAILLE);
                u+= 1;
                if (u == 254){
                    u = 0;
                }
            }
        }
//        gc.setFill(ip[2]);
//        gc.fillRect(0, 0, canvas.getWidth(),canvas.getHeight() );


        if(nbframe == 3){
            nbframe = 0;
        }else nbframe++;




        // dessin Labyrinthe
        gc.setFill(Color.rgb(30, 30, 30));
        Labyrinthe laby = labyrinthe.getLabyrinthe();
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.setFill(loader.getImage("wall_right.png"));
                    gc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        //sortie
        if (labyrinthe.getLabyrinthe().sortie != null) {
            double sortiex = labyrinthe.getLabyrinthe().sortie.getX();
            double sortiey = labyrinthe.getLabyrinthe().sortie.getY();
            //gc.setFill(Color.rgb(86,72,23));
            if(!labyrinthe.getLabyrinthe().pj.getAmulette()) {
                gc.setFill(loader.getImage("floor_ladder.png"));
                gc.fillRect(sortiex * TAILLE, sortiey * TAILLE, TAILLE, TAILLE);
                gc.setFill(loader.getImage("doors_leaf_closed.png"));
                gc.fillRect(sortiex * TAILLE, sortiey * TAILLE+TAILLE, TAILLE, TAILLE);

            }else {
                gc.setFill(loader.getImage("floor_ladder.png"));
                gc.fillRect(sortiex * TAILLE, sortiey * TAILLE, TAILLE, TAILLE);
                gc.setFill(loader.getImage("doors_leaf_open.png"));
                gc.fillRect(sortiex * TAILLE, sortiey * TAILLE+TAILLE, TAILLE, TAILLE);
            }
        }

        //arme
        if(!labyrinthe.getLabyrinthe().pj.possedeArme()) {
            if (!labyrinthe.getLabyrinthe().armes.isEmpty()) {
                double armex = labyrinthe.getLabyrinthe().armes.get(0).getX();
                double armey = labyrinthe.getLabyrinthe().armes.get(0).getY();
                gc.setFill(loader.getImage("weapon_red_gem_sword.png"));
                gc.fillRect(armex * TAILLE+((double) TAILLE /4), armey * TAILLE, (double) TAILLE /2, TAILLE);
            }
        }

        //vie
        for(int i = 0; i<labyrinthe.getLabyrinthe().pj.getVie();i++){
            gc.setFill(loader.getImage("ui_heart_full.png"));
            gc.fillRect((i * TAILLE) + ((double) TAILLE / 4), (double) TAILLE / 4, (double) TAILLE /2, (double) TAILLE /2);
        }

        //bouclier
        if(labyrinthe.getLabyrinthe().pj.possedeBouclier()){
            gc.setFill(loader.getImage("shield.png"));
            gc.fillRect((18 * TAILLE) + ((double) TAILLE / 4), (double) TAILLE / 4, (double) TAILLE /2, (double) TAILLE /2);
        }
        if(!labyrinthe.getLabyrinthe().pj.possedeBouclier()) {
            if (!labyrinthe.getLabyrinthe().boucliers.isEmpty()) {
                double bouclierx = labyrinthe.getLabyrinthe().boucliers.get(0).getX();
                double boucliery = labyrinthe.getLabyrinthe().boucliers.get(0).getY();
                gc.setFill(loader.getImage("shield.png"));
                gc.fillRect(bouclierx * TAILLE, boucliery * TAILLE, TAILLE, TAILLE);
            }
        }

        // perso
        double persox = labyrinthe.getLabyrinthe().pj.getX();
        double persoy = labyrinthe.getLabyrinthe().pj.getY();
        if (labyrinthe.getLabyrinthe().pj.getIsAttaque().equals("gauche")) {
            switch (nbframe) {
                case 0 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f0_left.png"));
                }
                case 1 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f1_left.png"));
                }
                case 2 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f2_left.png"));
                }
                case 3 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f3_left.png"));
                }
                default -> {
                    gc.setFill(labyrinthe.getLabyrinthe().pj.getCouleur());
                }
            }
        } else {
            switch (nbframe) {
                case 0 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f0.png"));
                }
                case 1 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f1.png"));
                }
                case 2 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f2.png"));
                }
                case 3 -> {
                    gc.setFill(loader.getImage("knight_m_idle_anim_f3.png"));
                }
                default -> {
                    gc.setFill(labyrinthe.getLabyrinthe().pj.getCouleur());
                }
            }
        }
        gc.fillRect(persox * TAILLE, persoy * TAILLE, TAILLE, TAILLE);
        if(labyrinthe.getLabyrinthe().pj.possedeArme()) {
            gc.setFill(loader.getImage("weapon_red_gem_sword.png"));
            gc.fillRect((19 * TAILLE) + ((double) TAILLE / 4), (double) TAILLE / 4, (double) TAILLE / 2, (double) TAILLE);
        }else{
            gc.setFill(loader.getImage("weapon_rusty_sword.png"));
            gc.fillRect((19 * TAILLE) + ((double) TAILLE / 4), (double) TAILLE / 4, (double) TAILLE / 2, (double) TAILLE);
        }
        if(!Objects.equals(labyrinthe.getLabyrinthe().pj.getIsAttaque(), "")){
            if(labyrinthe.getLabyrinthe().pj.possedeArme()) {
                switch (labyrinthe.getLabyrinthe().pj.getIsAttaque()) {
                    case "droite" -> {
                        gc.setFill(loader.getImage("weapon_red_gem_sword_right.png"));
                        gc.fillRect(persox * TAILLE + TAILLE, persoy * TAILLE + ((double) TAILLE / 4), TAILLE, (double) TAILLE / 2);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "gauche" -> {
                        gc.setFill(loader.getImage("weapon_red_gem_sword_left.png"));
                        gc.fillRect(persox * TAILLE - TAILLE, persoy * TAILLE + ((double) TAILLE / 4), TAILLE, (double) TAILLE / 2);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "haut" -> {
                        gc.setFill(loader.getImage("weapon_red_gem_sword.png"));
                        gc.fillRect(persox * TAILLE + ((double) TAILLE / 4), persoy * TAILLE - TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "bas" -> {
                        gc.setFill(loader.getImage("weapon_red_gem_sword_down.png"));
                        gc.fillRect(persox * TAILLE + ((double) TAILLE / 4), persoy * TAILLE + TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    default -> {
                        gc.setFill(loader.getImage("weapon_red_gem_sword.png"));
                        gc.fillRect(persox * TAILLE, persoy * TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }
                }
                ;
            }else {
                switch (labyrinthe.getLabyrinthe().pj.getIsAttaque()) {
                    case "droite" -> {
                        gc.setFill(loader.getImage("weapon_rusty_sword_right.png"));
                        gc.fillRect(persox * TAILLE + TAILLE, persoy * TAILLE + ((double) TAILLE / 4), TAILLE, (double) TAILLE / 2);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "gauche" -> {
                        gc.setFill(loader.getImage("weapon_rusty_sword_left.png"));
                        gc.fillRect(persox * TAILLE - TAILLE, persoy * TAILLE + ((double) TAILLE / 4), TAILLE, (double) TAILLE / 2);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "haut" -> {
                        gc.setFill(loader.getImage("weapon_rusty_sword.png"));
                        gc.fillRect(persox * TAILLE + ((double) TAILLE / 4), persoy * TAILLE - TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    case "bas" -> {
                        gc.setFill(loader.getImage("weapon_rusty_sword_down.png"));
                        gc.fillRect(persox * TAILLE + ((double) TAILLE / 4), persoy * TAILLE + TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }

                    default -> {
                        gc.setFill(loader.getImage("weapon_rusty_sword.png"));
                        gc.fillRect(persox * TAILLE, persoy * TAILLE, (double) TAILLE / 2, TAILLE);
                        labyrinthe.getLabyrinthe().pj.setAttaque("");
                    }
                }
                ;
            }

        }
        // monstre
        for(Monstre m : labyrinthe.getLabyrinthe().monstres){
            if (m != null) {
                double monstrex = m.getX();
                double monstrey = m.getY();
                switch (nbframe) {
                    case 0 -> {
                        gc.setFill(loader.getImage("big_demon_idle_anim_f0.png"));
                    }
                    case 1 -> {
                        gc.setFill(loader.getImage("big_demon_idle_anim_f1.png"));
                    }
                    case 2 -> {
                        gc.setFill(loader.getImage("big_demon_idle_anim_f2.png"));
                    }
                    case 3 -> {
                        gc.setFill(loader.getImage("big_demon_idle_anim_f3.png"));
                    }
                }

                gc.fillRect(monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
            }
        }
        //amulette
        if (labyrinthe.getLabyrinthe().amulette != null) {
            if (!labyrinthe.getLabyrinthe().pj.getAmulette()) {
                double amulettex = labyrinthe.getLabyrinthe().amulette.getX();
                double amulettey = labyrinthe.getLabyrinthe().amulette.getY();
                gc.setFill(loader.getImage("skull.png"));
                gc.fillOval(amulettex * TAILLE, amulettey * TAILLE, TAILLE, TAILLE);
            }
        }
    }
}
