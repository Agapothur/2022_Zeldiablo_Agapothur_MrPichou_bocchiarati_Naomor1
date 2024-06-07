package gameLaby.laby;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Monstre extends Entite{
    private Color couleur = Color.PURPLE;
    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    private int pv;
    public Monstre(int dx, int dy) {
        super(dx, dy);
        pv = 3;
    }

    public void subirDegats(int degats) {
        this.pv -= degats;
    }

    public int getPv() {
        return pv;
    }

    public Color getCouleur(){
        return this.couleur;
    }

    public void setCouleur(Color c){
        this.couleur = c;
    }
}
