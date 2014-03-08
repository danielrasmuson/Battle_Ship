Program Requirments
Two Functions You Need To make
Setup Board
    Name the function setup_YOURNAME() - setup_DAN()
    list a 10x10 board
    Put down five - 'A' - for Carrier
    Put down four - 'B' - for BattleShip
    Put down three - 'C' - for Cruiser
    Put down three - 'S' - for Submarine
    Put down two - 'D' - for Destroyer
    Returns the file path for the board you made
    You are responsible for providing a correct board (I dont check)

See Below For Information on pieces OR See Example "testBoard.txt"


Solve Board
    Name the function setup_YOURNAME() - solve_DAN()
    Your program will take an instance of the interface class
    You must use board.fireShot(x,y) - will return a string of one of three
        "0" - miss
        "1" - hit
        Name of the ship that was sunk (ex: "Submarine")
    You are responsible of keeping tracking of a mock board the getBoard function is only testing

HOW WILL THE GAME BE SCORED
    You will make a board for your opponent
    They will solve it and the number of turns will be added to there total
    This will loop 1000 times
    Then it will switch
    The person with the lowest score wins

RULES FOR BATTLESHIP
Rules for BattleShip (a Milton Bradley Game)

Game Objective
The object of Battleship is to try and sink all of the other player's before they sink all of your ships. All of the other player's ships are somewhere on his/her board. You try and hit them by calling out the coordinates of one of the squares on the board. The other player also tries to hit your ships by calling out coordinates. Neither you nor the other player can see the other's board so you must try to guess where they are. Each board in the physical game has two grids: the lower (horizontal) section for the player's ships and the upper part (vertical during play) for recording the player's guesses.

Starting a New Game
Each player places the 5 ships somewhere on their board. The ships can only be placed vertically or horizontally. Diagonal placement is not allowed. No part of a ship may hang off the edge of the board. Ships may not overlap each other. No ships may be placed on another ship.

Once the guessing begins, the players may not move the ships.

The 5 ships are: Carrier (occupies 5 spaces), Battleship (4), Cruiser (3), Submarine (3), and Destroyer (2).

Playing the Game
Player's take turns guessing by calling out the coordinates. The opponent responds with "hit" or "miss" as appropriate. Both players should mark their board with pegs: red for hit, white for miss. For example, if you call out F6 and your opponent does not have any ship located at F6, your opponent would respond with "miss". You record the miss F6 by placing a white peg on the lower part of your board at F6. Your opponent records the miss by placing.

When all of the squares that one your ships occupies have been hit, the ship will be sunk. You should announce "hit and sunk". In the physical game, a red peg is placed on the top edge of the vertical board to indicate a sunk ship.

As soon as all of one player's ships have been sunk, the game ends.