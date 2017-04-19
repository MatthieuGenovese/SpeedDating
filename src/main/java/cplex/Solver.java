/*
 * Licensed Materials - Property of IBM
 * 5725-A06 5725-A29 5724-Y48 5724-Y49 5724-Y54 5724-Y55
 * Copyright IBM Corporation 1998, 2012. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

//-------------------------------------------------------------- -*- Java -*-
//Java version of carseq.cpp of OPL distrib
//--------------------------------------------------------------------------
package cplex;


import cplex.AssignmentConstraint;
import ilog.concert.*;
import ilog.cp.IloCP;
import ilog.opl.*;

import java.io.*;
import java.util.ArrayList;

public class Solver
{
    private static String chemin;
    private static int colonnes, lignes;
    private static int crenaux = 2;

    public Solver(String chemin, int colonnes, int lignes){
        this.chemin = chemin;
        this.lignes = lignes;
        this.colonnes = colonnes;
    }

    static class MyData extends IloCustomOplDataSource
    {
        MyData(IloOplFactory oplF)
        {
            super(oplF);
        }

        public void customRead()
        {
            int f = colonnes;
            int h = lignes;
            int c = crenaux;
            int mini = 2;


            IloOplDataHandler handler = getDataHandler();
            handler.startElement("f");
            handler.addIntItem(f);
            handler.endElement();
            handler.startElement("h");
            handler.addIntItem(h);
            handler.endElement();
            handler.startElement("c");
            handler.addIntItem(c);
            handler.endElement();
            handler.startElement("mini");
            handler.addIntItem(mini);
            handler.endElement();

            int scores[][] = importerMatrice();


            int dispoF[][] ={{0, 0},
                    {0, 0}};
            int dispoH[][] ={{0, 0},
                    {0, 0}};

            handler.startElement("scores");
            handler.startArray();
            for (int i = 0 ; i< f ; i++) {
                handler.startArray();
                for (int j = 0 ; j< h ; j++)
                    handler.addIntItem(scores[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();


           /* handler.startElement("dispoF");
            handler.startArray();
            for (int i = 0 ; i< f-1 ; i++) {
                handler.startArray();
                for (int j = 0 ; j< h-1 ; j++)
                    handler.addIntItem(dispoF[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();

            handler.startElement("dispoH");
            handler.startArray();
            for (int i = 0 ; i< f-1 ; i++) {
                handler.startArray();
                for (int j = 0 ; j< h -1; j++)
                    handler.addIntItem(dispoH[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();*/
        }
    };

    public void exec() throws Exception
    {
        int status = 127;
        //getModelFromFile();
        try {
            IloOplFactory.setDebugMode(true);
            IloOplFactory oplF = new IloOplFactory();
            IloOplErrorHandler errHandler = oplF.createOplErrorHandler(System.out);
            IloOplModelSource modelSource=oplF.createOplModelSourceFromString(getModelFromFile(),"test opl");
            IloOplSettings settings = oplF.createOplSettings(errHandler);
            IloOplModelDefinition def=oplF.createOplModelDefinition(modelSource,settings);
            IloCP cp = oplF.createCP();
            IloOplModel opl=oplF.createOplModel(def,cp);
            IloOplDataSource dataSource=new MyData(oplF);
            opl.addDataSource(dataSource);
            opl.generate();
            //opl.getElement("scores").asIntMap().set(1,1);
            //System.out.println(opl.getElement("demand").asIntMap().get(1));
            if ( cp.solve() )
            {
                System.out.println("OBJECTIVE: " + opl.getCP().getObjValue());
                opl.postProcess();
                opl.printSolution(System.out);
                String res;
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                opl.printSolution(os);
                res = os.toString();
                os.close();
                extraireResultats(res,colonnes);
                status = 0;
            } else {
                System.out.println("No solution!");
                status = 1;
            }

            oplF.end();
        } catch (IloOplException ex) {
            System.err.println("### OPL exception: " + ex.getMessage());
            ex.printStackTrace();
            status = 2;
        } catch (IloException ex) {
            System.err.println("### CONCERT exception: " + ex.getMessage());
            ex.printStackTrace();
            status = 3;
        } catch (Exception ex) {
            System.err.println("### UNEXPECTED UNKNOWN ERROR ...");
            ex.printStackTrace();
            status = 4;
        }
    }

    static String getModelFromFile(){
        BufferedReader fis;
        String ligne = "";
            try {
            fis =  new BufferedReader(new FileReader(new File(chemin)));
            String c = fis.readLine();
            while(c != null){
                ligne += c;
                ligne += "\n";
                c = fis.readLine();
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ligne;
    }

    static int[][] importerMatrice() {
        ArrayList<String[]> listeSArray = new ArrayList<>();
        int nbCol = 0;
        int res[][];
        int nbLignes = 0;
        try {
            FileReader c = new FileReader("matriceConflits.csv");
            BufferedReader r = new BufferedReader(c);
            String ligne = r.readLine();


            while (ligne != null) {
                String[] decompose = ligne.split(",");
                nbCol = decompose.length;
                listeSArray.add(decompose);
                ligne = r.readLine();
                nbLignes++;
            }
            r.close();
            res = new int [nbCol][nbLignes];
            for(int i = 0; i < listeSArray.size(); i++){
                for(int j = 0; j < listeSArray.get(i).length; j++){
                    res[i][j] = Integer.parseInt(listeSArray.get(i)[j]);
                }
            }

        } catch (Exception e) {
            throw new Error(e);
        }
        return res;
    }

    public int[][][] extraireResultats(String res, int taille){
        char[] tableau = res.toCharArray();
        int[][][] results = new int[taille][taille][crenaux];
        int cpt1 = 0;
        int cpt2 = 0;
        int cpt3 = 0;
        boolean niveau2 = false;
        boolean niveau3 = false;
        boolean start = false;
        for(Character c : tableau){
            if(c == '[' && !start){
                start = true;
            }
            else if(c == '[' && !niveau2){
                niveau2 = true;
            }
            else if(c == '[' && !niveau3){
                niveau3 = true;
            }
            else if(c == ']' && niveau2 && !niveau3){
                cpt2 = 0;
                cpt1++;
            }
            else if(c == ']' && niveau3){
                cpt3 = 0;
                cpt2++;
                niveau3 = false;
            }
            else if(Character.isDigit(c) && start){
                results[cpt1][cpt2][cpt3] = Character.getNumericValue(c);
                cpt3++;
            }
        }
        for(int i = 0; i < taille; i++){
            for(int j = 0; j < taille; j++){
                for(int k = 0; k < crenaux; k++) {
                    System.out.print(results[i][j][k] + ",");
                }
                System.out.println();
            }
            System.out.println();
        }
        return results;
    }

    static String getModelText()
    {
        String model="using CP;";
        model+="int f = ...;";
        model+="int h = ...; ";
        model+="int c = ...;";
        model+="int mini = ...;";
        model+="range F = 1..f;";
        model+="range H = 1..h;" ;
        model+="range C = 1..c;";
        model+="range A = 1..2;";
        model+="int scores[F][H]= ...;" ;
        model+="dvar int rencontres[F][H][C] in 0..1;";
        model+="dexpr int attentes = -(h*f - sum(f in F, c in C, h in H ) rencontres[f][h][c]);";
        model+="dexpr int maxScores = sum(f in F, h in H, c in C) scores[f][h] * rencontres[f][h][c];";
        model+="maximize attentes + maxScores;";
        model+="subject to {";
        model+="forall(f in F, h in H, c in C){";
        model+="if(scores[f][h] == 0){";
        model+="rencontres[f][h][c] == 0;";
        model+="}";
        model+="}";
        model+="forall(f in F, h in H){";
        model+="(sum(c in C) rencontres[f][h][c]) <= 1;";
        model+="}";
        model+="forall(f in F){";
        model+="(sum(h in H, c in C) rencontres[f][h][c]) >= mini;";
        model+="}";
        model+="forall(h in H){";
        model+="(sum(f in F, c in C) rencontres[f][h][c]) >= mini;";
        model+="}";
        model+="forall(f in F, c in C){";
        model+="(sum(h in H) rencontres[f][h][c]) <= 1;";
        model+="}";
        model+="forall(h in H, c in C){";
        model+="(sum(f in F) rencontres[f][h][c]) <= 1;";
        model+="};";
        model+="}";
        return model;
    }
}