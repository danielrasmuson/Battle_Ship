import java.util.*;
public class ShipStatus {
    public Map<String, Integer> pieceLength;
    public ShipStatus(){
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
    public void setSunkShip(String shipName){
        this.pieceLength.put(shipName, 0);
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
}