package gameLaby.laby;

import javafx.scene.paint.Color;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private final Labyrinthe[] labyrinthes;
    private String[] noms;
    int current;
    public String last;

    Save saves;
    Labyrinthe laby;

    int kills;

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
        kills = 0;
    }

    @Override
    public void update(double deltaTime, Clavier clavier) throws IOException {
        laby = labyrinthes[current];
        if (clavier.haut) {
            laby.deplacerPerso(Labyrinthe.HAUT);
            laby.pj.setCouleur(Color.RED);
            last = Labyrinthe.HAUT;
        }
        if (clavier.bas) {
            laby.deplacerPerso(Labyrinthe.BAS);
            laby.pj.setCouleur(Color.RED);
            last = Labyrinthe.BAS;
        }
        if (clavier.gauche) {
            laby.deplacerPerso(Labyrinthe.GAUCHE);
            laby.pj.setCouleur(Color.RED);
            last = Labyrinthe.GAUCHE;
        }
        if (clavier.droite) {
            laby.deplacerPerso(Labyrinthe.DROITE);
            laby.pj.setCouleur(Color.RED);
            last = Labyrinthe.DROITE;
        }
        if (clavier.space) {
            int[][] directions = laby.pj.attaqueDirectionelle(last);
            laby.pj.setCouleur(Color.ORANGE);
            for(Monstre m : getLabyrinthe().monstres){
                laby.monstreEstSurCase(m, directions[0], laby.pj.getForce());
                if (!laby.getMur(directions[0][0], directions[0][1]))
                    laby.monstreEstSurCase(m, directions[1], laby.pj.getForce());
            }
        }
        setLabyrinthe();
        debloqueAmulette();

        for(Monstre m : getLabyrinthe().monstres){
            if (m.getPv() <= 0) {
                laby.nePlusAfficherMonstre(m);
                if(current == 10) kills += 1;
            }
        }
        for(int i = 0; i<laby.armes.size(); i++) {
            if(laby.armes.get(i) != null) {
                if (Arrays.equals(laby.armes.get(i).getPos(), laby.pj.getPos()))
                    laby.pj.setArme(laby.armes.get(i));
            }
        }
        int remove = -1;
        for(int i = 0; i<laby.boucliers.size(); i++){
            if(laby.boucliers.get(i) != null){
                if(Arrays.equals(laby.boucliers.get(i).getPos(), laby.pj.getPos())){
                    laby.pj.setBouclier(laby.boucliers.get(i));
                    remove = i;
                    //laby.boucliers.get(i).setPos(999,999);
                }
            }
        }
        if(remove != -1){
            laby.boucliers.remove(remove);
        }

        if(current == 9){
            System.out.println(laby.boucliers.size());
        }
        Random rand = new Random();
        for(Monstre m : laby.monstres) {
            int ceDeplace = rand.nextInt(3);
            if(ceDeplace == 1){
                int typeDeplacement = rand.nextInt(6);
                switch (typeDeplacement) {

                    case 1, 0, 3, 4 -> {
                        laby.deplacerMonstreAttire(m);
                    }
                    case 2 -> {
                        laby.deplacerMonstreAleatoire(m, Labyrinthe.ACTIONS[rand.nextInt(4)]);
                    }
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
        return this.laby.etreFini();
    }

    /**
     * @return Retourne le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthes[current];
    }


    public void debloqueAmulette(){
        if(kills == 7){
            labyrinthes[10].murs[18][4] = false;
            labyrinthes[10].murs[17][4] = false;
            labyrinthes[10].murs[16][4] = false;
        }
    }
    public void setLabyrinthe() throws IOException {
        saves.save(laby);
        int currentE = -1;
        for(int i = 0; i<laby.echelles.length; i++){
            if(laby.echelles[i] != null){
                if (Arrays.equals(laby.pj.getPos(), laby.echelles[i].getPos()))
                    currentE = i;
            }
        }
        if(currentE != -1) {

            switch(last){
                case Labyrinthe.DROITE -> laby.pj.x -= 1;
                case Labyrinthe.GAUCHE -> laby.pj.x += 1;
                case Labyrinthe.HAUT -> laby.pj.y += 1;
                case Labyrinthe.BAS -> laby.pj.y -= 1;
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
            laby = labyrinthes[current];
            saves.restaure(laby);
        }

    }

}
