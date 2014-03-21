import java.util.*;
public class ShipStatus {
    public Map<String, Integer> pieceLength;
    private Board parent;
    public ShipStatus(Board parent){
        this.parent = parent;

        this.pieceLength = new HashMap<String, Integer>();
        // 0 stands for the ship is still alive
        this.pieceLength.put("Carrier", 5);
        this.pieceLength.put("Battleship", 4);
        this.pieceLength.put("Cruiser", 3);
        this.pieceLength.put("Submarine", 3);
        this.pieceLength.put("Destroyer", 2);
        // System.out.println(this.pieceLength.get("Destroyer"));
    }
    public int getLongestShip(){
        Integer highest = 0;
        for (Integer value : this.pieceLength.values()) {
            if (value > highest){
                highest = value;
            }
        }
        return (int) highest;
    }
    public int getSmallestShip(){
        Integer lowest = 100; // could you really havea  ship with more then 5
        for (Integer value : this.pieceLength.values()) {
            if (value < lowest){
                lowest = value;
            }
        }
        return (int) lowest;
    }
    public void setSunkShip(String shipName){
        for (int z = 0; z < getShipLength(shipName); z++){
            
            // the reason im choosing getHitN(1) is because I anticpate it removing the last hit
            // so it will also be the most recent hit
            int[] hit = this.parent.getMoves().getHitN(1);

            // todo dont really like th S here, ship status shouldn't know that
            this.parent.setBoardSquare(hit[0], hit[1], "S");
            this.parent.getMoves().setMoveResult(hit[0], hit[1],  "S");
        }
        this.pieceLength.remove(shipName);
    }
    public boolean noShips(){
        /*
            Are there still ships that havent been sunk?
        */
        for (Integer value : this.pieceLength.values()) {
            if (value > 0){
                return false;
            }
        }
        return true;
    }
    public int getShipLength(String shipName){
        return this.pieceLength.get(shipName);
    }
}