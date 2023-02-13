//Written by Otis Ding
//Student ID: 251220811
//This class creates a game board and helps evaluate the position of both the player and the computer

public class Evaluate {
	
	//Initializes instanced variables, including the matrix we'll be using as our board
	private char[][] gameBoard;
	private int size, tilesToWin, maxLevels;
	
	//Constructor: initializes the instanced variables to the values given into the parameters
	public Evaluate (int size, int tilesToWin, int maxLevels) {
		//Initalizes gameBoard to be the correct dimensions
		gameBoard = new char[size][size];
		this.size = size;
		this.tilesToWin = tilesToWin;
		this.maxLevels = maxLevels;
		
		//Initializes all the values inside gameBoard to 'empty', 'e', as described in the assignment
		for (int i = 0; i < size; i++) {
			for(int q = 0; q < size; q++) {
				gameBoard[q][i] = 'e';
			}
		}
	}
	
	//createDictionary method - creates a dictionary of size 8017
	//8017 was the chosen size of the dictionary since we needed to use a prime number between 6000 and 10,000, and this number feels good
	public Dictionary createDictionary() {
		Dictionary dict = new Dictionary(8017);
		return dict;
	}
	
	//repeatedState method - checks if a Record in the dictionary has the same key as the current board, returns it if it exists, otherwise returns null
	public Record repeatedState(Dictionary dict) {
		//Create empty string
		String key = "";
		
		//Concatenate each character from the board into the string to make the key
		for (int i = 0; i < this.size; i++) {
			for (int q = 0; q < this.size; q++) {
				key = key + gameBoard[q][i];
			}
		}
		
		//Uses the get function from Dictionary to either get the object or null
		return dict.get(key);
		
	}
	
	//insertState method - Creates a key with the current board, makes it into a Record object, and inserts it into the hash table
	public void insertState(Dictionary dict, int score, int level) {
		
		//Makes the key
		String key = "";
		for (int i = 0; i < this.size; i++) {
			for (int q = 0; q < this.size; q++) {
				key = key + gameBoard[q][i];
			}
		}
		
		//Makes the Record
		Record record = new Record(key, evalBoard(), this.maxLevels);
		//Puts the record into the hash table
		dict.put(record);
	}
	
	//storePlay method - inserts a symbol into the game board matrix with the given parameters
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	//squareIsEmpty method - checks if the square at the position given by the parameters is empty, if it contains the 'e' char
	public boolean squareIsEmpty(int row, int col) {
		if (gameBoard[row][col] == 'e') {
			return true;
		} else {
			return false;
		}
	}
	
	//tileOfComputer method - Sets the tile specified by the parameters to belowing to the computer, 'c'
	public boolean tileOfComputer(int row, int col) {
		if (gameBoard[row][col] == 'c') {
			return true;
		} else {
			return false;
		}
	}
	
	//tileOfHuman method - Sets the tile specified by the parameters to belowing to the human, 'h'
	public boolean tileOfHuman(int row, int col) {
		if (gameBoard[row][col] == 'h') {
			return true;
		} else {
			return false;
		}
	}
	
	//wins method - returns true if either the computer of the human wins, false otherwise
	public boolean wins (char symbol) {
		if ((evalBoard() == 3) || (evalBoard() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	//isDraw method - checks if there are still empty tiles on the board, if there are not, then it is a draw
	public boolean isDraw() {
		char checker = 'e';
		for (int i = 0; i < this.size; i++) {
			for (int q = 0; q < this.size; q++) {
				//Checks if any of the tiles is empty, as long as a single one still exists, false is returned
				if (checker == gameBoard[q][i]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	//evalBoard method - returns the score, a measure of what position the board is currently in
	//Uses other private methods to work
	public int evalBoard() {
		//If the computer has won, returns 3
		if ((isRow('c') || isColumn('c') || isDiag('c'))) {
			return 3;
		//If the human has won, returns 0
		} else if (isRow('h') || isColumn('h') || isDiag('h')) {
			return 0;
		//If there is a draw, returns 2
		} else if (isDraw()){
			return 2;
		//Otherwise the game is still ongoing, so returns 1
		}else {
			return 1;
		}
	}
	
	//Private isColumn method - checks if the parameter symbol has enough tiles in any column to win
	private boolean isColumn(char symbol) {
		//Create variable winCondition, this will track how many of the symbols are in a row.
		//If winConsition == tilesToWin, then whoever has control of the parameter symbol has won
		int winCondition = 0;
		
		//Iterates through each column of the board checking each of its elements
		//Tally's up the number of times the symbol appears in a row in each column and compares it with the tilesToWin instanced variable
		for (int i = 0; i < this.size; i++) {
			for (int q = 0; q < this.size; q++) {
				if (symbol == gameBoard[q][i]) {
					winCondition += 1;
				} else {
					//Resets winCondition if the streak of the symbols get interrupted
					winCondition = 0;
				}
				if (winCondition == this.tilesToWin) {
					return true;
				}
			}
			//Resets winCondition at the end of the column
			winCondition = 0;
		}
		return false;
	}
	
	//isRow method - checks if the parameter symbol has enough tiles in any row to win
	private boolean isRow(char symbol) {
		
		//Same principal as above, only with rows this time
		int winCondition = 0;
		
		for (int q = 0; q < this.size; q++) {
			for (int i = 0; i < this.size; i++) {
				if (symbol == gameBoard[q][i]) {
					winCondition += 1;
				} else {
					winCondition = 0;
				}
				if (winCondition == this.tilesToWin) {
					return true;
				}
			}
			winCondition = 0;
		}
		return false;
	}
	
	//isDiag method - Checks if any diagonal on the board has enough of the parameter symbols to be a win
	private boolean isDiag(char symbol) {
		
		//Same principal when it comes to the winConditions as above
		int winCondition = 0;
		
		//This algorithm below iterates down the diagonals starting from the upper left 
	    for( int k = 0 ; k < this.size * 2 ; k++ ) {
	        for( int j = 0 ; j <= k ; j++ ) {
	            int i = k - j;
	            if( i < this.size && j < this.size ) {
	            	if (symbol == gameBoard[i][j]) {
						winCondition += 1;
					} else {
						//Resets winCondition if there is an interruption in the diagonal
						winCondition = 0;
					}
					if (winCondition == this.tilesToWin) {
						return true;
					}
	            }
	        }
	        //Resets winCondition at the end of the diagonal
	        winCondition = 0;
	    }
	    
	    //Since the algorithm above only iterates in one of the diagonal directions, we need to reverse the matrix so the other direction of diagonals will get looked at as well
	    char [] temp = new char[this.size * this.size];
	    char [][] reversed = new char[size][size];
	    
	    //Stores all the values of the original gameBoard matrix into the temp array
	    int counter = 0;
	    for (int q = 0; q < this.size; q++) {
			for (int i = 0; i < this.size; i++) {
				temp[counter] = gameBoard[q][i];
				counter ++;
			}
		}
	    
	    //Stores all the elements from the temp array into reversed such that the elements are reversed
	    counter = 0;
	    for (int q = 0; q < this.size; q++) {
			for (int i = this.size-1; i >= 0; i--) {
				reversed[q][i] = temp[counter];
				counter++;
			}
		}
	    
	    //Runs the algorithm again, this time on reversed so that we can check if any of the diagonals on the direction we haven't checked yet meet the win condition
	    //Otherwise same as what we did above
	    winCondition = 0;
	    for( int k = 0 ; k < this.size * 2 ; k++ ) {
	        for( int j = 0 ; j <= k ; j++ ) {
	            int i = k - j;
	            if( i < this.size && j < this.size ) {
	            	if (symbol == reversed[i][j]) {
						winCondition += 1;
					} else {
						winCondition = 0;
					}
					if (winCondition == this.tilesToWin) {
						return true;
					}
	            }
	        }
	        winCondition = 0;
	    }
	    
	    
	    return false;
	}
	
}
