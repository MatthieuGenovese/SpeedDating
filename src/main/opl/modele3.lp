using CP;
 //DONNEES

 int f = ...; //nb de femmes
 int h = ...; //nb d'hommes
 int c = ...; //nb de créneaux
 int mini = ...;

 int fw = f+1;  // femme "attente" -> si un homme a rdv avec elle, il est en realité en train d'attendre
 int fe = f+2;  // femme "de fin" -> si un homme a rdv avec elle, il a en realité fini sa soirée
 int hw = h+1;  // homme "attente"
 int he = h+2;  // homme "de fin"

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

 dvar int rencontres[F2][H2][C] in 0..1; // tab de sortie : 1 si rencontre entre cette femme et cet homme a ce creneau

 dexpr int nbRencontresF[f in F] = sum(h in H, c in C) rencontres[f][h][c]; //nb de rencontres par femme
 dexpr int nbRencontresH[h in H] = sum(f in F, c in C) rencontres[f][h][c]; //nb de rencontres par homme

 dexpr int penalite = - ((sum(f in F) (nbRencontresF[f] < mini)) + (sum(h in H) (nbRencontresH[h] < mini)));
 	// on deduit une penalité à l'objectif pour chaque personne rencontrant moins que le minimum de rencontre

 dexpr int maxScores = sum(f in F, h in H, c in C) scores[f][h] * rencontres[f][h][c];
	// on fait la somme des scores des rencontres effectuées

 dexpr int attentesF[f in F] = sum(c in C) rencontres[f][hw][c]; //nb de creneau a attendre pour chaque femme
 dexpr int attentesH[h in H] = sum(c in C) rencontres[fw][h][c];


 dexpr int attentesTotF = sum(f in F) attentesF[f]; //nb de creneau a attendre pour toutes les femmes
 dexpr int attentesTotH = sum(h in H) attentesH[h];


 dexpr int attentesTot = attentesTotF + attentesTotH; //nb de creneau d'attente

 maximize maxScores - attentesTot + penalite; // on maximise la somme des scores et le nombre de rencontre, on minimise l'attente


 //CONTRAINTES

 subject to {
 	forall(f in F, h in H, c in C){                      //rencontres interdites
		if(scores[f][h] == 0){
			rencontres[f][h][c] == 0;
 		}
	}
 	forall(f in F, h in H){               				 //Au plus 1 rencontre par couple
 		(sum(c in C) rencontres[f][h][c]) <= 1;
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
   			rencontres[f][he][c] == 1;		//après depart rdv avec homme de fin
     	}
     	if (c > dispoF[f][2]){            //pas de rdv apres depart pour les hommes
   			rencontres[f][h][c] == 0;
     	}
    }
    forall(h in H, c in C, f in F){
		if (c < dispoH[h][1]){            //pas de rdv avant arrivee pour les hommes
			rencontres[f][h][c] == 0;
   		}
   		if (c > dispoH[h][2]){            //pas de rdv apres depart pour les hommes
   			rencontres[fe][h][c] == 1;		//apres depart rdv avec femme de fin
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
    forall(f in F, cr in 1..c-1){            //plus de rencontres reelle apres une rencontre de fin pr femme
    	rencontres[f][he][cr] <= rencontres[f][he][cr+1];
    }
    forall(h in H, cr in 1..c-1){
   		rencontres[fe][h][cr] <= rencontres[fe][h][cr+1];
    }
    forall(c in C){                // les personnes fictives ne se rencontrent pas entre elles
    	rencontres[fe][he][c]==0;
    	rencontres[fe][hw][c]==0;
    	rencontres[fw][he][c]==0;
    	rencontres[fw][hw][c]==0;
   }
}
