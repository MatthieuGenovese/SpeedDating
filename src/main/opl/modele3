using CP;
 //DONNEES

 int f = ...; //nb de femmes
 int h = ...; //nb d'hommes
 int c = ...; //nb de créneaux
 int mini = ...;

 int fw = f+1;
 int fe = f+2;
 int hw = h+1;
 int he = h+2;

 range F = 1..f;
 range H = 1..h;
 range C = 1..c;
 range A = 1..2;

 range F2 = 1..fe;
 range H2 = 1..he;

 int scores[F][H]= ...; //tableau de scores passé en entrée
 int dispoF[F][A]= ...;
 int dispoH[H][A]= ...;

 //SORTIES

 dvar int rencontres[F2][H2][C] in 0..1;

 dexpr int nbRencontresF[f in F] = sum(h in H, c in C) rencontres[f][h][c]; //nb de rencontres par femme
 dexpr int nbRencontresH[h in H] = sum(f in F, c in C) rencontres[f][h][c]; //nb de rencontres par homme

 dexpr int penalite = - ((sum(f in F) (nbRencontresF[f] < mini)) + (sum(h in H) (nbRencontresH[h] < mini)));

 dexpr int maxScores = sum(f in F, h in H, c in C) scores[f][h] * rencontres[f][h][c];


 dexpr int attentesF[f in F] = sum(c in C) rencontres[f][hw][c]; //nb de creneau a attendre par femme
 dexpr int attentesH[h in H] = sum(c in C) rencontres[fw][h][c];


 dexpr int attentesTotF = sum(f in F) attentesF[f];
 dexpr int attentesTotH = sum(h in H) attentesH[h];


 dexpr int attentesTot = attentesTotF + attentesTotH; //nb de creneau d'attente

 maximize maxScores - attentesTot + penalite;


 //CONTRAINTES

 subject to {

 	forall(f in F, h in H, c in C){                                   //rencontres interdites
		if(scores[f][h] == 0){
			rencontres[f][h][c] == 0;
 		}
	}
 	forall(f in F, h in H){                  				 //Au plus 1 rencontre par couple
 		(sum(c in C) rencontres[f][h][c]) <= 1;
 	}
	forall(f in F){
		(sum(h in H, c in C) rencontres[f][h][c]) >= mini;   //minimum de rencontre pour chq femme
	}
	forall(h in H){
		(sum(f in F, c in C) rencontres[f][h][c]) >= mini;   //minimum de rencontre pour chq homme
	}
	forall(f in F, c in C){
		(sum(h in H) rencontres[f][h][c]) <= 1; //au plus 1 rencontres par femme et par créneau
	}
	forall(h in H, c in C){
		(sum(f in F) rencontres[f][h][c]) <= 1;	// au plus 1 rencontes par homme et par creneau
	}
	forall(f in F, c in C, h in H){
		if (c < dispoF[f][1]){            //pas de rdv avant arrivee pour les femmes
			rencontres[f][h][c] == 0;
   		}
   		if (c > dispoF[f][2]){            //pas de rdv apres depart pour les femmes
   			rencontres[f][h][c] == 0;
     	}
    }
    forall(h in H, c in C, f in F){
		if (c < dispoH[h][1]){            //pas de rdv avant arrivee pour les hommes
			rencontres[f][h][c] == 0;
   		}
   		if (c > dispoH[h][2]){            //pas de rdv apres depart pour les hommes
   			rencontres[f][h][c] == 0;
     	}
    }
    forall(f in F, c in C : c >= dispoF[f][1]){                  //1 rencontre obligatoire a chq créneau, reelle ou non pr femme
    	//if (c >= dispoF[f][1]){
    		(sum(h in H2) rencontres[f][h][c]) == 1;

    }
    forall(h in H, c in C){                  //1 rencontre obligatoire a chq créneau, reelle ou non pr homme
    	if (c >= dispoH[h][1]){
    		(sum(f in F2) rencontres[f][h][c]) == 1;
    	}
    }
    forall(f in F, cr in 1..c-1){            //plus de rencontres reelle apres une rencontre de fin
    	rencontres[f][he][cr] <= rencontres[f][he][cr+1];
    }
    forall(h in H, cr in 1..c-1){
   		rencontres[fe][h][cr] <= rencontres[fe][h][cr+1];
    }
    forall(c in C){
    	rencontres[fe][he][c]==0;
    	rencontres[fe][hw][c]==0;
    	rencontres[fw][he][c]==0;
    	rencontres[fw][hw][c]==0;
   }
}
