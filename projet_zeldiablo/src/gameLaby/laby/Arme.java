package gameLaby.laby;

public class Arme extends Entite{

    int boost;
    /**
     * Constructeur de l'entité
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Arme(int dx, int dy) {
        super(dx, dy);
        boost = 2 + (int)Math.floor(Math.random()*2);
    }

    public int boostDegats(int degats){
        return degats+boost;
    }

    public int[] getPos() {
        return new int[]{x, y};
    }
}
