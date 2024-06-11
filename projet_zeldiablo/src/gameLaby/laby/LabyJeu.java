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
    public String last;

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
            for(Monstre m : getLabyrinthe().monstres){
                labyrinthes[current].monstreEstSurCase(m, directions[0], labyrinthes[current].pj.getForce());
                if (!labyrinthes[current].getMur(directions[0][0], directions[0][1]))
                    labyrinthes[current].monstreEstSurCase(m, directions[1], labyrinthes[current].pj.getForce());
            }
        }
        setLabyrinthe();
        for(Monstre m : getLabyrinthe().monstres){
            if (m.getPv() <= 0) {
                labyrinthes[current].nePlusAfficherMonstre(m);
            }
        }
        for(int i = 0; i<labyrinthes[current].armes.length; i++) {
            if(labyrinthes[current].armes[i] != null) {
                if (Arrays.equals(labyrinthes[current].armes[i].getPos(), labyrinthes[current].pj.getPos()))
                    labyrinthes[current].pj.setArme(labyrinthes[current].armes[i]);
            }
            if(labyrinthes[current].boucliers[i] != null) {
                if (Arrays.equals(labyrinthes[current].boucliers[i].getPos(), labyrinthes[current].pj.getPos())) {
                    labyrinthes[current].pj.setBouclier(labyrinthes[current].boucliers[i]);
                    //envois de l'amulette dans les abisses
                    labyrinthes[current].boucliers[i].x = 999;
                    labyrinthes[current].boucliers[i].y = 999;
                }
            }
        }
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
        int currentE = -1;
        for(int i = 0; i<labyrinthes[current].echelles.length; i++){
            if(labyrinthes[current].echelles[i] != null){
                if (Arrays.equals(labyrinthes[current].pj.getPos(), labyrinthes[current].echelles[i].getPos()))
                    currentE = i;
            }
        }
        if(currentE != -1) {
            saves.save(labyrinthes[current]);
            switch(last){
                case Labyrinthe.DROITE -> labyrinthes[current].pj.x -= 1;
                case Labyrinthe.GAUCHE -> labyrinthes[current].pj.x += 1;
                case Labyrinthe.HAUT -> labyrinthes[current].pj.y += 1;
                case Labyrinthe.BAS -> labyrinthes[current].pj.y -= 1;
            }

            switch (current) {
                case 0:
                    if (currentE == 0) {
                        current = 1;
                    }
                    break;
                case 1:
                    switch (currentE) {
                        case 0:
                            current = 0;
                            break;
                        case 1 :
                            current = 2;
                    }
                    break;
                case 2:
                    switch (currentE) {
                        case 0:
                            current = 1;
                            break;
                        case 1 :
                            current = 3;

                    }
                    break;
                case 3:
                    switch (currentE) {
                        case 0:
                            current = 2;
                            break;
                        case 1 :
                            current = 4;
                            break;
                        case 2:
                            current = 5;
                            break;
                        case 3:
                            current = 6;

                    }
                    break;
                case 4:
                    switch (currentE) {
                        case 0:
                            current = 3;
                            break;
                        case 1 :
                            current = 8;
                            break;
                        case 2:
                            current = 7;
                            break;
                    }
                    break;
                case 5:
                    switch (currentE) {
                        case 0:
                            current = 3;
                            break;
                        case 1 :
                            current = 10;
                            break;
                    }
                    break;
                case 6:
                    switch (currentE) {
                        case 0:
                            current = 3;
                            break;
                        case 1 :
                            current = 9;
                            break;
                    }
                    break;
                case 7, 8:
                    if (currentE == 0) {
                        current = 4;
                    }
                    break;
                case 9:
                    if (currentE == 0) {
                        current = 6;
                    }
                    break;
                case 10:
                    if (currentE == 0) {
                        current = 5;
                    }
                    break;

            }
            saves.restaure(labyrinthes[current]);
        }
    }

}
