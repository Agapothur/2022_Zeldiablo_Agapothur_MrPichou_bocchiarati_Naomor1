package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private Labyrinthe labyrinthe;
    private String nom;

    /**
     * Constructeur de la classe LabyJeu.
     *
     * @param n Le nom du fichier contenant la configuration du labyrinthe.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public LabyJeu(String n) throws IOException {
        nom = n;
        this.labyrinthe = new Labyrinthe(nom);
    }

    @Override
    public void update(double deltaTime, Clavier clavier) throws IOException {
        if (clavier.haut) {
            this.labyrinthe.deplacerPerso(Labyrinthe.HAUT);
        }
        if (clavier.bas) {
            this.labyrinthe.deplacerPerso(Labyrinthe.BAS);
        }
        if (clavier.gauche) {
            this.labyrinthe.deplacerPerso(Labyrinthe.GAUCHE);
        }
        if (clavier.droite) {
            this.labyrinthe.deplacerPerso(Labyrinthe.DROITE);
        }

        setLabyrinthe();

    }

    @Override
    public void init() {
//pas besoin
    }

    @Override
    public boolean etreFini() {
        return this.labyrinthe.etreFini();
    }

    /**
     * @return Retourne le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }

    public void setLabyrinthe() throws IOException {
        if((nom.equals("projet_zeldiablo/labySimple/e00.txt") || nom.equals("projet_zeldiablo/labySimple/e00bis.txt") )&& labyrinthe.pj.getX() == 0 && labyrinthe.pj.getY() == 2){
            nom = "projet_zeldiablo/labySimple/e01.txt";
            labyrinthe = new Labyrinthe(nom);
        }

        if(nom.equals("projet_zeldiablo/labySimple/e01.txt") && labyrinthe.pj.getX() == 0  && labyrinthe.pj.getY() == 2){
            nom = "projet_zeldiablo/labySimple/e00bis.txt";
            labyrinthe = new Labyrinthe(nom);
        }
    }

}
