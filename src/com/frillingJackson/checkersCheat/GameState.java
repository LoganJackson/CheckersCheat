package com.frillingJackson.checkersCheat;

/*
 * Piece encoding:
 * O (red)   king for the dark pieces 
 * o (black) pawn for the dark pieces 
 * T (green) king for light piece
 * t (tan)   pawn for light pieces
 * E (white) empty space on board
 * X (black) unreachable space
 */

public class GameState {
<<<<<<< HEAD
	public char[][] board = new char[8][8];
=======
	private char[][] board = new char[8][8];
>>>>>>> 524ad13e47273f088448d1d8bf2d8e161f9a3098

	public GameState(char[][] state){
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				set(r, c, state[r][c]);
	}

	public GameState(String state){
		char[] charArray = state.toCharArray();
		int index = 0;
		for (int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++) {
				while (Character.isWhitespace(charArray[index])) index++;
				set(r, c, charArray[index++]);
			}
	}

	public GameState() {
		this("EXEXEXEX"
				+ "XEXEXEXE"
				+ "EXEXEXEX"
				+ "XEXEXEXE"
				+ "EXEXEXEX"
				+ "XEXEXEXE"
				+ "EXEXEXEX"
				+ "XEXEXEXE");
	}
	
	public static GameState initialBoard() {
		return new GameState("tXtXtXtX"
				+ "XtXtXtXt"
				+ "tXtXtXtX"
				+ "XEXEXEXE"
				+ "EXEXEXEX"
				+ "XoXoXoXo"
				+ "oXoXoXoX"
				+ "XoXoXoXo");
<<<<<<< HEAD
	}
	
	public void rotateCW() {
		char[][] rotated = new char [8][8];
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				rotated[c][7 - r] = board[r][c];
		board = rotated;
	}
	
=======
	}
	
	public void rotateCW() {
		char[][] rotated = new char [8][8];
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				rotated[c][7 - r] = board[r][c];
		board = rotated;
	}
	
>>>>>>> 524ad13e47273f088448d1d8bf2d8e161f9a3098
	public void rotateCCW() {
		char[][] rotated = new char [8][8];
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				rotated[r][c] = board[c][7 - r];
		board = rotated;
	}
	
	public void flip() {
		rotateCW();
		rotateCW();
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				if (board[r][c] == 'o') board[r][c] = 't';
				else if (board[r][c] == 'O') board[r][c] = 'T';
				else if (board[r][c] == 't') board[r][c] = 'o';
				else if (board[r][c] == 'T') board[r][c] = 'O';
<<<<<<< HEAD
=======
	}

	public char get(int row, int col) {return board[row][col];}

	public void set(int row, int col, char piece) {
		if (piece == 'o' || piece == 'O' || piece == 't' || piece == 'T' || piece == 'E')
			board[row][col] = piece;
		else 
			board[row][col] = 'X';
>>>>>>> 524ad13e47273f088448d1d8bf2d8e161f9a3098
	}

	public char get(int row, int col) {return board[row][col];}

	public void set(int row, int col, char piece) {
		if (piece == 'o' || piece == 'O' || piece == 't' || piece == 'T' || piece == 'E')
			board[row][col] = piece;
		else 
			board[row][col] = 'X';
	}

	public char[][] getCharArray(){
		return this.board;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++)
				sb.append(board[r][c]);
			sb.append('\n');
		}
		return sb.toString();
	}
}
