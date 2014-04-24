package com.frillingJackson.checkersCheat;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class CorrectionActivity extends Activity {
	
	public int lastKnownX; // Might be float types - can't remember
	public int lastKnownY;
	
	ImageView mImage;
	Canvas canvas;
	Paint paint;
	ImageView gameboard1;
	GameState board1 = new GameState();
	GameState newboard1;
	Bitmap newImage;
	int x;
	int y;
	Bitmap newImage1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_correction);
		ImageView gameboard1 = (ImageView)findViewById(R.id.correctionView);
		registerForContextMenu(gameboard1);  

		
		
		//O (red) = king for the dark pieces 
		//o (black)= pawn for the dark pieces 
		//T (green)= king for light piece
		//t (white)  = pawn for light pieces
		//E (empty) = empty space on board1
		//X  = unreachable space
		
		
		//String boardString=getIntent().getStringExtra();
		//GameState board1 = new GameState(boardString);
		
		//dummy gamestate
		
		board1.set(0,1,'o');
		board1.set(0,3,'o');
		board1.set(0,5,'o');
		board1.set(0,7,'o');
		
		board1.set(1,0,'o');
		board1.set(1,2,'o');
		board1.set(1,4,'o');
		board1.set(1,6,'o');
		
		board1.set(2,3,'o');
		
		board1.set(7,0,'t');
		board1.set(7,2,'t');
		board1.set(7,4,'t');
		board1.set(7,6,'t');
		
		board1.set(6,1,'t');
		board1.set(6,3,'t');
		board1.set(6,5,'t');
		board1.set(6,7,'t');
		
		board1.set(5,4,'t');
		
	
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
		
		
		String stringBoard="";
		for (int i=0; i<=7;i++){
			for(int j=0;j<=7;j++){
				stringBoard=stringBoard+board1.get(i,j);
			}
		}
		Log.d("board", stringBoard);			
		gameboard1.setOnTouchListener(new ImageView.OnTouchListener() {
			
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN){
	                x=(int)(8 * event.getX() / v.getWidth());
	                y=(int)(8 * event.getY() / v.getHeight());
	            		if(board1.get(x, y)!='E'){
	            			showPopupMenu(v);
	            		}
	            	}
	                return true;       
	            }
	    });
		
		
		gameboard1.setImageBitmap(newImage);

	}
	
	 private void showPopupMenu(View v){
		 
		 
		   PopupMenu popupMenu = new PopupMenu(CorrectionActivity.this, v);
		      popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
		      
		      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
		   
		   @Override
		  public boolean onMenuItemClick(MenuItem item) {
			GameState board1 = new GameState();
		   int itemId = item.getItemId();
		if (itemId == R.id.Red) {
			board1=correctboard1(board1.getCharArray(), x,y, 'O');
		} else if (itemId == R.id.Black) {
			board1=correctboard1(board1.getCharArray(),x,y,'o');
		} else if (itemId == R.id.Green) {
			board1=correctboard1(board1.getCharArray(),x,y,'T');
		} else if (itemId == R.id.White) {
			board1=correctboard1(board1.getCharArray(),x,y,'t');
		}
		else if (itemId == R.id.Remove){
			board1=correctboard1(board1.getCharArray(),x,y,'X');
		}
		    return true;
		   }
		  });
		      
		      popupMenu.show();
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.correction, menu);
		return true;
	}
	
	
	
	public void sendBoard1(View view){
		String stringBoard="";
		
		for (int i=0; i<=7;i++){
			for(int j=0;j<=7;j++){
				stringBoard=stringBoard+board1.get(i,j);
			}
		}
		
		//GameState newboard11 = new GameState(newboard1.board1);
		Intent intent = new Intent(this, Suggestion.class);
		intent.putExtra("corrected",stringBoard);
		startActivity(intent);
	}
	


	GameState correctboard1(char[][] oldboard1, int xCoordinate, int yCoordinate, char ch){
		
		
		
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
		
		
		
		board1.set(yCoordinate, xCoordinate, ch);
		Bitmap newImage1= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
		Canvas canvas1= new Canvas(newImage1);
		
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, tan);
			}
		}
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, brown);
			}
		}		
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, tan);
			}
		}
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, brown);
			}
		}	
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(board1.get(i,j)=='o'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, black);
				}
				else if(board1.get(i,j)=='O'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, red);
				}
				else if(board1.get(i, j)=='t'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, white);
				}
				else if(board1.get(i, j)=='T'){
					canvas1.drawCircle(j*32+15, i*32+15, 10,green);
				}
				else if(board1.get(i, j)=='X'){
					canvas1.drawCircle(j*32+15,i*32+15,10,tan);
				}
			}
		}
		
		ImageView gameboard11 = (ImageView)findViewById(R.id.correctionView);
		gameboard11.setImageBitmap(newImage1);
	return board1;
	}
}

	

	
	
	


