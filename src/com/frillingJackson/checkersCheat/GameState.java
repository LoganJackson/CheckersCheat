package com.frillingJackson.checkersCheat;

public class GameState {
	char[][] board = new char[8][8];
	//P (pink) = king for the dark pieces 
	//b (brown)= pawn for the dark pieces 
	//G (green)= king for light piece
	//t (tan)  = pawn for light pieces
	
	GameState(char[][] state){
		board = state;
	}
	
	GameState(){
		for (int row = 0; row < 8; row++) {
			//for every row, mark only the "dark" spaces
			if(row%2 == 0){ // if the row is even 
				for (int col = 0; col < 7; col = col+2) {
					board[row][col] = 'E'; //setting each spot to E or empty.
				} 
			}else{ //row is odd
				for (int col = 1; col < 8; col =col+2) {
					board[row][col] = 'E'; //setting each spot to E or empty.
				}
			}
		}
	}
	
	
	char get(int row, int col){
		return board[row][col];
	}
	
	void set(int row, int col, char piece){
		board[row][col] = piece;
	}
}
