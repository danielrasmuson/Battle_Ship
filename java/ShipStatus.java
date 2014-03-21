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
        if (parent.getHCount() == 0){
            for (int z = 0; z < getShipLength(shipName); z++){
                // the reason im choosing getHitN(1) is because I anticpate it removing the last hit
                // so it will also be the most recent hit
                int[] hit = this.parent.getMoves().getHitN(1);

                // todo dont really like th S here, ship status shouldn't know that
                this.parent.setBoardSquare(hit[0], hit[1], "S");
                this.parent.getMoves().setMoveResult(hit[0], hit[1],  "S");
            } 
        } else{
            // we hit mutliple ships and we are going to have to be clever in how we 
            // write them as sunk
            
            // // int[] hit = this.parent.getMoves().getHitN(2);
            // for (int z = 0; z < getShipLength(shipName); z++){
            //     // the reason im choosing getHitN(1) is because I anticpate it removing the last hit
            //     // so it will also be the most recent hit
            //     int[] hit = this.parent.getMoves().getHitN(1);

            //     // todo dont really like th S here, ship status shouldn't know that
            //     this.parent.setBoardSquare(hit[0], hit[1], "S");
            //     this.parent.getMoves().setMoveResult(hit[0], hit[1],  "S");
            // } 
            int[] hit = this.parent.getMoves().getHitN(1);
            System.out.println(Arrays.toString(hit));
            int x = hit[0];
            int y = hit[1];

            // TESTS THE UNKNOWNS GOING LEFT RIGHT UP ANDDOWN
            // this controls vertical or hoziontal        
            int[][] xOrY = {{1,0},{0,1}};
            for (int[] axis : xOrY){
                int xMod = axis[0];
                int yMod = axis[1];

                // int upDownTotal = 0;
                // things controls increasing or decreasing
                int[] upDown = {1,-1};
                for (int direction : upDown){
                    int directionTotal = 0;
                    cantSkipGaps:
                    for (int i = 1; i < getShipLength(shipName); i++){

                        // if you * by 0 nothing happens
                        int sX = x+(i*direction*xMod);
                        int sY = y+(i*direction*yMod);
                        if (!parent.isSquareOffBoard(sX,sY) && parent.isHit(sX,sY)){
                            directionTotal += 1;
                        }else{
                            // want to stop if something is not correct
                            break cantSkipGaps;
                        }
                    }
                    System.out.print("The Total for this direction is: ");
                    System.out.println(directionTotal);
                    System.out.println(Arrays.toString(axis)); // y
                    System.out.println(direction); // down (higher)
                    System.out.println("");
                }
            }
            System.out.println("");
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