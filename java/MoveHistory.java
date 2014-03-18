import java.util.*;
public class MoveHistory {
    public Map<Integer, int[]> moveLocation;
    public Map<Integer, String> moveResult;
    private Board parent;

    public MoveHistory(Board parent) {
        this.moveLocation = new HashMap<Integer, int[]>();
        this.moveResult = new HashMap<Integer, String>();
        this.parent = parent;
    }
    public void addMove(int x, int y, String result){
        // todo result might not be the best
        int[] coord = {x,y};
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
    public HashMap<Integer, int[]> getAllHits(){
        // myMap = new HashMap<String, String>();
        // allHits = new HashMap<Integer, int[]>();
        HashMap allHits = new HashMap();
        int lastMove = this.getHighestMoveNum();
        for (Map.Entry<Integer, int[]> entry : moveLocation.entrySet()) {
            Integer key = entry.getKey();
            int[] value = entry.getValue();

            // todo change this to isHit()
            // tdodo change this to is parent
            if (!(this.moveResult.get(key).equals("0"))){                
                allHits.put(key, value);
            }
        }
        return allHits;
    }
    public int[] getLastHitN(int n){
        int highestMove = this.getHighestMoveNum();
        int nthHit = 0;
        for (int i = highestMove; i > 0; i--){
            // todo isHit()
            // todo change this -- bad encapsulation
            // is hit
            int[] coord = this.moveLocation.get(i);
            // System.out.println(Arrays.toString(coord));
            if (this.parent.isHit(coord[0], coord[1])){
                nthHit += 1;
                if (nthHit == n){
                    return this.moveLocation.get(i);
                }
            }
        }
        int[] doesNotExist = {-1};
        return doesNotExist; 
    }
    public boolean isLastHitN(int n){
        int highestMove = this.getHighestMoveNum();
        int nthHit = 0;
        for (int i = highestMove; i > 0; i--){
            // todo isHit()
            // todo change this -- bad encapsulation
            // is hit
            int[] coord = this.moveLocation.get(i);
            // System.out.println(Arrays.toString(coord));
            if (this.parent.isHit(coord[0], coord[1])){
                nthHit += 1;
                if (nthHit == n){
                    return true;
                }
            }
        }
        return false; 
    }
}
