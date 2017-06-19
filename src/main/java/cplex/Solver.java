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


import ilog.concert.IloException;
import ilog.cp.IloCP;
import ilog.opl.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Solver
{
    private static String chemin;
    private static int colonnes, lignes;
    private static int creneaux;

    public Solver(String chemin, int colonnes, int lignes, int creneaux){
        this.chemin = chemin;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.creneaux = creneaux;
    }

    static class MyData extends IloCustomOplDataSource
    {
        MyData(IloOplFactory oplF)
        {
            super(oplF);
        }

        public void customRead()
        {
            //colonnes = 3;
            //lignes = 3;
            int f = lignes;
            int h = colonnes;
            int c = creneaux;
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
            int dispoH[][] = importerDispoH();
            int dispoF[][] = importerDispoF();
            handler.startElement("scores");
            handler.startArray();
            for (int i = 0 ; i < f ; i++) {
                handler.startArray();
                for (int j = 0 ; j< h ; j++)
                    handler.addIntItem(scores[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();


            handler.startElement("dispoF");
            handler.startArray();
            for (int i = 0 ; i< f ; i++) {
                handler.startArray();
                for (int j = 0 ; j< 2 ; j++)
                    handler.addIntItem(dispoF[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();

            handler.startElement("dispoH");
            handler.startArray();
            for (int i = 0 ; i< h ; i++) {
                handler.startArray();
                for (int j = 0 ; j< 2; j++)
                    handler.addIntItem(dispoH[i][j]);
                handler.endArray();
            }
            handler.endArray();
            handler.endElement();
        }
    };

    public int[][][] exec() throws Exception {
        int status = 127;
        //getModelFromFile();
        int[][][] resultat = {{{-1}}};
        int iteration = 1;
        try {
            IloOplFactory.setDebugMode(false);
            IloOplFactory oplF = new IloOplFactory();
            IloOplErrorHandler errHandler = oplF.createOplErrorHandler(System.out);
            IloOplModelSource modelSource = oplF.createOplModelSource(chemin);
            IloOplSettings settings = oplF.createOplSettings(errHandler);
            IloOplModelDefinition def = oplF.createOplModelDefinition(modelSource, settings);
            IloCP cp = oplF.createCP();
            cp.setOut(null);
            IloOplModel opl = oplF.createOplModel(def, cp);
            IloOplDataSource dataSource=new MyData(oplF);
            String nomDonnees = "src\\main\\opl\\donnees" + Integer.toString(iteration);
            //IloOplDataSource dataSource = new IloOplDataSource(oplF.getEnv(), nomDonnees + ".dat");
            opl.addDataSource(dataSource);
            opl.generate();

            if (cp.solve()) {
                opl.postProcess();
                String res = opl.getElement("rencontres").asIntMap().toString();
                resultat = extraireResultats(res, 3);
                status = 0;
            } else {
                System.out.println("No solution!");
                status = 1;
            }

            oplF.end();
        }
        catch(IloOplException ex){
            System.err.println("### OPL exception: " + ex.getMessage());
            ex.printStackTrace();
            status = 2;
        } catch(IloException ex){
            System.err.println("### CONCERT exception: " + ex.getMessage());
            ex.printStackTrace();
            status = 3;
        } catch(Exception ex){
            System.err.println("### UNEXPECTED UNKNOWN ERROR ...");
            ex.printStackTrace();
            status = 4;
        }

        return resultat;
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

                nbLignes++;
                String[] decompose = ligne.split(",");
                nbCol = decompose.length;
                listeSArray.add(decompose);
                ligne = r.readLine();
            }
            r.close();
            res = new int [nbLignes][nbCol];
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

    static int[][] importerDispoH(){
        ArrayList<String[]> listeSArray = new ArrayList<>();
        int res[][];
        int nbLigne = 0;
        try {
            FileReader c = new FileReader("dispoH.csv");
            BufferedReader r = new BufferedReader(c);
            String ligne = r.readLine();


            while (ligne != null) {
                nbLigne++;
                String[] decompose = ligne.split(",");
                listeSArray.add(decompose);
                ligne = r.readLine();
            }
            r.close();
            res = new int [nbLigne][2];
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

    static int[][] importerDispoF(){
        ArrayList<String[]> listeSArray = new ArrayList<>();
        int res[][];
        int nbLigne = 0;
        try {
            FileReader c = new FileReader("dispoF.csv");
            BufferedReader r = new BufferedReader(c);
            String ligne = r.readLine();


            while (ligne != null) {
                nbLigne++;
                String[] decompose = ligne.split(",");
                listeSArray.add(decompose);
                ligne = r.readLine();
            }
            r.close();
            res = new int [nbLigne][2];
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

    public int[][][] extraireResultats(String res, int model){
        char[] tableau = res.toCharArray();
        int taillef,tailleh;
        if(model ==3) {
            taillef = lignes+2;
            tailleh = colonnes+2;
        }
        else{

            taillef = lignes;
            tailleh = colonnes;
        }

        int results[][][] = new int[taillef][tailleh][creneaux];
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
                niveau2 = false;
            }
            else if(c == ']' && niveau3){
                cpt3 = 0;
                cpt2++;
                niveau3 = false;
            }
            else if(c == ';'){
                break;
            }
            else if(Character.isDigit(c) && start){
                results[cpt1][cpt2][cpt3] = Character.getNumericValue(c);
                cpt3++;
            }
        }
        for(int i = 0; i < taillef; i++){
            for(int j = 0; j < tailleh; j++){
                for(int k = 0; k < creneaux; k++) {
                    System.out.print(results[i][j][k] + ",");
                }
                System.out.println();
            }
            System.out.println();
        }
        return results;
    }
}