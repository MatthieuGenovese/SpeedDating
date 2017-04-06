package CustomNodes;

/**
 * Created by Jeremy on 06/04/2017.
 */

//Interface Observable
public interface Obs {
    public void ajouterObservateur(Observateur o );
    public void supprimerObservateur(Observateur o);
    public void notifier();
}
