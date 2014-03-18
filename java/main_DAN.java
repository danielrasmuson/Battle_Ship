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

        int numOfBoards = 1;
        for (int i = 1; i < numOfBoards+1; i++){
            // Board board = new Board("1000Boards\\"+Integer.toString(i)+".txt");
            Board board = new Board("1000Boards\\"+"1"+".txt");
            
            // for (int x = 0; x < 44; x++){
            while (!(board.isGameDone())){
            // for (int z = 0; z < 30; z++){
                // System.out.println("----------------------------");
                // board.print();
                // int bestX = -1;
                // int bestY = -1;
                // int highestScore = 0;
                // for (int y = 0; y < 10; y++){
                //     for (int x = 0; x < 10; x++){
                //         if (board.isSquareUnknown(x,y)){
                //             int score = board.getGuessScore(x,y);
                //             System.out.print(x);
                //             System.out.print("-");
                //             System.out.print(y);
                //             System.out.print(" -- ");
                //             System.out.println(score);
                //             if (score > highestScore){
                //                 bestY = y;
                //                 bestX = x;
                //                 highestScore = score;
                //             }
                //         }
                //     }
                // }
                // System.out.println("");
                // System.out.println("----------------------------");


                int[] coord = board.getBestGuess();
                // System.out.println();
                // System.out.println();
                // System.out.println(Arrays.toString(coord));
                // board.print();
                // System.out.println();
                // System.out.println();
                String result = board.fireShot(coord[0], coord[1]);
                if (result.equals("hit")){
                    board.sinkShip();
                }
            }
            System.out.println("");
            System.out.println("");
            board.print();
        }        
    }
}