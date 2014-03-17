import java.util.*;
public class Board {
    public String[][] board;
    public int checkerBoard;
    public ShipStatus ships;
    public MoveHistory moves;
    public Interface battleShip;

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

        // init - pick a random number for the checkerBoard
        // todo add random back in was causing problems
        // Random rand = new Random();
        // int randomNum = rand.nextInt((1 - 0) + 1) + 0;
        this.checkerBoard = 0;

        // init ships
        ShipStatus ships = new ShipStatus();
        this.ships = ships;

        // init - move history 
        this.moves = new MoveHistory();
    }
    // ---------- GENERAL -----------
    public MoveHistory getMoves(){
        return this.moves;
    }
    public boolean isGameDone(){
        return battleShip.isSolved();
    }
    public void print(){
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                System.out.print(this.board[y][x]);
            }
            System.out.println();
        }
    }
    public ShipStatus getShips(){
        return this.ships;
    }

    // ---------- FINDING SHIP -----------
    public int[] getNextSquare(){
        /*
            Pick the next square on the checkerboard that is unsolved
        */
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                if (isSquareOnCheckboard(x,y)){
                    if (isSquareUnknown(x,y)){
                        int[] coords = {x, y};
                        return coords;
                    }
                }
            }
        }

        // should never get here
        int[] endOfBoard = {-1, -1};
        return endOfBoard;
    }
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
    public boolean isSquareMiss(int x, int y){
        // if the squre is not on the board
        int size = this.board.length-1;
        if (x < 0 || y < 0 || x > size || y > size){
            return false;
        }

        // look at the board to see if I shot there
        if (this.board[y][x].equals("0")){
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
            return "sunk";//hopefully this will get me out of the loop I'm stuck in
        }

        // adds the move to the move history
        String result = battleShip.fireShot(x,y);
        this.moves.addMove(x,y,result);

        if (result.equals("0")){
            this.board[y][x] = "0";
            return "miss";
        } else if(result.equals("1")){
            this.board[y][x] = "1";
            return "hit";
        } else{ // it should only sink a ship when its called from sinkingShip 
            this.board[y][x] = "1";
            // if there are no ships left the game is over
            if (ships.noShips()){
                return "done";
            }
            else{
                this.ships.setSunkShip(result);
                return "sunk";
            }     
        }
    }
    public int getGuessScore(int x, int y){
        /*
            Currently Guesses are scored by the ones who have the highest
            number of unknowns surronding them.
        */ 
        // We dont need to find anything longer then the biggest ship on the field
        int longestShip = this.getShips().getLongestShip();

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
                for (int i = 1; i < longestShip; i++){

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
        return (int) ((squareTotal/((longestShip-1)*4.0))*100);
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
    public String getLine(int[] point1, int[] point2){
        // you need to know if the ship you found is going up or down 
        if (point1[0] == point2[0]){
            return "vertical";
        } else{
            return "horziontal";
        }
    }
    public void fireOnShipLine(){
        /*
            figures out what direction the ship is facing
            then takes the next shot at it
        */
        int[] endpoint1 = this.getMoves().getLastHitN(1); // most recent hit
        int[] endpoint2 = this.getMoves().getLastHitN(2); // most recent hit

        String direction = this.getLine(endpoint1,endpoint2);

        // todo add horizontal
        int xY; int yYes; int xYes;
        if (direction.equals("vertical")){
            xY = 1; // y
            yYes = 1;
            xYes = 0;
        } else{
            xY = 0; // y
            xYes = 1;
            yYes = 0;
        }

        // it has a greater y
        int[] lowerEndpoint = new int[2];
        int[] higherEndpoint = new int[2];
        if (endpoint1[xY] > endpoint2[xY]){
            lowerEndpoint = endpoint1;
            higherEndpoint = endpoint2;
        }else{
            lowerEndpoint = endpoint2;
            higherEndpoint = endpoint1;
        }
        int x = lowerEndpoint[0];
        int y = lowerEndpoint[1];
        String result = "miss";
        if (this.isSquareUnknown(x+(1*xYes),y+(1*yYes))){
            result = this.fireShot(x+(1*xYes),y+(1*yYes));
        }

        x = higherEndpoint[0];
        y = higherEndpoint[1];
        // basically as long as its not sunk
        if (result.equals("miss") || result.equals("hit")){
            if (this.isSquareUnknown(x-(1*xYes),y-(1*yYes))){
                result = this.fireShot(x-(1*xYes),y-(1*yYes));
            }
        }

        // if it doesn't hit anything know it means they are too different ships
        if (result.equals("miss")){
            System.out.println("TWO SHIPS NEXT TO ONE ANOTHER");
        }

        // it needs to keep doing this until we get result == sunk
        if (result.equals("hit")){
            this.fireOnShipLine();
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
        int[] lastHit = this.getMoves().getLastHitN(1); // most recent hit
        int fX = lastHit[0];
        int fY = lastHit[1];
        String result = "miss";
        if (this.isSquareUnknown(fX+1, fY)){
            result = this.fireShot(fX+1, fY);
        } 
        if (result.equals("miss") && this.isSquareUnknown(fX-1, fY)){
            result = this.fireShot(fX-1, fY);
        }
        if (result.equals("miss") && this.isSquareUnknown(fX, fY+1)){
            result = this.fireShot(fX, fY+1);
        }
        if (result.equals("miss") && this.isSquareUnknown(fX, fY-1)){
            result = this.fireShot(fX, fY-1);
        }
    }
}
