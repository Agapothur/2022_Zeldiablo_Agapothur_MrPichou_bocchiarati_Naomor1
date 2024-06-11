package gameLaby.laby;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * classe labyrinthe. represente un labyrinthe avec
 * <ul> des murs </ul>
 * <ul> un personnage (x,y) </ul>
 * <ul> un monstre (x,y) </ul>
 */
public class
Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X', PJ = 'P', MONSTRE = 'M', AMULETTE = 'A', VIDE = '.', SORTIE = 'S',
            ECHELLE1 = '1', ECHELLE2 = '2', ECHELLE3 = '3', ECHELLE4 = '4',
            ARME = 'W', BOUCLIER = 'B';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut", BAS = "Bas", GAUCHE = "Gauche", DROITE = "Droite";

    // Ajoutez les constantes pour les directions possibles
    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};

    /**
     * attributs du personnage et du monstre
     */
    public Perso pj;
    public Monstre[] monstres;
    public Amulette amulette;
    public Sortie sortie;
    public Echelle[] echelles;
    public ArrayList<Arme> armes;
    public ArrayList<Bouclier> boucliers;

    public String p;
    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;
    private Random random;
    private boolean amulettepresente, sortiepresente;
    int nbMonstre;


    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on diminue colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        return new int[]{x, y};
    }

    /**
     * charge le labyrinthe
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        this.murs = new boolean[nbColonnes][nbLignes];
        this.pj = null;
        this.monstres = null;
        this.amulette = null;
        this.random = new Random();
        this.sortie = null;
        this.echelles = new Echelle[4];
        this.armes = new ArrayList<>();
        this.boucliers = new ArrayList<>();
        this.monstres = new Monstre[0];
        this.nbMonstre = 0;

        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        this.murs[colonne][numeroLigne] = true;
                        break;
                    case VIDE:
                        this.murs[colonne][numeroLigne] = false;
                        break;
                    case PJ:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute PJ
                        this.pj = new Perso(colonne, numeroLigne);
                        break;
                    case MONSTRE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        ajouterMonstre(new Monstre(colonne, numeroLigne));
                        if(nom.equals("projet_zeldiablo/labySimple/e10.txt"))
                            nbMonstre += 1;
                        break;
                    case AMULETTE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute amulette
                        this.amulette = new Amulette(colonne, numeroLigne);
                        this.amulettepresente = true;
                        break;
                    case SORTIE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = true;
                        // ajoute amulette
                        this.sortie = new Sortie(colonne, numeroLigne);
                        this.sortiepresente = true;
                        break;
                    case ECHELLE1:
                        this.murs[colonne][numeroLigne] = false;
                        this.echelles[0] = new Echelle(colonne, numeroLigne);
                        break;
                    case ECHELLE2:
                        this.murs[colonne][numeroLigne] = false;
                        this.echelles[1] = new Echelle(colonne, numeroLigne);
                        break;
                    case ECHELLE3:
                        this.murs[colonne][numeroLigne] = false;
                        this.echelles[2] = new Echelle(colonne, numeroLigne);
                        break;
                    case ECHELLE4:
                        this.murs[colonne][numeroLigne] = false;
                        this.echelles[3] = new Echelle(colonne, numeroLigne);
                        break;
                    case ARME :
                        this.murs[colonne][numeroLigne]=false;
                        this.armes.add(new Arme(colonne, numeroLigne));
                        break;
                    case BOUCLIER:
                        this.murs[colonne][numeroLigne]=false;
                        this.boucliers.add(new Bouclier(colonne, numeroLigne));
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        ;
        // ferme fichier
        bfRead.close();

    }

    private void ajouterMonstre(Monstre monstre) {
        Monstre[] temp = new Monstre[monstres.length+1];
        System.arraycopy(monstres, 0, temp, 0, monstres.length);
        temp[monstres.length] = monstre;
        monstres = temp;
    }

    /**
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs et le monstre
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {

        // case courante
        int[] courante = {this.pj.x, this.pj.y};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // si c'est pas un mur et pas le monstre, on effectue le deplacement
        boolean monstreDevant = false;
        for(Monstre m : monstres){
            if(m.x == suivante[0] && m.y == suivante[1])
                monstreDevant = true;
        }
        if (!this.murs[suivante[0]][suivante[1]] && !monstreDevant) {
            // on met a jour personnage
            this.pj.x = suivante[0];
            this.pj.y = suivante[1];
        }
        //si c'est l'amulette, on la prend
        if(this.amulettepresente) {
            if (this.amulette.x == suivante[0] && this.amulette.y == suivante[1]) {
                this.pj.setAmulette(true);
                if(this.sortie != null)
                    this.murs[this.sortie.x][this.sortie.y] = false;
                //envois de l'amulette dans les abisses
                this.amulette.x = 999;
                this.amulette.y = 999;
            }

        }
        if(this.sortiepresente) {
            if (this.sortie.x == suivante[0] && this.sortie.y == suivante[1]) {
                if (this.pj.getAmulette()) {
                    endScreen(true);
                }
            }
        }
//        String actions = ACTIONS[random.nextInt(ACTIONS.length)];
//        deplacerMonstreAleatoire(actions);

    }
    /**
     * deplace le monstre en fonction de l'action.
     * gere la collision avec les murs et le personnage
     *
     * @param action une des actions possibles
     */
    public void deplacerMonstreAleatoire(Monstre m, String action) {
        if (m.getPv() > 0) {
            boolean attaque = false;
            int[] courante = {m.x, m.y};
            int[] suivante = getSuivant(courante[0], courante[1], action);


            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (m.x - j == this.pj.x && m.y - i == this.pj.y) {
                        this.pj.subirdegat(1);
                        m.setCouleur(Color.RED);
                        attaque = true;
                        if (this.pj.getVie() == 0) {
                            System.out.println("Bro's dead, RIP Bozo");
                            System.exit(1);
                        }
                    }
                }
            }
            if(!(suivante[0] < 0 || suivante[0] > 19 || suivante[1] < 0 || suivante[1] > 13 )) {
                if (!(this.murs[suivante[0]][suivante[1]]) && (this.pj.x != suivante[0] || this.pj.y != suivante[1]) && !attaque) {
                    m.x = suivante[0];
                    m.y = suivante[1];
                }
            }
        }
    }

    public void deplacerMonstreAttire(Monstre m) {

        if(m.getPv() > 0){
            m.setCouleur(Color.PURPLE);
            boolean attaque = false;
            int[] courante = {m.x, m.y};
            String action = "";
            if (courante[0] < this.pj.x) {
                action = ACTIONS[3];
            }
            if (courante[0] > this.pj.x) {
                action = ACTIONS[2];
            }
            if (courante[1] < this.pj.y) {
                if (this.murs[courante[0] + 1][courante[1]] && action == ACTIONS[3] || this.murs[courante[0] - 1][courante[1]] && (action == ACTIONS[2])) {
                    action = ACTIONS[1];
                }
                if (action == "") {
                    action = ACTIONS[1];
                }
            }
            if (courante[1] > this.pj.y) {
                if (this.murs[courante[0] + 1][courante[1]] && action == ACTIONS[3] || this.murs[courante[0] - 1][courante[1]] && (action == ACTIONS[2])) {
                    action = ACTIONS[0];
                }
                if (action == "") {
                    action = ACTIONS[0];
                }
            }

            int[] suivante = getSuivant(courante[0], courante[1], action);


            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (m.x - j == this.pj.x && m.y - i == this.pj.y) {
                        this.pj.subirdegat(1);
                        m.setCouleur(Color.RED);
                        attaque = true;
                        if (this.pj.getVie() == 0) {
                            endScreen(false);
                        }
                    }
                }

                if(!(suivante[0] < 0 || suivante[0] > 19 || suivante[1] < 0 || suivante[1] > 13 )) {
                    if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1]) && !attaque) {
                        m.x = suivante[0];
                        m.y = suivante[1];
                    }
                }
            }
        }
    }

    /**
     * jamais fini
     *
     * @return fin du jeu
     */
    public boolean etreFini() {
        return false;
    }


    // GETTER
    // ##################################
    // ##################################


    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * return mur en (i,j)
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }

    public void monstreEstSurCase(Monstre m, int[] position, int degats) {
        if (m.getX() == position[0] && m.getY() == position[1]) {
            m.subirDegats(degats);
        }
    }

    public void nePlusAfficherMonstre(Monstre m) {
        m.x = 999;
        m.y = 999;
    }

    public void endScreen(boolean win){
        if(win){
            System.out.print("You Win");
        }
        else{
            System.out.print("You Lose");
        }
        System.exit(0);
    }
}
