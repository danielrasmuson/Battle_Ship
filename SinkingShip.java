public class SinkingShip {
    private Board parent;
    public SinkingShip(parent){
        this.parent = parent;
    }
    private void sinkShip(){
        this.fireCirclingShip(); 
        this.fireOnShipLine();
    }
    private String getLineLastTwoHits(){
        // you need to know if the ship you found is going up or down 
        int[] point1 = this.getMoves().getLastHitN(1); // most recent hit
        int[] point2 = this.getMoves().getLastHitN(2); // most recent hit
        if (point1[0] == point2[0]){
            return "v"; // vertical
        } else{
            return "h"; // horizontal
        }
    }
    private String fireOnShipLine(){
        String direction = this.getLineLastTwoHits();
        String result = "hit";
        while (result.equals("hit") || result.equals("miss")){
            result = fireNextShotOnLine(direction);
            // System.out.println(result);
        }
        return result;
    }
    private int[] nextSquare(int[] coord, String direction, int upDown){
        // updown is negative or positive 1 depending on direction
        if (direction.equals("v")){
            coord[1] = coord[1]+1*upDown;
            return coord;
        }else{
            coord[0] = coord[0]+1*upDown;
            return coord;
        }
    }
    private void fireCirclingShip(){
        /*
            Once you hit a ship you guess around the ship trying to find 
            another hit so you can determine the direction
        */
        // todo make this code a little cleaner
        // todo make it pick a random order
        // todo mayb include the length or probability of a direction
        // todo here I should know that it probably wont be in the two sides next to it
        // [1, 3]
        // ??????????
        // ?0?0?010??
        // ??0?0010?? <-- see how it circled here
        // ??????1???
        // ??????1???
        // ??????1???
        // ??????????
        // ??????????
        // ??????????
        // ??????????

        int[] lastHit = this.getMoves().getLastHitN(1); // most recent hit
        int fX = lastHit[0];
        int fY = lastHit[1];
        String result = "miss";
        if (this.isSquareUnknown(fX+1, fY)){
            result = this.fireShot(fX+1, fY);
            // System.out.println("1 <-- here: "+result);
        } 
        if (result.equals("miss") && this.isSquareUnknown(fX-1, fY)){
            result = this.fireShot(fX-1, fY);
            // System.out.println("2 <-- here: "+result);
        }
        if (result.equals("miss") && this.isSquareUnknown(fX, fY+1)){
            result = this.fireShot(fX, fY+1);
            // System.out.println("3 <-- here: "+result);
        }
        if (result.equals("miss") && this.isSquareUnknown(fX, fY-1)){
            result = this.fireShot(fX, fY-1);
            // System.out.println("4 <-- here: "+result);
        }
    }
    /*
        figures out what direction the ship is facing
        then takes the next shot at it
    */
    private String fireNextShotOnLine(String direction){
        int[] lastHit = this.getMoves().getLastHitN(1); // most recent hit

        int[] nSquare = this.nextSquare(lastHit, direction, 1);
        boolean nMissing = isSquareMiss(nSquare[0], nSquare[1]);
        while (!nMissing){ // while the next square is not a miss
            if (isSquareUnknown(nSquare[0], nSquare[1])){
                return this.fireShot(nSquare[0], nSquare[1]);
            }
            nSquare = this.nextSquare(nSquare, direction, 1);
            nMissing = isSquareMiss(nSquare[0], nSquare[1]);
        }

        int[] pSquare = this.nextSquare(lastHit, direction, -1);
        boolean pMissing = isSquareMiss(pSquare[0], pSquare[1]);
        while (!pMissing){ // while the next square is not a miss
            // System.out.print("Backwards Looking at ");
            // System.out.println(Arrays.toString(pSquare));
            // this.print();
            if (isSquareUnknown(pSquare[0], pSquare[1])){
                return this.fireShot(pSquare[0], pSquare[1]);
            }
            // System.out.println("");
            pSquare = this.nextSquare(pSquare, direction, -1);
            pMissing = isSquareMiss(pSquare[0], pSquare[1]);
        }
        return "notLine";
    }
    private boolean isHit(int x, int y){
        if (board[y][x].equals("?") || board[y][x].equals("0")){
            return false;
        }else{
            return true;
        }
    }
}
