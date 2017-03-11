package cplex;

import java.util.Iterator;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloIntervalVarMap;
import ilog.concert.IloTuple;
import ilog.concert.IloTupleSet;
import ilog.cp.IloCP;
import ilog.cp.IloCustomConstraint;
import ilog.opl.IloOplModel;

// https://leifengtechblog.wordpress.com/2015/11/14/setting-up-eclipse-for-java-api-of-ibm-ilog-cplex/
// https://www.ibm.com/developerworks/community/forums/html/topic?id=77777777-0000-0000-0000-000014553046
class AssignmentConstraint extends IloCustomConstraint {


    private final IloIntVar desulf;

    private final IloCP cp;

    private IloIntervalVarMap aBFToCVarMap;

    private final IloTupleSet reqC2BF;

    public AssignmentConstraint(IloCP cp, IloOplModel opl) throws IloException {
        super(cp);
        System.out.println("CTOR");
        this.cp = cp;
        this.desulf = opl.getElement("timeDesulf").asIntVar();
        addVar(desulf);
        reqC2BF = opl.getElement("reqC2BF").asTupleSet();
        aBFToCVarMap = opl.getElement("aBFToConverter").asIntervalVarMap();
    }

    @Override
    public void execute() {
        //System.out.println("CALL " + cp.getMin(desulf) + ", " + cp.getMax(desulf)) ;
        //setMax(desulf, 125);
        double i = 0;
        for (Iterator it1 = reqC2BF.iterator(); it1.hasNext(); ) {
            final IloTuple t = (IloTuple) it1.next();
            try {
                if (cp.isAbsent(aBFToCVarMap.get(t))) i++;
            } catch (IloException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("CALL " + i / reqC2BF.getSize());
        //			System.out.println(
        //					t.makeTupleValue("bf").getIntValue(0) + " " +t.makeTupleValue("c").getIntValue(0) + " " +t.getIntValue("slack")
        //					);
        // Get the string "p" from the tuple.
        //int c = t.getIntValue("c.id");
        //int b = t.getIntValue("b");
        // System.out.println(t.getIndex());
    }
}
