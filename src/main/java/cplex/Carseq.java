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
import ilog.concert.IloException;
import ilog.cp.IloCP;
import ilog.opl.IloOplDataSource;
import ilog.opl.IloOplErrorHandler;
import ilog.opl.IloOplException;
import ilog.opl.IloOplFactory;
import ilog.opl.IloOplModel;
import ilog.opl.IloOplModelDefinition;
import ilog.opl.IloOplModelSource;
import ilog.opl.IloOplSettings;


public class Carseq
{
	//    static class MyData extends IloCustomOplDataSource
	//    {
	//        MyData(IloOplFactory oplF)
	//        {
	//            super(oplF);
	//        }

	//        public void customRead()
	//        {
	//            int _nbConfs = 7;
	//            int _nbOptions = 5;
	//
	//            IloOplDataHandler handler = getDataHandler();
	//            handler.startElement("nbConfs");
	//            handler.addIntItem(_nbConfs);
	//            handler.endElement();
	//            handler.startElement("nbOptions");
	//            handler.addIntItem(_nbOptions);
	//            handler.endElement();
	//
	//            int _demand[] = {5, 5, 10, 10, 10, 10, 5};
	//            handler.startElement("demand");
	//            handler.startArray();
	//            for (int i= 0; i< _nbConfs; i++)
	//                handler.addIntItem(_demand[i]);
	//            handler.endArray();
	//            handler.endElement();
	//
	//            int _option[][] = {{1, 0, 0, 0, 1, 1, 0},
	//                               {0, 0, 1, 1, 0, 1, 0},
	//                               {1, 0, 0, 0, 1, 0, 0},
	//                               {1, 1, 0, 1, 0, 0, 0},
	//                               {0, 0, 1, 0, 0, 0, 0}};
	//            handler.startElement("option");
	//            handler.startArray();
	//            for (int i = 0 ; i< _nbOptions ; i++) {
	//                handler.startArray();
	//                for (int j = 0 ; j<_nbConfs ; j++)
	//                    handler.addIntItem(_option[i][j]);
	//                handler.endArray();
	//            }
	//            handler.endArray();
	//            handler.endElement();
	//
	//            int _capacity[][] = {{1, 2}, {2, 3}, {1, 3}, {2, 5}, {1, 5}};
	//            handler.startElement("capacity");
	//            handler.startArray();
	//            for (int i = 0; i<_nbOptions;i++) {
	//                handler.startTuple();
	//                for (int j= 0; j<=1;j++)
	//                    handler.addIntItem(_capacity[i][j]);
	//                handler.endTuple();
	//            }
	//            handler.endArray();
	//            handler.endElement();
	//        }
	//    };

	public void exec() throws Exception
	{
		int status = 127;
		try {
			IloOplFactory.setDebugMode(true);
			IloOplFactory oplF = new IloOplFactory();
			IloOplErrorHandler errHandler = oplF.createOplErrorHandler(System.out);
			IloOplModelSource modelSource=oplF.createOplModelSource("../opl-torpedo/torpedo-scheduling.mod");
			IloOplSettings settings = oplF.createOplSettings(errHandler);
			IloOplModelDefinition def=oplF.createOplModelDefinition(modelSource,settings);
			IloCP cp = oplF.createCP();
			IloOplModel opl=oplF.createOplModel(def,cp);

			IloOplDataSource dataSource1= oplF.createOplDataSource("../opl-torpedo/sandbox.dat");
			opl.addDataSource(dataSource1);
			IloOplDataSource dataSource2= oplF.createOplDataSource("../opl-torpedo/warmup/inst_config1_300_200.dat");
			opl.addDataSource(dataSource2);
			
			

			opl.generate();
			cp.add(new AssignmentConstraint(cp, opl));

//			IloTupleSet reqC2BF = opl.getElement("reqC2BF").asTupleSet();
//			System.out.println(reqC2BF.getSchema());
//			System.out.println(reqC2BF.getSchema().getColumnName(3));
//			System.out.println(reqC2BF.getSchema().getColumnName(2));
			//			// Iterate through the TupleSet.
			//			for (java.util.Iterator it1 = reqC2BF.iterator(); it1.hasNext(); )
			//			{
			//				IloTuple t = (IloTuple)it1.next();
			//
			//				System.out.println(
			//						t.makeTupleValue("bf").getIntValue(0) + " " +t.makeTupleValue("c").getIntValue(0) + " " +t.getIntValue("slack")
			//						);
			//				// Get the string "p" from the tuple.
			//				//int c = t.getIntValue("c.id");
			//				//int b = t.getIntValue("b");
			//				// System.out.println(t.getIndex());
			//			}

			if ( cp.solve() )
			{
				//System.out.println("OBJECTIVE: " + opl.getCP().getObjValue());
				opl.postProcess();
				//opl.printSolution(System.out);
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

}

