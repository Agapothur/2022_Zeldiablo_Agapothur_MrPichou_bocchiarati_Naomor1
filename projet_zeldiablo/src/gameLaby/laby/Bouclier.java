package gameLaby.laby;

public class Bouclier extends Items{
    int pv;
    /**
     * Constructeur de l'entité
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Bouclier(int dx, int dy) {
        super(dx, dy);
        pv = 2 + (int)Math.floor(Math.random()*4);
    }

    public void subirDegats(int degats){
        pv -= degats;
    }

    public int[] getPos() {
        return new int[]{x, y};
    }
}
