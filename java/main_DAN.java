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
        Board board = new Board("testBoard.txt");
        while (!(board.isGameDone())){
        // for (int i = 0; i < 50; i++){
            int[] coord = board.getBestGuess();
            String result = board.fireShot(coord[0], coord[1]);
            if (result.equals("hit")){
                board.sinkShip();
            }
        }
        
    }
}