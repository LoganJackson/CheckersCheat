package com.frillingJackson.checkersCheat;

import com.frillingJackson.checkersCheat.CheckersAI.NoMovesLeftException;
import com.frillingJackson.checkersCheat.GameState.Move;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class Suggestion extends Activity {
	String boardString;
	GameState board1;
	Move bestMove;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);
		
		
		boardString=getIntent().getStringExtra("corrected");
		  board1= new GameState(boardString);
		  
		  
		  Paint black = new Paint();
			black.setStyle(Paint.Style.FILL);
			black.setARGB(255, 0, 0, 0);
			
			Paint red = new Paint();
			red.setStyle(Paint.Style.FILL);
			red.setARGB(255, 252, 1, 227);
			
			Paint green = new Paint();
			green.setStyle(Paint.Style.FILL);
			green.setARGB(255, 0, 255, 0);
			
			Paint white = new Paint();
			white.setStyle(Paint.Style.FILL);
			white.setARGB(255, 255, 255, 255);
			
			Paint brown= new Paint();
			red.setStyle(Paint.Style.FILL);
			red.setARGB(255,124 ,34, 34);
			
			Paint tan=new Paint();
			tan.setStyle(Paint.Style.FILL);
			tan.setARGB(255,185 ,126, 67);
			
			Paint yellow=new Paint();
			yellow.setStyle(Paint.Style.STROKE);
			yellow.setARGB(255, 233, 247, 7);
			
			
			
			
		Bitmap newImage= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
			Canvas canvas= new Canvas(newImage);
			for(int i=0; i<newImage.getWidth();i+=64){
				for(int j=0; j<newImage.getHeight();j+=64){
					canvas.drawRect(i, j, i+32, j+32, tan);
				}
			}
			for(int i=32; i<newImage.getWidth();i+=64){
				for(int j=0; j<newImage.getHeight();j+=64){
					canvas.drawRect(i, j, i+32, j+32, brown);
				}
			}		
			for(int i=32; i<newImage.getWidth();i+=64){
				for(int j=32; j<newImage.getHeight();j+=64){
					canvas.drawRect(i, j, i+32, j+32, tan);
				}
			}
			for(int i=0; i<newImage.getWidth();i+=64){
				for(int j=32; j<newImage.getHeight();j+=64){
					canvas.drawRect(i, j, i+32, j+32, brown);
				}
			}	
			for(int i=0; i<8;i++){
				for(int j=0;j<8;j++){
					if(board1.get(i,j)=='o'){
						canvas.drawCircle(j*32+15, i*32+15, 10, black);
					}
					else if(board1.get(i,j)=='O'){
						canvas.drawCircle(j*32+15, i*32+15, 10, red);
					}
					else if(board1.get(i, j)=='t'){
						canvas.drawCircle(j*32+15, i*32+15, 10,white );
					}
					else if(board1.get(i, j)=='T'){
						canvas.drawCircle(j*32+15, i*32+15, 10,green);
					}
				}
			}
			ImageView gameboard1=  (ImageView)findViewById(R.id.SuggestImage);
			gameboard1.setImageBitmap(newImage);
			
			
			
			bestMove=findBestMove();
			canvas.drawRect(bestMove.from().col*32, bestMove.from().row*32, bestMove.from().col*32+32, bestMove.from().row*32+32,yellow );
			canvas.drawRect(bestMove.to().col*32, bestMove.to().row*32,bestMove.to().col *32+32,bestMove.to().row*32+32,yellow);
			Log.d("bestMove", bestMove.toString());
			
			
	}
	
		
		
	
@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggestion, menu);
		return true;
	}

	public Move findBestMove(){
		
	
	CheckersAI checkersAI=CheckersAI.getInstance();
	
	Move bestMove = null;
	try {
		bestMove = checkersAI.getBestMove(board1,4);
		Log.d("bestMove", bestMove.toString());
		return bestMove;
		
	} catch (NoMovesLeftException e) {
		
		e.printStackTrace();
	}
	return bestMove;
	}

}
