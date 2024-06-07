package gameLaby.laby;

import javafx.scene.paint.Color;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.Arrays;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private Labyrinthe[] labyrinthes;
    private String[] noms;
    int current;
    String last;

    Save saves;

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
        this.saves = new Save();
    }

    @Override
    public void update(double deltaTime, Clavier clavier) throws IOException {
        if (clavier.haut) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.HAUT);
            this.labyrinthes[current].pj.setCouleur(Color.RED);
            last = Labyrinthe.HAUT;
        }
        if (clavier.bas) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.BAS);
            this.labyrinthes[current].pj.setCouleur(Color.RED);
            last = Labyrinthe.BAS;
        }
        if (clavier.gauche) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.GAUCHE);
            this.labyrinthes[current].pj.setCouleur(Color.RED);
            last = Labyrinthe.GAUCHE;
        }
        if (clavier.droite) {
            this.labyrinthes[current].deplacerPerso(Labyrinthe.DROITE);
            this.labyrinthes[current].pj.setCouleur(Color.RED);
            last = Labyrinthe.DROITE;
        }
        if (clavier.space) {
            int[][] directions = labyrinthes[current].pj.attaqueDirectionelle(last);
            this.labyrinthes[current].pj.setCouleur(Color.ORANGE);
            labyrinthes[current].monstreEstSurCase(directions[0], labyrinthes[current].pj.getForce());
            if(!labyrinthes[current].getMur(directions[0][0], directions[0][1]))
                labyrinthes[current].monstreEstSurCase(directions[1], labyrinthes[current].pj.getForce());
        }
        setLabyrinthe();

        if(labyrinthes[current].monstre.getPv() == 0){
            labyrinthes[current].nePlusAfficherMonstre();
        }

        if(Arrays.equals(labyrinthes[current].armes[0].getPos(), labyrinthes[current].pj.getPos()))
            labyrinthes[current].pj.setArme(labyrinthes[current].armes[0]);

        System.out.println(labyrinthes[current].pj.possedeArme());

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
        if((noms[current] == noms[0])&& Arrays.equals(labyrinthes[current].pj.getPos(), labyrinthes[current].echelles[0].getPos())){
            labyrinthes[current].pj.y -= 1;
            saves.save(labyrinthes[current]);
            current = 1;
            saves.restaure(labyrinthes[current]);
        }

        if(noms[current] == noms[1] && labyrinthes[current].pj.getX() == 15  && labyrinthes[current].pj.getY() == 13){
            labyrinthes[current].pj.y -= 1;
            saves.save(labyrinthes[current]);
            current = 0;
            saves.restaure(labyrinthes[current]);

        }

        if(noms[current] == noms[1] && labyrinthes[current].pj.getX() == 0  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x += 1;
            saves.save(labyrinthes[current]);
            current = 2;
            saves.restaure(labyrinthes[current]);
        }

        if(noms[current] == noms[2] && labyrinthes[current].pj.getX() == 0  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x += 1;
            saves.save(labyrinthes[current]);
            current = 1;
            saves.restaure(labyrinthes[current]);
        }

        if(noms[current] == noms[2] && labyrinthes[current].pj.getX() == 19  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x -= 1;
            saves.save(labyrinthes[current]);
            current = 3;
            saves.restaure(labyrinthes[current]);
        }

        if(noms[current] == noms[3] && labyrinthes[current].pj.getX() == 0  && labyrinthes[current].pj.getY() == 2){
            labyrinthes[current].pj.x += 1;
            saves.save(labyrinthes[current]);
            current = 2;
            saves.restaure(labyrinthes[current]);
        }
    }

}
