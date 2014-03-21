// // // http://www.datagenetics.com/blog/december32011/
// STRAGETY
// Two functions
// ship hunt.
// Parries the board like a checkerboard until it hits something. Also takes into consideration square probabilities. Wanting to hit open space more because they are more chances to hit a ship. Once something is hit used sinkShip. Marks squares as impossible as it goes along.
// Sink ship.
// Looks at ships that are already sunk and takes the largest length. Then picks  a square adjacent to it.
// Note. I should make a move history thing.
import java.util.Arrays;
public class main_DAN {
    public static void main(String[] args) {

        // int numOfBoards = 9;
        int boardNum = 2;
        int total = 0;
        // for (int i = 1; i < numOfBoards+1; i++){
        for (int i = boardNum; i < boardNum+1; i++){
            Board board = new Board("1000Boards\\"+Integer.toString(i)+".txt");
            
            // for (int z = 0; z < 65; z++){
            int movecounter = 1;
            stuckInLoop:
            while (!board.isGameDone()){
                int[] coord = board.getBestGuess();
                board.fireShot(coord[0], coord[1]);
                if (board.isHit(coord[0], coord[1])){
                    board.sinkShip();
                }

                if (movecounter > 100){
                    System.out.println("problem in main loop");
                    break stuckInLoop;
                }

            }
            total += board.getMoves().getHighestMoveNum();
            board.print();
        }
        System.out.print("Average Moves:");
        System.out.println(total);
    }
}