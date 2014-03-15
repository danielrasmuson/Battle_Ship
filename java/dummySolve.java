public class dummySolve {
    public static void main(String[] args) {
        System.out.println("hello!");
        // main(board);
    }
    public static String main_YOURNAME(Interface board){
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                board.fireShot(x, y);

                if (board.isSolved()){
                    return "exit";
                }
            }
        }
        return "exit";
    }
}
