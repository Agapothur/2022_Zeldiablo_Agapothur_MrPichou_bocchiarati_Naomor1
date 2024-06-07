package gameLaby.laby;

import javafx.scene.canvas.GraphicsContext;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Monstre extends Entite{

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
}
