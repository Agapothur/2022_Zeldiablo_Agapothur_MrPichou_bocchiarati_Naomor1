package gameLaby.laby;

public class Save {
    int pvJoueur;
    boolean amulette;
    Arme arme;
    Bouclier bouclier;

    public void save(Labyrinthe l){
        pvJoueur = l.pj.getVie();
        amulette = l.pj.getAmulette();
        arme = l.pj.getArme();
        bouclier = l.pj.getBouclier();
    }

    public void restaure(Labyrinthe l){
        l.pj.setVie(pvJoueur);
        l.pj.setAmulette(amulette);
        l.pj.setArme(arme);
        l.pj.setBouclier(bouclier);
    }
}
