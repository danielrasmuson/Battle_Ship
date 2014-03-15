def main_YOURNAME(board):
    # fire shots in from top left to bottom right
    for y in range(10):
        for x in range(10):
            board.fireShot(x,y)

            #dont need this but you can use it
                #interface will stop when game is over
            if board.isSolved():
                return