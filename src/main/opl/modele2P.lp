 include "modele2.lp";

 subject to {
 	penalite == - ((sum(f in F) (nbRencontresF[f] < mini)) + (sum(h in H) (nbRencontresH[h] < mini)));
}
