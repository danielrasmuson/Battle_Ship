public class FindingShip {
    private Board parent;
    public FindingShip(Board parent){
        this.parent = parent;
    }
    private void setBoardSquare(int x , int y, String placeChar){
        this.board[y][x] = placeChar;
    }
    private int getGuessScore(int x, int y){
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
    private int[] getBestGuess(){
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
}
