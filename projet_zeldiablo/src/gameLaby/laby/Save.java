package gameLaby.laby;

public class Save {
    int pvJoueur;
    boolean amulette;

    public void save(Labyrinthe l){
        pvJoueur = l.pj.getVie();
        amulette = l.pj.getAmulette();
    }

    public void restaure(Labyrinthe l){
        l.pj.setVie(pvJoueur);
        l.pj.setAmulette(amulette);
    }
}
