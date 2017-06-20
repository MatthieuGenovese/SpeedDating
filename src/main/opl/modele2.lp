using CP;
//include pour les modèles modele2C et modele2P
 //DONNEES

 int f = ...; //nb de femmes
 int h = ...; //nb d'hommes
 int c = ...; //nb de créneaux
 int mini = ...;

 range F = 1..f;
 range H = 1..h;
 range C = 1..c;
 range C2 = 1..(c-1);
 range A = 1..2;

 int scores[F][H]= ...; //tableau de scores passé en entrée
 int dispoF[F][A]= ...;
 int dispoH[H][A]= ...;

 dvar int penalite ;

 //SORTIES

 dvar int rencontres[F][H][C] in 0..1;

 dexpr int rencontresF[f in F][c in C] = sum(h in H) rencontres[f][h][c];
 dexpr int rencontresH[h in H][c in C] = sum(f in F) rencontres[f][h][c];

 dvar int presenceF[f in F][c in C];
 dvar int presenceH[h in H][c in C];

 dexpr int dernierF[f in F] = sum(c in C)presenceF[f][c];
 dexpr int dernierH[h in H] = sum(c in C)presenceH[h][c];

 dexpr int nbRencontresF[f in F] = sum(h in H, c in C) rencontres[f][h][c]; //nb de rencontres par femme
 dexpr int nbRencontresH[h in H] = sum(f in F, c in C) rencontres[f][h][c]; //nb de rencontres par homme


 dexpr int maxScores = sum(f in F, h in H, c in C) scores[f][h] * rencontres[f][h][c];


 dexpr int attentesF[f in F] = dernierF[f] - nbRencontresF[f]; //nb de creneau a attendre par femme
 dexpr int attentesH[h in H]= dernierH[h] - nbRencontresH[h];

 dexpr int attentesTot = sum(f in F)attentesF[f] + sum(h in H)attentesH[h]; //nb de creneau d'attente

  //pour maximiser le nombre de rencontres :
 //maximize  maxScores + (sum(f in F)nbRencontresF[f]) + (sum(h in H)nbRencontresH[h]);

  //penalité si on respecte pas le minimum
 //maximise maxScores + penalite
 maximize maxScores - attentesTot + penalite;


 //CONTRAINTES

 subject to {

 	 forall(f in F, c in C){
 		presenceF[f][c] >= rencontresF[f][c];
 		presenceF[f][c] <= 1;
 	}
 	forall(f in F, c in C2){
 		presenceF[f][c] >= presenceF[f][c+1];
 		presenceF[f][c] <= 1;
 	}
 	forall(h in H, c in C){
 		presenceH[h][c] >= rencontresH[h][c];
 		presenceH[h][c] <= 1;
 	}
 	forall(h in H, c in C2){
 		presenceH[h][c] >= rencontresH[h][c+1];
 		presenceH[h][c] <= 1;
 	}

 	forall(f in F, h in H, c in C){                                   //rencontres interdites
		if(scores[f][h] == 0){
			rencontres[f][h][c] == 0;
 		}
	}
 	forall(f in F, h in H){                  				 //Au plus 1 rencontre par couple
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
}
