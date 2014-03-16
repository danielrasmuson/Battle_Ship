import java.util.*;
public class Board {

    // board
    public String[][] board;
    public int checkerBoard;
    public boolean sinkShip;
    public ShipStatus ships;
    public MoveHistory moves;

    public Board(){
        String[][] board = new String[10][10];
        this.board = board;
        // turn it into string
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                this.board[y][x] = "?";
            }
        }

        // init - pick a random number for the checkerBoard
        Random rand = new Random();
        int randomNum = rand.nextInt((1 - 0) + 1) + 0;
        this.checkerBoard = randomNum;

        // init - when we haven't found a ship we aren't trying to sink anything
        this.sinkShip = false;

        // init ships
        ShipStatus ships = new ShipStatus();
        this.ships = ships;

        // init - move history 
        this.moves = new MoveHistory();

    }
    public boolean sinkingShip(){
        // this means you hit a ship and you are in the process of sinking it
        return this.sinkShip;
    }
    public void setSinkShipOff(){
        this.sinkShip = false;
    }
    public void setSinkShipOn(){
        this.sinkShip = true;
    }
    /*
        Pick the next square on the checkerboard that is unsolved
    */
    public int[] getNextSquare(){
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
        if (this.board[y][x].equals("?")){
            return true;
        } else{
            return false;
        }
    }
    /*
        // pick even or odd squares
            // the smallest ship is two so if I only
            // hit black square on checkboard that is a good thing
            // isSquareOnCheckboard?        
    */
    public boolean isSquareOnCheckboard(int x, int y){
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
    // maybe I dont need int x and int y not sure
    public String processResult(String result, int x, int y){
        // adds the move to the move history
        this.moves.addMove(x,y, result);

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
                return "sunk";
            }     
        }
    }
    public void print(){
        for (int y = 0; y < this.board.length; y++){
            for (int x = 0; x < this.board[y].length; x++){
                System.out.print(this.board[y][x]);
            }
            System.out.println();
        }
    }
    // public int[][] getFiringOrder(){
    //     // returns an array of points in most likely to least likely order
    //     // todo: I should probably include  orderingit by side lengths

    // }
    public MoveHistory getMoves(){
        return this.moves;
    }
}
