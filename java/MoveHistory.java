// Move Number: 1 -- [6, 1] -- H
// Move Number: 2 -- [7, 1] -- 0
// Move Number: 3 -- [5, 1] -- 0
// Putting [6, 2] in 4
// Move Number: 1 -- [6, 1] -- H
// Move Number: 2 -- [7, 1] -- 0
// Move Number: 3 -- [5, 1] -- 0
// Move Number: 4 -- [6, 2] -- H <--- Good!

// Move Number: 1 -- [6, 1] -- H
// Move Number: 2 -- [7, 1] -- 0
// Move Number: 3 -- [5, 1] -- 0
// Move Number: 4 -- [6, 3] -- H <--- Bad! see its already corrupted coming in here
// Putting [6, 3] in 5
// Move Number: 1 -- [6, 1] -- H
// Move Number: 2 -- [7, 1] -- 0
// Move Number: 3 -- [5, 1] -- 0
// Move Number: 4 -- [6, 3] -- H
// Move Number: 5 -- [6, 3] -- H

import java.util.*;
public class MoveHistory {
    private Map<Integer, int[]> moveLocation;
    private Map<Integer, String> moveResult;
    private Board parent;

    public MoveHistory(Board parent) {
        this.moveLocation = new HashMap<Integer, int[]>();
        this.moveResult = new HashMap<Integer, String>();
        this.parent = parent;
    }
    public void addMove(int x, int y, String result){
        // todo result might not be the best
        int[] coord = {x,y};
        // this.print();
        Integer currentMove = this.getHighestMoveNum()+1;
        this.moveLocation.put(currentMove, coord);
        this.moveResult.put(currentMove, result);
    }
    public int getHighestMoveNum(){
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
    public int[] getLastHitN(int n){
        int highestMove = this.getHighestMoveNum();
        int nthHit = 0;
        for (int i = highestMove; i > 0; i--){
            int[] coord = this.moveLocation.get(i);
            if (this.parent.isHit(coord[0], coord[1])){
                nthHit += 1;
                if (nthHit == n){
                    return this.moveLocation.get(i);
                }
            }
        }
        this.print();
        this.parent.print();
        int[] doesNotExist = {-1};
        return doesNotExist; 
    }
}
