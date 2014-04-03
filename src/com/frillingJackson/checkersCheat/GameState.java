package com.frillingJackson.checkersCheat;

public class GameState {
	char[][] board = new char[8][8];
	//P (pink) = king for the dark pieces 
	//b (brown)= pawn for the dark pieces 
	//G (green)= king for light piece
	//t (tan)  = pawn for light pieces
	//E (empty) = empty space on board
	//X  = unreachable space
	
	//for (int row = 0; row <=7; row++) {
		//for every row, mark only the "dark" spaces
	//	if(row%2 == 0){ // if the row is even 
	//		for (int col = 0; col <= 6; col = col+2) {
	//			board[row][col] = 'E'; //setting each spot to E or empty.
	//          board[row][col+1] ='X'; //setting the 
	//		} 
	//	}else{ //row is odd
	//		for (int col = 0; col <= 7; col =col+2) {
	//          board[row][col] ='X'; //setting the
	//			board[row][col+1] = 'E'; //setting each spot to E or empty.
	//		}
	//	}
	//}
	
	//dummy gamestate
	
	//board[0][1] = 'b';
	//board[0][3] = 'b';
	//board[0][5] = 'b';
	//board[0][7] = 'b';
	
	//board[1][0] = 'b';
	//board[1][2] = 'b';
	//board[1][4] = 'b';
	//board[1][6] = 'b';
	
	//board[2][3] = 'b';
	
	//board[7][0] = 't';
	//board[7][2] = 't';
	//board[7][4] = 't';
	//board[7][6] = 't';
	
	//board[6][1] = 't';
	//board[6][3] = 't';
	//board[6][5] = 't';
	//board[6][7] = 't';
	
	//board[5][4] = 't';
	
	GameState(char[][] state){
		board = state;
	}
	
	GameState(String state){
		char[] charArray = state.toCharArray();
		char[][] newState = new char[8][8];
		int index =0;
		for (int row= 0 ; row<= 7 ; row++){
			for(int col =0; col<=7; col++){
				newState[row][col]= charArray[index];
				index++;
			}
		}
		board = newState;
	}
	
	GameState(){
		for (int row = 0; row <= 7; row++) {
			//for every row, mark only the "dark" spaces
			if(row%2 == 0){ // if the row is even 
				for (int col = 0; col <= 6; col = col+2) {
					board[row][col] = 'E'; //setting each spot to E or empty.
					board[row][col+1] ='X'; //setting the
				} 
			}else{ //row is odd
				for (int col = 0; col <= 7; col =col+2) {
					board[row][col] ='X'; //setting the
					board[row][col+1] = 'E'; //setting each spot to E or empty.
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
