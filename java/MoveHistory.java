import java.util.*;
public class MoveHistory {
    public Map<Integer, int[]> moveLocation;
    public Map<Integer, String> moveResult;

    public MoveHistory() {
        this.moveLocation = new HashMap<Integer, int[]>();
        this.moveResult = new HashMap<Integer, String>();
    }
    public void addMove(int x, int y, String rawResult){
        // todo rawresult might not be the best
        int[] coord = {x,y};
        Integer currentMove = this.getLastMoveNum()+1;
        this.moveLocation.put(currentMove, coord);
        this.moveResult.put(currentMove, rawResult);
    }
    public int getLastMoveNum(){
        int highest = 0;
        for (int value : this.moveLocation.keySet()) {
            if (value > highest){
                highest = value;
            }
        }
        return (int) highest;
    }
    public void print(){
        for (Map.Entry<Integer, int[]> entry : moveLocation.entrySet()) {
            Integer key = entry.getKey();
            int[] value = entry.getValue();
            System.out.print("Move Number: "+key);
            System.out.print(" -- "+Arrays.toString(value));
            System.out.println(" -- "+this.moveResult.get(key));
        }
    }
}
