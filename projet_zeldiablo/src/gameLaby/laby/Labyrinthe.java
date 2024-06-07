package gameLaby.laby;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char MONSTRE = 'M';
    public static final char AMULETTE = 'A';
    public static final char VIDE = '.';
    public static final char SORTIE = 'S';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "Haut";
    public static final String BAS = "Bas";
    public static final String GAUCHE = "Gauche";
    public static final String DROITE = "Droite";

    // Ajoutez les constantes pour les directions possibles
    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};


    /**
     * attributs du personnage et du monstre
     */
    public Perso pj;
    public Monstre monstre;

    public Amulette amulette;

    public Sortie sortie;

    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;

    private Random random;

    private boolean amulettepresente;
    private boolean sortiepresente;
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
        int[] res = {x, y};
        return res;
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
        this.monstre = null;
        this.amulette = null;
        this.random = new Random();
        this.sortie = null;

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
                        this.monstre = new Monstre(colonne, numeroLigne);
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
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute amulette
                        this.sortie = new Sortie(colonne, numeroLigne);
                        this.sortiepresente = true;
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        // ferme fichier
        bfRead.close();

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
        if (!this.murs[suivante[0]][suivante[1]] && (this.monstre.x != suivante[0] || this.monstre.y != suivante[1])) {
            // on met a jour personnage
            this.pj.x = suivante[0];
            this.pj.y = suivante[1];
        }
        //si c'est l'amulette, on la prend
        if(this.amulettepresente) {
            if (this.amulette.x == suivante[0] && this.amulette.y == suivante[1]) {
                this.pj.setAmulette(true);
                //envois de l'amulette dans les abisses
                this.amulette.x = 999;
                this.amulette.y = 999;
            }

        }
        if(this.sortiepresente) {
            if (this.sortie.x == suivante[0] && this.sortie.y == suivante[1]) {
                if (this.pj.getAmulette()) {
                    System.out.println("WIIIINNNNNNNNN");
                    System.exit(0);
                }
            }
        }
//        String actions = ACTIONS[random.nextInt(ACTIONS.length)];
//        deplacerMonstreAleatoire(actions);
        deplacerMonstreAttire();
    }
    /**
     * deplace le monstre en fonction de l'action.
     * gere la collision avec les murs et le personnage
     *
     * @param action une des actions possibles
     */
    public void deplacerMonstreAleatoire(String action) {
        if(monstre.getPv() > 0){
            boolean attaque = false;
            int[] courante = {this.monstre.x, this.monstre.y};
            int[] suivante = getSuivant(courante[0], courante[1], action);

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (this.monstre.x - j == this.pj.x && this.monstre.y - i == this.pj.y) {
                        this.pj.subirdegat(1);
                        System.out.println(this.pj.getVie());
                        attaque = true;
                        if (this.pj.getVie() == 0) {
                            System.out.println("Bro's dead, RIP Bozo");
                            System.exit(1);
                        }
                    }
                }
            }
            if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1]) && !attaque) {
                this.monstre.x = suivante[0];
                this.monstre.y = suivante[1];
            }
        }
    }

    public void deplacerMonstreAttire() {
        if(monstre.getPv() > 0){
            boolean attaque = false;
            int[] courante = {this.monstre.x, this.monstre.y};
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
                    if (this.monstre.x - j == this.pj.x && this.monstre.y - i == this.pj.y) {
                        this.pj.subirdegat(1);
                        System.out.println(this.pj.getVie());
                        attaque = true;
                        if (this.pj.getVie() == 0) {
                            System.out.println("Bro's dead, RIP Bozo");
                            System.exit(1);
                        }
                    }
                }
            }
            if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1]) && !attaque) {
                this.monstre.x = suivante[0];
                this.monstre.y = suivante[1];
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

    // ##################################
    // GETTER
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

    public void monstreEstSurCase(int[] position, int degats) {
        if (monstre.getX() == position[0] && monstre.getY() == position[1]) {
            monstre.subirDegats(degats);
        }
    }

    public void nePlusAfficherMonstre() {
        monstre.x = 999;
        monstre.y = 999;
    }
}
