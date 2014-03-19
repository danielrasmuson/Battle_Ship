import java.util.*;
public class Board {

    public ShipStatus ships;
    public Moves moves;
    // public FindingShip find;

    public Board(String fileName){
        // init ships, move history, finding ship
        this.ships = new ShipStatus();
        this.moves = new Moves(this, fileName);
        // this.find = new FindingShip(this);
    }
    public Moves getMoves(){
        return this.moves;
    }
    public boolean isGameDone(){
        return battleShip.isSolved();
    }
    public ShipStatus getShips(){
        return this.ships;
    }
    public void print(){
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                System.out.print(this.board[y][x]);
            }
            System.out.println();
        }
    }
    private boolean isSquareUnknown(int x, int y){
        // if the squre is not on the board
        int size = this.board.length-1;
        if (x < 0 || y < 0 || x > size || y > size){
            return false;
        }

        // look at the board to see if I shot there
        if (this.board[y][x].equals("?")){
            return true;
        } else{
            return false;
        }
    }
    private boolean isSquareMiss(int x, int y){
        // if the squre is not on the board
        int size = this.board.length-1;
        if (x < 0 || y < 0 || x > size || y > size){
            // if its off the board its not really missing
            // System.out.println("here!!! its off the board?");
            return true; // todo - maybe not the best
        }

        // look at the board to see if I shot there
        // System.out.println(this.board[y][x]);
        if (this.board[y][x].equals(this.getMoves().getMC())){ // get miss character
            return true;
        } else{
            return false;
        }
    }

    public boolean isSquareOnCheckboard(int x, int y){
        /*
            // pick even or odd squares
                // the smallest ship is two so if I only
                // hit black square on checkboard that is a good thing
                // isSquareOnCheckboard?        
        */
        if (y % 2 == this.checkerBoard){
            if (x % 2 == this.checkerBoard){
                return true;
            }
            else{
                return false;
            }
        }else{
            if (x % 2 == this.checkerBoard+1){
                return true;
            }
            else{
                return false;
            }
        }
    }
    public String fireShot(int x, int y){
        // maybe I dont need int x and int y not sure
        // if the square is already known I dont want to waste a shot
        // not that my program will but I dont want to take the chance
        if (!(this.isSquareUnknown(x,y))){
            System.out.println("square already known.. bad!");
            // todo probably a better solution
            return "bad";//hopefully this will get me out of the loop I'm stuck in
        }

        // adds the move to the move history
        String result = battleShip.fireShot(x,y);

        if (result.equals("0")){
            this.board[y][x] = "0";
            this.moves.addMove(x,y,"0");
            return "miss";
        } else if(result.equals("1")){
            this.board[y][x] = "H";
            this.moves.addMove(x,y,"1");
            return "hit";
        } else{ // it should only sink a ship when its called from sinkingShip 
            // set board to sunk
            // todo assume I hit the ship in order
            // todo renalbe this
            // int shipLength = this.getShips().getShipLength(result);
            // for (int z = 0; z < shipLength; z++){
            //     int[] hit = this.getMoves().getLastHitN(z+1);
            //     System.out.println(Arrays.toString(hit));
            //     this.print();
            //     this.board[hit[1]][hit[0]] = "S";
            // }

            // this.board[y][x] = "S";
            // if there are no ships left the game is over
            this.board[y][x] = "H";
            this.getShips().setSunkShip(result);
            if (ships.noShips()){
                this.moves.addMove(x,y,"done");
                return "done";
            }
            else{
                this.moves.addMove(x,y,result);
                return "sunk";
            }     
        }
    }
    public int getGuessScore(int x, int y){
        /*
            Currently Guesses are scored by the ones who have the highest
            number of unknowns surronding them.
        */ 
        // the reason I choose shortest is because we are less likely to hit a large ship
        // but we are more likely to hit the destroyer then the 3 would really count
        int shortestShip = this.getShips().getSmallestShip();

        int squareTotal = 0;

        // we already know what the square is
        if (!(isSquareUnknown(x,y))){
            return 0;
        }

        // TESTS THE UNKNOWNS GOING LEFT RIGHT UP ANDDOWN
        // this controls vertical or hoziontal        
        int[][] xOrY = {{1,0},{0,1}};
        for (int[] axis : xOrY){
            int xMod = axis[0];
            int yMod = axis[1];

            // things controls increasing or decreasing
            int[] upDown = {1,-1};
            for (int direction : upDown){
                cantSkipGaps:
                for (int i = 1; i < shortestShip; i++){

                    // if you * by 0 nothing happens
                    if (isSquareUnknown(x+(i*direction*xMod),y+(i*direction*yMod))){
                        squareTotal += 1;
                    }else{
                        // want to stop if something is not correct
                        break cantSkipGaps;
                    }
                }
            }
        }

        // a percentage of the highest possible
        // 4 sides
        return (int) ((squareTotal/((shortestShip-1)*4.0))*100);
    }
    public int[] getBestGuess(){
        int bestX = -1;
        int bestY = -1;
        int highestScore = 0;
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                if (this.isSquareUnknown(x,y)){
                    int score = this.getGuessScore(x,y);
                    if (score > highestScore){
                        bestY = y;
                        bestX = x;
                        highestScore = score;
                        // just saves some time
                        if (score == 100){ // i.e. 100%
                            int[] coord = {bestX, bestY};
                            return coord; 
                        }
                    }
                }
            }
        }

        int[] coord = {bestX, bestY};
        return coord;
    }

    // ---------- SINKING SHIP -----------
    public void sinkShip(){
        this.fireCirclingShip(); 
        this.fireOnShipLine();
    }
    public String getLineLastTwoHits(){
        // you need to know if the ship you found is going up or down 
        int[] point1 = this.getMoves().getLastHitN(1); // most recent hit
        int[] point2 = this.getMoves().getLastHitN(2); // most recent hit
        if (point1[0] == point2[0]){
            return "v"; // vertical
        } else{
            return "h"; // horizontal
        }
    }
    public String fireOnShipLine(){
        String direction = this.getLineLastTwoHits();
        String result = "hit";
        while (result.equals("hit") || result.equals("miss")){
            result = fireNextShotOnLine(direction);
            // System.out.println(result);
        }
        return result;
    }
    public int[] nextSquare(int[] coord, String direction, int upDown){
        // updown is negative or positive 1 depending on direction
        if (direction.equals("v")){
            coord[1] = coord[1]+1*upDown;
            return coord;
        }else{
            coord[0] = coord[0]+1*upDown;
            return coord;
        }

    }
    public void fireCirclingShip(){
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
    // todo restructure this function its a mess
    public String fireNextShotOnLine(String direction){
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
    public boolean isHit(int x, int y){
        if (board[y][x].equals("?") || board[y][x].equals("0")){
            return false;
        }else{
            return true;
        }
    }
}