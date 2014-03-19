from random import randint

def placePiece(length, pieceChr, board):
    """
        Write the piece to the board without
        going off the board or overwritting 
        another piece
    """
    # will the piece be horizontal or vetical
    direction = ["vertical","horizontal"]

    # coords starting in the top right
    coordOkay = False
    while coordOkay == False:
        x = randint(0,len(board)-1)
        y = randint(0,len(board)-1)
        orientation = direction[randint(0,1)]
        coordOkay = isCoordOkay(x,y,orientation,length,board)

    # todo should be a better way to do this
    if orientation == "vertical":
        yY = 1
        xY = 0
    else:
        yY = 0
        xY = 1

    for i in range(length):
        board[y+i*yY][x+i*xY] = pieceChr

    return board

def isCoordOkay(x,y,orientation,length,board):
    # a little confusing but it says if
    # x should be increasing or if y
    # should be increasing
    if orientation == "vertical":
        yY = 1
        xY = 0
    else:
        yY = 0
        xY = 1

    for i in range(length):
        # if its not on the board it should be false
        if y+i*yY > len(board)-1 or x+i*xY > len(board)-1:
            return False
        if board[y+i*yY][x+i*xY] != "0":
            return False
    return True

def createBoard(fileName):
    #init blank bloard
    board = []
    for i in range(10):
        row = []
        for x in range(10):
            row.append("0")
        board.append(row)

    # Carrier "A" length 5 - Battleship "B" lenght 4 etc
    pieceKey = {
    "Carrier": {"Length":5,"Char":"A"}, 
    "Battleship": {"Length":4,"Char":"B"}, 
    "Cruiser": {"Length":3,"Char":"C"}, 
    "Submarine": {"Length":3,"Char":"S"}, 
    "Destroyer": {"Length":2,"Char":"D"}
    } 

    for k,v in pieceKey.items():
        # print(v)
        board = placePiece(v["Length"], v["Char"], board)

    outText = ""
    for row in board:
        outText += " ".join(row)
        outText += "\n"
    outText = outText[:-1] # stip last new line 

    textFile = open(fileName,"w")
    textFile.write(outText)
    textFile.close()

counter = 1
while counter < 10001:
    createBoard("Boards\\"+str(counter)+".txt")
    counter += 1