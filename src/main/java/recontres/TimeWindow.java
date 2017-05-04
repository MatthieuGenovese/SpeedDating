package recontres;

/**
 * Created by Matthieu on 04/05/2017.
 */
public class TimeWindow implements ITimeWindows {
    private int firstSlot, lastSlot;

    public TimeWindow(int firstSlot, int lastSlot){
        this.firstSlot = firstSlot;
        this.lastSlot = lastSlot;
    }


    public int getArrivalSlot(){
        return firstSlot;
    }

    public int getDepartureSlot(){
        return lastSlot;
    }
}
