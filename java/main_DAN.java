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
        int boardNum = 1;
        int total = 0;
        // for (int i = 1; i < numOfBoards+1; i++){
        for (int i = boardNum; i < boardNum+1; i++){
            Board board = new Board("1000Boards\\"+Integer.toString(i)+".txt");
            
            int[] coord = board.getBestGuess();
            board.fireShot(6, 1);
            // board.fireShot(6, 2);
            // board.fireShot(6, 3);
            // board.fireShot(6, 4);
            // board.fireShot(6, 5);
            // board.fireShot(6, 6);
            board.fireCirclingShip();
            String direction = board.getLineLastTwoHits();
            board.getMoves().print();
            board.fireNextShotOnLine(direction);
            // board.fireNextShotOnLine(direction);
            // board.fireOnShipLine();

            board.print();
            // for (int z = 0; z < 60; z++){
            // // while (!board.isGameDone()){
            //     int[] coord = board.getBestGuess();
            //     String result = board.fireShot(coord[0], coord[1]);
            //     if (result.equals("H")){
            //         board.sinkShip();
            //     }
                
            //     board.print();
            //     // System.out.println("");
            //     // System.out.println("");
            // }
            // board.getMoves().print();
            // System.out.println(board.getMoves().getHighestMoveNum());    
            total += board.getMoves().getHighestMoveNum();
        }
        System.out.print("Average Moves:");
        System.out.println(total);
    }
}