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
        Board board = new Board();
        Interface battleShip = new Interface("testBoard.txt");
        // System.out.println(board.checkerBoard);

        // ship hunt
        // I should add something to cancel squares that are not possible
            // if I have already sunk the 2 I dont need to check cravess that are less then 2 anymore


        // while its not solved

        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                String rawResult = battleShip.fireShot(x, y);
                board.processResult(rawResult, x, y);
            }
        }
        // battleShip.printBoard();
        // System.out.println("\n");
        // board.printBoard(); 


        // gameDone;
        // while (!(battleShip.isSolved())){

        //     // while we haven't found a ship and sinking ship is off
        //     if (!(board.sinkingShip())){
        //         int[] coord = board.getNextSquare(); // see the next square function for all the details
        //         String resultRaw = battleShip.fireShot(coord[0], coord[1]);
        //         String result = board.processResult(resultRaw, coord[0], coord[1]);
        //         if (result.equals("hit")){
        //             board.setSinkShipOn();
        //         }
        //     }
        //     // we hit a ship and we want to try and sink it
        //     else{
        //         // we circle around (see getFiringOrder) until we find a sequence
        //         int[][] shots = getFiringOrder(x,y);
        //         for (int i = 0; i < shots.length; i++){
        //             int[] coord = shots[i];
        //             String resultRaw = battleShip.fireShot(coord[0], coord[1]);
        //             String result = board.processResult(resultRaw, coord[0], coord[1]);

        //             // we found a sequence
        //             if (result.equals("hit")){
        //                 int[][] shotsInRow = getSinkingShipLine();
        //                 // for shot in shotsInRow:
        //                 //     result = fireshot
        //                 //     result = processResult
        //                 //     ship sunk?
        //                 //     break 
        //             }
        //             else if (result.equals("done")){
        //                 break gameDone;
        //             }
        //         }
        //         // where did it orginally hit?
        //         // finds if its horizontal or vertical
        //             // by guessing around the hit spot
        //             // guessing at longest lengths first 
        //             // returns an array of points in most likely to least likely order

        //         // if we get a secondhit it continues in that line until it sinks a ship

        //         // once ship was sunk checks hits verses the size of the ship 
        //         // to determine if it was a clean hit 

        //     }
        // }
    }
}


// thought on how to make a random board
// gets two random numbers 0-9
// asks if it can put those down
// if yes it does it 
// if not it doesn't