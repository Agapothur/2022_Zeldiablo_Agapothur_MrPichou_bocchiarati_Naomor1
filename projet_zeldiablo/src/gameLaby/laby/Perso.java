package gameLaby.laby;


import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * gere un personnage situe en x,y
 */
public class Perso {
    /**
     * si le joueur a l'amulette pour sortir
     */
    private boolean amulette;
    /**
     * position du personnage
     */
    int x, y;

    private Arme arme;
    private Bouclier bouclier;

    private int vie;
    private int force;

    ArrayList<Items> inventaire = new ArrayList<>();

    private String attaque;
    public Color couleur = Color.RED;
    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Perso(int dx, int dy) {
        this.x = dx;
        this.y = dy;
        this.vie = 5;
        this.force = 1;
        this.attaque = "";
        this.arme = null;
    }

    /**
     * setter de l'amulette
     * @param amulette a ou non l'amulette
     */
    public void setAmulette(boolean amulette) {
        this.amulette = amulette;
    }

    /**
     * methode subir dégats
     * @param v vie à enlever
     */
    public void subirdegat(int v){
        if(!possedeBouclier()){
            this.vie -= v;
        }
        else
            this.vie -= redirigerDegats(v);
    }

    public int redirigerDegats(int v){
        int rest = 0;
        if(bouclier.pv - v <= 0) {
            rest = v - bouclier.pv;
            casserBouclier();
        }
        bouclier.pv -= v;
        casserBouclier();
        return rest;
    }

    public void casserBouclier(){
        bouclier = null;
    }
    /**
     * permet de savoir si le personnage est en x,y
     *
     * @param dx position testee
     * @param dy position testee
     * @return true si le personnage est bien en (dx,dy)
     */
    public boolean etrePresent(int dx, int dy) {

        return (this.x == dx && this.y == dy);
    }

    // ############################################
    // GETTER
    // ############################################

    /**
     * @return position x du personnage
     */
    public int getX() {
        // getter
        return this.x;
    }


    /**
     * @return position y du personnage
     */
    public int getY() {
        //getter
        return this.y;
    }

    /**
     * @return si le joueur posséde l'amulette
     */
    public boolean getAmulette() {
        return amulette;
    }

    public int getVie(){
        return this.vie;
    }

    public Color getCouleur(){
        return this.couleur;
    }

    public void setCouleur(Color c) {
        this.couleur = c;
    }

    public int getForce() {
        if(possedeArme()){
            return arme.boostDegats(force);
        }
        return force;
    }

    public boolean possedeArme(){
        return arme != null;
    }

    public boolean possedeBouclier(){
        return bouclier != null;
    }

    public int[][] attaqueDirectionelle(String direction) {
        switch (direction) {
            case Labyrinthe.DROITE ->{this.setAttaque("droite");}
            case Labyrinthe.GAUCHE->{this.setAttaque("gauche");}
            case Labyrinthe.HAUT->{this.setAttaque("haut");}
            case Labyrinthe.BAS->{this.setAttaque("bas");}
            default -> {this.setAttaque("");}
        };
        return switch (direction) {
            case Labyrinthe.DROITE -> new int[][]{{x + 1, y}, {x + 2, y}};
            case Labyrinthe.GAUCHE -> new int[][]{{x - 1, y}, {x - 2, y}};
            case Labyrinthe.HAUT -> new int[][]{{x, y - 1}, {x, y - 2}};
            case Labyrinthe.BAS -> new int[][]{{x, y + 1}, {x, y + 2}};
            default -> new int[][]{{0, 0}, {0, 0}};
        };

    }

    public void setVie(int pv) {
        this.vie = pv;
    }

    public int[] getPos(){
        return new int[]{x,y};
    }

    public void setArme(Arme arme) {
        this.arme = arme;
    }

    public void setBouclier(Bouclier bouclier) {
        this.bouclier = bouclier;
    }

    public String getIsAttaque(){
        return this.attaque;
    }

    public void setAttaque(String attaque) {
        this.attaque = attaque;
    }

    public Arme getArme() {
        return arme;
    }

    public Bouclier getBouclier() {
        return bouclier;
    }
}
