package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private Labyrinthe[] labyrinthes;
    private String[] noms;
    int current;

    /**
     * Constructeur de la classe LabyJeu.
     *
     * @param n Le nom du fichier contenant la configuration du labyrinthe.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public LabyJeu(String[] n) throws IOException {
        this.noms = n;
        this.labyrinthes = new Labyrinthe[this.noms.length];
        for(int i = 0; i< this.noms.length; i++){
            this.labyrinthes[i] = new Labyrinthe(this.noms[i]);
        }
        this.current = 0;
    }

    @Override
    public void update(double deltaTime, Clavier clavier) throws IOException {
        if (clavier.haut) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.HAUT);
        }
        if (clavier.bas) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.BAS);
        }
        if (clavier.gauche) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.GAUCHE);
        }
        if (clavier.droite) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.DROITE);
        }

        setLabyrinthe();

    }

    @Override
    public void init() {
//pas besoin
    }

    @Override
    public boolean etreFini() {
        return this.labyrinthes[current].etreFini();
    }

    /**
     * @return Retourne le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthes[current];
    }

    public void setLabyrinthe() throws IOException {
        if((noms[current] == noms[0])&& labyrinthes[current].pj.getX() == 15 && labyrinthes[current].pj.getY() == 13){
            labyrinthes[current].pj.y -= 1;
            current = 1;
        }

        if(noms[current] == noms[1] && labyrinthes[current].pj.getX() == 15  && labyrinthes[current].pj.getY() == 13){
            labyrinthes[current].pj.y -= 1;
            current = 0;
        }

        if(noms[current] == noms[1] && labyrinthes[current].pj.getX() == 0  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x += 1;
            current = 2;
        }

        if(noms[current] == noms[2] && labyrinthes[current].pj.getX() == 0  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x += 1;
            current = 1;
        }
    }

}
