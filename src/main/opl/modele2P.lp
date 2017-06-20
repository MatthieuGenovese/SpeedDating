 include "modele2.lp";

 //nommé modele2 dans rapport

 subject to {
 	penalite == - ((sum(f in F) (nbRencontresF[f] < mini)) + (sum(h in H) (nbRencontresH[h] < mini)));
}
