import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
class Interface {
    // todo the orginal board and board are currently the same
    // dont want to spend the time to figure out how to fix it
    
    /** Do not use all the functions here (dont get board and see where everything is) 
            Feel free to use anything while testing

        You must use - fireShot
        And you can use - isSolved or getShotsFired

    */

    public int fired;  
    public Map<String, String> pieceKey;
    public String[][] board;
    public String[][] orginalBoard;

    public Interface(String fileName){
        this.fired = 0;

        // The following is equivalent to Pythons dictionary
        // for example 
        // System.out.println(this.pieceKey.get("A"));
        // will return "Carrie"
        this.pieceKey = new HashMap<String, String>();
        this.pieceKey.put("A", "Carrier");
        this.pieceKey.put("B", "Battleship");
        this.pieceKey.put("C", "Cruiser");
        this.pieceKey.put("S", "Submarine");
        this.pieceKey.put("D", "Destroyer");

        setBoard(fileName);
    }
    public void setBoard(String fileName){
        // read file and add to board array
        String content = null;
        File file = new File("..\\"+fileName); //for ex foo.txt
        try {
           FileReader reader = new FileReader(file);
           char[] chars = new char[(int) file.length()];
           reader.read(chars);
           content = new String(chars);
           reader.close();
        } catch (IOException e) {
           e.printStackTrace();
        }

        // appending to this board and orginal board
        String[][] board = new String[10][10];
        String[][] orginalBoard = new String[10][10];
        String[] lineList = content.split("\n");
        for (int i = 0; i < lineList.length; i++){
            String[] charList = lineList[i].split(" ");

            // for some reason this one has an extra char
            int last = charList.length-1;
            charList[last] = charList[last].substring(0,1);           

            board[i] = charList;
            orginalBoard[i] = charList;
        }


        this.board = board;
        this.orginalBoard = orginalBoard;
    }
    public void printBoard(){
        for (int i = 0; i < this.board.length; i++){
            for (int x = 0; x < this.board[i].length; x++){
                System.out.print(this.board[i][x]);
            }
            System.out.println();
        }
    }
    public void printOrginalBoard(){
        for (int i = 0; i < this.orginalBoard.length; i++){
            for (int x = 0; x < this.orginalBoard[i].length; x++){
                System.out.print(this.orginalBoard[i][x]);
            }
            System.out.println();
        }
    }
    public int getShotsFired(){
        return this.fired;
    }  
    public String fireShot(int x, int y){
        //Zach is waaaay cooler than Dan
        //Dan also needs to learn to comment his code
        //I can't understand half the stuff in this method
        this.fired += 1;
        String shot = this.board[y][x];
        if (shot.equals("0")){
            return "0";
        }
        else{
            this.board[y][x] = "1";
            
            // turn this.board into a string
            String strBoard = "";
            for (int i = 0; i < this.board.length; i++){
                for (int z = 0; z < this.board[i].length; z++){
                    strBoard += this.board[i][z];
                }
            }

            // the shot is not in the board i.e. its sunk
            if (strBoard.indexOf(shot) == -1){
                return this.pieceKey.get(shot);
            }
            else{ // its just a hit
                return "1";
            }
        }
    }
    public boolean isSolved(){
        // if all the characters are numbers the puzzled is solved
        try{
            for (int i = 0; i < this.board.length; i++){
                for (int x = 0; x < this.board[i].length; x++){
                    Integer.parseInt(this.board[i][x]);
                }
            }
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    public void setDone(){
        this.printOrginalBoard();
        System.out.println("Turns:"+this.getShotsFired());
        this.printBoard();
    }
}

