package recontres;

import personnes.IParticipants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthieu on 27/04/2017.
 */
public class GestionnaireCreneaux implements IEventMeetings {
    /*ArrayList<Observateur> observateurs = new ArrayList<Observateur>();*/
    ArrayList<Creneau> listeCreneaux;
    int creneauCourant;


    public GestionnaireCreneaux(int nbCrenaux){
        creneauCourant = 1;
        listeCreneaux = new ArrayList<>();
        for(int i = 1; i <= nbCrenaux; i++){
            listeCreneaux.add(new Creneau(i));
        }
        /*notifier();*/
    }

    public void ajouterRencontre(IMeeting meeting, int numCrenau){
        listeCreneaux.get(numCrenau).ajouterRencontre(meeting);
    }

    public int getNbCrenaux(){
        return listeCreneaux.size();
    }

    public Creneau getCrenau(int i ){
        return listeCreneaux.get(i);
    }

    public boolean hasMet(IParticipants homme, IParticipants femme){
        for(int i = 0; i < creneauCourant; i++){
            for(int j = 0; j < listeCreneaux.get(i).getNbRencontres(); j++){
                if(listeCreneaux.get(i).getRencontre(j).getHomme().equals(homme) && listeCreneaux.get(i).getRencontre(j).getFemme().equals(femme)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean metAt(IParticipants homme, IParticipants femme, int slot){
        for(int j = 0; j < listeCreneaux.get(slot-1).getNbRencontres(); j++){
            if(listeCreneaux.get(slot-1).getRencontre(j).getHomme().equals(homme) && listeCreneaux.get(slot-1).getRencontre(j).getFemme().equals(femme)){
                return true;
            }
        }
        return false;
    }

    public boolean isPresent(IMeeting meeting){
        for(int i = 0; i < creneauCourant; i++){
            for(int j = 0; j < listeCreneaux.get(i).getNbRencontres(); j++){
                if(listeCreneaux.get(i).getRencontre(j).equals(meeting)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<IMeeting> getPastMeetings(){
        ArrayList<IMeeting> pastMeetings = new ArrayList<>();
        for(int i = 0; i < creneauCourant; i++){
            for(int j = 0; j < listeCreneaux.get(i).getNbRencontres(); j++){
                pastMeetings.add(listeCreneaux.get(i).getRencontre(j));
            }
        }
        return pastMeetings;
    }

    public List<IMeeting> getCurrentMeetings(){
        ArrayList<IMeeting> currentMeetings = new ArrayList<>();
        for(int j = 0; j < listeCreneaux.get(creneauCourant-1).getNbRencontres(); j++){
            currentMeetings.add(listeCreneaux.get(creneauCourant-1).getRencontre(j));
        }
        return currentMeetings;
    }

    public List<IMeeting> getNextMeetings(){
        ArrayList<IMeeting> nextMeetings = new ArrayList<>();
        for(int i = creneauCourant; i < listeCreneaux.size(); i++){
            for(int j = 0; j < listeCreneaux.get(i).getNbRencontres(); j++){
                nextMeetings.add(listeCreneaux.get(i).getRencontre(j));
            }
        }
        return nextMeetings;
    }

    //public List<IMeeting> getScheduledMeetings();

    public List<IMeeting> getAllMeetings(){
        ArrayList<IMeeting> res = new ArrayList<IMeeting>(getNextMeetings());
        res.addAll(getCurrentMeetings());
        res.addAll(getPastMeetings());
        return res;
    }

    /*@Override
    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifier() {
        for(Observateur o : observateurs){
            o.updated(this);
        }
    }*/
}
