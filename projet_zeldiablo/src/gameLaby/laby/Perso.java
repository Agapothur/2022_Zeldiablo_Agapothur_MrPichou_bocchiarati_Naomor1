package gameLaby.laby;


import javafx.scene.paint.Color;

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

    private int vie;

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
        this.vie-=v;
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
        return this.getCouleur();
    }

    public void setCouleur(Color c){
        this.couleur = c;
    }
}
