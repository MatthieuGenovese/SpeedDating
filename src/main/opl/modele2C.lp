 include "modele2.lp" ;

 subject to{
	 forall(f in F){
		(sum(h in H, c in C) rencontres[f][h][c]) >= mini;   //minimum de rencontre pour chq femme
	}
	forall(h in H){
		(sum(f in F, c in C) rencontres[f][h][c]) >= mini;   //minimum de rencontre pour chq homme
	}
	penalite == 0;
}
