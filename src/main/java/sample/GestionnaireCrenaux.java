package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthieu on 27/04/2017.
 */
public class GestionnaireCrenaux implements IEventMeetings {
    ArrayList<Crenau> listeCrenaux;
    int crenauCourant;

    public GestionnaireCrenaux(int nbCrenaux){
        crenauCourant = 1;
        listeCrenaux = new ArrayList<>();
        for(int i = 1; i <= nbCrenaux; i++){
            listeCrenaux.add(new Crenau(i));
        }
    }

    public void ajouterRencontre(IMeeting meeting, int numCrenau){
        listeCrenaux.get(numCrenau).ajouterRencontre(meeting);
    }

    public int getNbCrenaux(){
        return listeCrenaux.size();
    }

    public Crenau getCrenau(int i ){
        return listeCrenaux.get(i);
    }


    public boolean hasMet(PersonneSoiree homme, PersonneSoiree femme){
        for(int i = 0; i < crenauCourant; i++){
            for(int j = 0; j < listeCrenaux.get(i).getNbRencontres(); j++){
                if(listeCrenaux.get(i).getRencontre(j).getHomme().equals(homme) && listeCrenaux.get(i).getRencontre(j).getFemme().equals(femme)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean metAt(PersonneSoiree homme, PersonneSoiree femme, int slot){
        for(int j = 0; j < listeCrenaux.get(slot-1).getNbRencontres(); j++){
            if(listeCrenaux.get(slot-1).getRencontre(j).getHomme().equals(homme) && listeCrenaux.get(slot-1).getRencontre(j).getFemme().equals(femme)){
                return true;
            }
        }
        return false;
    }

    public boolean isPresent(IMeeting meeting){
        for(int i = 0; i < crenauCourant; i++){
            for(int j = 0; j < listeCrenaux.get(i).getNbRencontres(); j++){
                if(listeCrenaux.get(i).getRencontre(j).equals(meeting)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<IMeeting> getPastMeetings(){
        ArrayList<IMeeting> pastMeetings = new ArrayList<>();
        for(int i = 0; i < crenauCourant; i++){
            for(int j = 0; j < listeCrenaux.get(i).getNbRencontres(); j++){
                pastMeetings.add(listeCrenaux.get(i).getRencontre(j));
            }
        }
        return pastMeetings;
    }

    public List<IMeeting> getCurrentMeetings(){
        ArrayList<IMeeting> currentMeetings = new ArrayList<>();
        for(int j = 0; j < listeCrenaux.get(crenauCourant-1).getNbRencontres(); j++){
            currentMeetings.add(listeCrenaux.get(crenauCourant-1).getRencontre(j));
        }
        return currentMeetings;
    }

    public List<IMeeting> getNextMeetings(){
        ArrayList<IMeeting> nextMeetings = new ArrayList<>();
        for(int i = crenauCourant; i < listeCrenaux.size(); i++){
            for(int j = 0; j < listeCrenaux.get(i).getNbRencontres(); j++){
                nextMeetings.add(listeCrenaux.get(i).getRencontre(j));
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
}
