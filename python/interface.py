class Interface():
    """ Do not use all the functions here (dont get board and see where everything is) 
            Feel free to use anything while testing

        You must use - fireShot
        And you can use - isSolved or getShotsFired

    """
    def __init__(self, fileName):
        self.fired = 0
        self.pieceKey = {"A":"Carrier","B":"Battleship","C":"Cruiser","S":"Submarine","D":"Destroyer"}

        self.setBoard(fileName)

    def getBoard(self):
        return self.board

    def setBoard(self, fileName):
        # note how this is one directory back
        textFile = open("..\\"+fileName,"r") #todo dynamic
        fullText = textFile.read()
        textFile.close()
        
        # the reason we have both self.board and self.orginalBoard
        # is because we are marking up self.board as the game goes on
        # when a shot is fired, if it hits a ship we change a C for example
        # to a 1
        self.board = []
        self.orginalBoard = [] # to print at the end board
        for line in fullText.split("\n"):
            self.board.append(line.split())
            self.orginalBoard.append(line.split())
        #todo add check board


    def fireShot(self, x, y):
        self.fired += 1 # increment shots
        shot = self.board[y][x]
        if shot == "0": #missed
            return "0"
        else: #hit
            self.board[y][x] = "1"
            if self.isSolved():
                self.setDone()

            if shot not in str(self.board): #sunk
                return self.pieceKey[shot]
            else: #just hit it
                return "1"

    def getShotsFired(self):
        return self.fired

    def printBoard(self):
        for line in self.board:
            print(" ".join(line))
    def printOriginalBoard(self):
        for line in self.orginalBoard:
            print(" ".join(line))

    def isSolved(self):
        # if there are letters in the board it will fail
        try:
            for row in self.board:
                for char in row:
                    int(char)
            return True
        except:
            return False

    def setDone(self):
        """Done Execution"""
        self.printOriginalBoard()
        print("Turns:", self.getShotsFired())
        self.printBoard()