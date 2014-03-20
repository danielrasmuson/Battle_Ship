import java.util.*;
public class Board {
    private String[][] board;
    private ShipStatus ships;
    private MoveHistory moves;
    private Interface battleShip;

    public Board(String fileName){
        this.battleShip = new Interface(fileName);


        String[][] board = new String[10][10];
        this.board = board;
        // turn it into string
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                this.board[y][x] = "?";
            }
        }

        // init ships
        ShipStatus ships = new ShipStatus(this);
        this.ships = ships;

        // init - move history 
        this.moves = new MoveHistory(this);
    }
    // ---------- GENERAL -----------
    public MoveHistory getMoves(){
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

    // ---------- FINDING SHIP -----------
    public boolean isSquareUnknown(int x, int y){
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
    public boolean isSquareMissOrOffBoard(int x, int y){
        // if the squre is not on the board
        int size = this.board.length-1;
        if (x < 0 || y < 0 || x > size || y > size){
            return true;
        }

        // look at the board to see if I shot there
        if (this.board[y][x].equals("0")){
            return true;
        } else{
            return false;
        }
    }
    public void setBoardSquare(int x, int y, String chr){
        this.board[y][x] = chr;
    }
    public String fireShot(int x, int y){
        // board is the only function that should know what this stuff means

        // key
        // ? --> Unknown
        // 0 --> Miss
        // H --> Hit
        // S --> Sunk
        if (!(this.isSquareUnknown(x,y))){
            System.out.print("square already known.. bad! -[");
            System.out.print(x);
            System.out.print(", ");
            System.out.print(y);
            System.out.println("]");
            return "bad";//hopefully this will get me out of the loop I'm stuck in
        }

        System.out.println("");
        System.out.println("");
        this.print();
        String result = battleShip.fireShot(x,y);

        if (result.equals("0")){
            this.moves.addMove(x,y,"0");
            setBoardSquare(x, y, "0");
            return "0";
        } else if(result.equals("1")){
            this.moves.addMove(x,y,"H");
            setBoardSquare(x, y, "H");
            return "H";
        } else{
            // todo assume I hit the ship in order
            this.moves.addMove(x,y,"H");
            setBoardSquare(x, y, "S");

            // System.out.println("going into sunk ship!!!");
            this.getShips().setSunkShip(result);

            // if there are H's are the board we have a serious problem it means
            // we hit a ship that is not part of the ship we sunk
            // we should take that move and put it into the cirlceShip if its only one hit
            // if its two hits we use the fireOnShip line
            int hCount = 0;
            for (int b = 0; b < 10; b++){
                for (int a = 0; a < 10; a++){
                    if (this.board[b][a] == "H"){
                        hCount += 1;
                    }
                }
            }
            if (hCount > 0){
                System.out.println("H COUNT");
                System.out.println("Time for drastic Action!");
                System.out.println(Arrays.toString(this.getMoves().getHitN(1)));
                // System.out.println(hCount);
                // this.print();
                // // make sure the H is correctly found by last hit
                // System.out.println("");
                // System.out.println("");
                // System.out.println("");
                // if (hCount == 1){                    

                // I dont know I might have to put this into a if count statment
                this.sinkShip();

                // }
                // this.print();
                System.out.println("END OF H COUNT");
            }


            // if there are no ships left the game is over
            // this.board[y][x] = "S";
            if (ships.noShips()){
                return "S";
            }
            else{
                return "S";
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

        // shouldn't get in here
        if (bestX == -1 || bestY == -1){
            System.out.println("");
            System.out.println("");
            System.out.println("COULD NOT FIND A BEEST GUESS");
            for (int y = 0; y < 10; y++){
                for (int x = 0; x < 10; x++){
                    if (this.isSquareUnknown(x,y)){
                        int score = this.getGuessScore(x,y);
                        if (score > highestScore){
                            System.out.print(x);
                            System.out.print(" - ");
                            System.out.print(y);
                            System.out.print(" - ");
                            System.out.println(score);
                        }
                    }
                }
            }
            this.print();
            System.out.println("END");
            System.out.println("");
            System.out.println("");
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
        int[] point1 = this.getMoves().getLastHitOrMiss(1); // most recent hit
        int[] point2 = this.getMoves().getLastHitOrMiss(2); // most recent hit
        if (point1[0] == point2[0]){
            return "v"; // vertical
        } else{
            return "h"; // horizontal
        }
    }
    public String fireOnShipLine(){
        String direction = this.getLineLastTwoHits();
        String result = "H";

        // I.E. not sunk
        while (result.equals("H") || result.equals("0")){
            result = fireNextShotOnLine(direction);
        }
        return result;
    }
    public int[] nextSquare(int[] coord, String direction, int upDown){
        // updown is negative or positive 1 depending on direction
        int[] newCoord = new int[2];
        if (direction.equals("v")){
            newCoord[1] = coord[1]+1*upDown;
            newCoord[0] = coord[0];
            return newCoord;
        }else{
            newCoord[0] = coord[0]+1*upDown;
            newCoord[1] = coord[1];
            return newCoord;
        }
    }
    public void fireCirclingShip(){
        /*
            Once you hit a ship you guess around the ship trying to find 
            another hit so you can determine the direction
        */
        // todo make this code a little cleaner
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

        int[] lastHit = this.getMoves().getHitN(1); // most recent hit
        int fX = lastHit[0];
        int fY = lastHit[1];
        String result = "0";
        if (this.isSquareUnknown(fX+1, fY)){
            result = this.fireShot(fX+1, fY);
            // System.out.println("1 <-- here: "+result);
        } 
        if (result.equals("0") && this.isSquareUnknown(fX-1, fY)){
            result = this.fireShot(fX-1, fY);
            // System.out.println("2 <-- here: "+result);
        }
        if (result.equals("0") && this.isSquareUnknown(fX, fY+1)){
            result = this.fireShot(fX, fY+1);
            // System.out.println("3 <-- here: "+result);
        }
        if (result.equals("0") && this.isSquareUnknown(fX, fY-1)){
            result = this.fireShot(fX, fY-1);
            // System.out.println("4 <-- here: "+result);
        }
    }
    /*
        figures out what direction the ship is facing
        then takes the next shot at it
    */
    public String fireNextShotOnLine(String direction){
        int[] lastHit = this.getMoves().getHitN(1); // most recent hit

        // depending if we are going down or up on the board
        int[] forwardBackward = {1, -1};
        for (int leftRight : forwardBackward){
            int[] nSquare = this.nextSquare(lastHit, direction, leftRight);
            boolean nMissing = isSquareMissOrOffBoard(nSquare[0], nSquare[1]);
            while (!nMissing){ // while the next square is not a miss
                if (isSquareUnknown(nSquare[0], nSquare[1])){
                    return this.fireShot(nSquare[0], nSquare[1]);
                }
                nSquare = this.nextSquare(nSquare, direction, leftRight);
                nMissing = isSquareMissOrOffBoard(nSquare[0], nSquare[1]);
            }
        }
        return "notLine";
    }
    public boolean isHitOrMiss(int x, int y){
        if (board[y][x].equals("?") || board[y][x].equals("0")){
            return false;
        }else{
            return true;
        }
    }
    public boolean isHit(int x, int y){
        if (board[y][x].equals("H")){
            return true;
        }else{
            return false;
        }
    }
}