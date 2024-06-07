package gameLaby.laby;

public class Echelle extends Entite{
    /**
     * Constructeur de l'entité
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Echelle(int dx, int dy) {
        super(dx, dy);
    }

    public int[] getPos(){
        return new int[]{x, y};
    }
}
