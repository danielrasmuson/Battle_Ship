import java.util.*;
public class Moves {
    private Map<Integer, int[]> moveLocation;
    private Map<Integer, String> moveResult;
    private Map<String, String> resultTable;
    private Board parent;
    private Interface battleShip;
    public String[][] board;

    public Moves(Board parent, String fileName) {
        this.moveLocation = new HashMap<Integer, int[]>();
        this.moveResult = new HashMap<Integer, String>();
        this.battleShip = new Interface(fileName);
        this.parent = parent;

        String[][] board = new String[10][10];
        this.board = board;
        // turn it into string
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                this.board[y][x] = "?";
            }
        }
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
    /* 
        get miss character
    */
    public String getMC(){
        return "0";
    }
    private HashMap<Integer, int[]> getAllHits(){
        // myMap = new HashMap<String, String>();
        // allHits = new HashMap<Integer, int[]>();
        HashMap allHits = new HashMap();
        int lastMove = this.getHighestMoveNum();
        for (Map.Entry<Integer, int[]> entry : moveLocation.entrySet()) {
            Integer key = entry.getKey();
            int[] value = entry.getValue();

            // todo change this to isHit()
            // tdodo change this to is parent
            if (!(this.moveResult.get(key).equals(getMC()))){                
                allHits.put(key, value);
            }
        }
        return allHits;
    }
    private int[] getLastHitN(int n){
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
    private boolean isLastHitN(int n){
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
    private String fireShot(int x, int y){
        if (!(this.isSquareUnknown(x,y))){
            System.out.println("square already known.. bad!");
            // todo probably a better solution
            return "bad";//hopefully this will get me out of the loop I'm stuck in
        }

        // adds the move to the move history
        String rawResult = battleShip.fireShot(x,y);
        
        

        int[] coord = {x,y};
        Integer currentMove = this.getHighestMoveNum()+1;
        this.moveLocation.put(currentMove, coord);
        this.moveResult.put(currentMove, result);

        if (result.equals("0")){
            this.parent.board[y][x] = "0";
            return "miss";
        } else if(result.equals("1")){
            this.parent.board[y][x] = "H";
            this.moves.addMove(x,y,"1");
            return "hit";
        } else{ // it should only sink a ship when its called from sinkingShip 
            // set board to sunk
            this.moves.addMove(x,y,"1");

            // todo assume I hit the ship in order
            int shipLength = this.getShips().getShipLength(result);
            for (int z = 0; z < shipLength; z++){
                // because if its out of range I shouldn't be searching
                if (this.getMoves().isLastHitN(z+1)){                
                    int[] hit = this.getMoves().getLastHitN(z+1);
                    // this.getMoves().print();
                    // System.out.println(Arrays.toString(hit));
                    // this.print();
                    this.parent.board[hit[1]][hit[0]] = "S";
                }
            }

            // this.parent.board[y][x] = "S";
            // if there are no ships left the game is over
            this.parent.board[y][x] = "H";
            this.getShips().setSunkShip(result);
            if (ships.noShips()){
                return "done";
            }
            else{
                this.moves.addMove(x,y,"1");
                return "sunk";
            }     
        }
    }
}
