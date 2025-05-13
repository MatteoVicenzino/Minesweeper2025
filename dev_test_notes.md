# Tests and behaviours that need to be implemented

Note file to keep track of test implementation and behaviours that need to be implemented in the Minesweeper game.
For now these are the tests that we are implementing, more to come...

### Cell Tests
- ~~new cell initialized is not bombed, revealed nor flagged~~
- ~~Cell can be set to mined~~
- ~~Revealed Cell cannot be revealed again~~
- ~~Revealed Cell cannot be flagged~~

### Minefield Tests
- ~~Minefield dimensions are set correctly~~
- ~~Mines are placed correctly on the minefield~~
- ~~input have to be valid cell on the minefield~~
- ~~Adjacent cells are calculated correctly~~
    - ~~Count is 0 when there are no adjacent mines~~
    - ~~Count is equal to the number of adjacent mines (regardless of the position)~~
    -  ~~Count adj cells on corner cells~~
    - ~~Count adj cells on bombed cells~~
- Cells are revealed correctly when the coordinates is given

### Game Tests
- When the game starts, a minefield grid of correct size is created and the correct number of mines is randomly placed.
- Calling game.revealCell(x, y) should mark the specified cell as revealed and update the internal state (e.g. cell.isRevealed = true, game over if mine, etc.)
- Calling game.flagCell(x, y) should toggle the flagged state of the specified cell, and increase or decrease game.flagsPlaced accordingly.
- game.getMinesLeft() should return the correct result: totalMines - flagsPlaced, and should reflect changes when flags are added or removed.
- When a cell is revealed all adjacent cells should be revealed correctly:
  - multiple tests tbd...
- If a revealed cell contains a mine, the game should immediately end and set gameOver = true.
- If all non-mine cells are revealed, game.isWon() should return true and gameOver should be set appropriately.
- When the first cell is revealed, the timer should start; when the game ends (win or lose), the timer should stop.
- game.getElapsedTime() should return a positive and increasing number while the game is running, and stop increasing when the game ends.
- Calling game.reset() should reinitialize the minefield, clear all revealed/flagged cells, reset timers, and return the game to its initial state.

### Cli and Graphics
to be implemented yet

# Refactoring

Note file to keep track of refactoring and improvements that need to be done at some point of the development.

### Cell 

### Minefield
- ~~rows and cols --> height and width~~
- possible refactor of field matrix into a separate class
- possible refactor of testCountAdjacentMinesOutOfBounds() into a more generic test in MinefieldTests.java

### Game

### Cli and Graphics


