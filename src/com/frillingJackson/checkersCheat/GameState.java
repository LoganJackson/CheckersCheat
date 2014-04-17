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
	private char[][] board = new char[8][8];

	public GameState(char[][] state){
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				board[r][c] = state[r][c];
	}

	public GameState(String state){
		char[] charArray = state.toCharArray();
		int index = 0;
		for (int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				set(r, c, charArray[index++]);
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
	}

	public char get(int row, int col) {return board[row][col];}

	public void set(int row, int col, char piece) {
		if (piece == 'o' || piece == 'O' || piece == 't' || piece == 'T' || piece == 'E')
			board[row][col] = piece;
		else 
			board[row][col] = 'X';
	}
}
