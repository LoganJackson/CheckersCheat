package com.frillingJackson.checkersCheat;


import android.app.Activity;
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
import android.widget.Toast;

public class CorrectionActivity extends Activity {
	
	public int lastKnownX; // Might be float types - can't remember
	public int lastKnownY;
	
	ImageView mImage;
	Canvas canvas;
	Paint paint;
	ImageView gameBoard;
	GameState board = new GameState();
	GameState newBoard;
	Bitmap newImage;
	int x;
	int y;
	Bitmap newImage1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_correction);
		ImageView gameBoard = (ImageView)findViewById(R.id.correctionView);
		registerForContextMenu(gameBoard);  

		
		
		//O (red) = king for the dark pieces 
		//o (black)= pawn for the dark pieces 
		//T (green)= king for light piece
		//t (white)  = pawn for light pieces
		//E (empty) = empty space on board
		//X  = unreachable space
		
		//GameState board = new GameState();
		
		//dummy gamestate
		
		board.set(0,1,'o');
		board.set(0,3,'o');
		board.set(0,5,'o');
		board.set(0,7,'o');
		
		board.set(1,0,'o');
		board.set(1,2,'o');
		board.set(1,4,'o');
		board.set(1,6,'o');
		
		board.set(2,3,'o');
		
		board.set(7,0,'t');
		board.set(7,2,'t');
		board.set(7,4,'t');
		board.set(7,6,'t');
		
		board.set(6,1,'t');
		board.set(6,3,'t');
		board.set(6,5,'t');
		board.set(6,7,'t');
		
		board.set(5,4,'t');
		
		
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
				canvas.drawRect(i, j, i+32, j+32, brown);
			}
		}
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=0; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, tan);
			}
		}		
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, brown);
			}
		}
		for(int i=0; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, tan);
			}
		}	
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(board.get(i,j)=='o'){
					canvas.drawCircle(j*32+15, i*32+15, 10, black);
				}
				else if(board.get(i,j)=='O'){
					canvas.drawCircle(j*32+15, i*32+15, 10, red);
				}
				else if(board.get(i, j)=='t'){
					canvas.drawCircle(j*32+15, i*32+15, 10,white );
				}
				else if(board.get(i, j)=='T'){
					canvas.drawCircle(j*32+15, i*32+15, 10,green);
				}
			}
		}
		
					
		gameBoard.setOnTouchListener(new ImageView.OnTouchListener() {
			
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN){
	                x=(int)(8 * event.getX() / v.getWidth());
	                y=(int)(8 * event.getY() / v.getHeight());
	            }
	            
	            for(int i=1; i<8;i+=2){
	    			for(int j=0; j<8;j+=2){
	    				if(x==i && y==j){
	    					showPopupMenu(v);
	    				}
	    			}
	            }
	        
	        
	                return true;
	                
	            }
	            
	        
	    });
		
		
		gameBoard.setImageBitmap(newImage);

	}
	
	 private void showPopupMenu(View v){
		   PopupMenu popupMenu = new PopupMenu(CorrectionActivity.this, v);
		      popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
		      
		      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
		   
		   @Override
		   public boolean onMenuItemClick(MenuItem item) {
			
		   switch(item.getItemId()){
		   case R.id.Red:  
			   newBoard=correctBoard(board.board, x,y, 'O');
			   break;
		   case R.id.Black:
			   newBoard=correctBoard(board.board,x,y,'o');			   
			   break;
		   case R.id.Green:
			   newBoard=correctBoard(board.board,x,y,'T');
			   break;
		   case R.id.White:
			   newBoard=correctBoard(board.board,x,y,'t');
			   break;
	
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
	
	
	
	
	

	public GameState correctBoard(char[][] oldBoard, int xCoordinate, int yCoordinate, char ch){
		
		
	
		Log.d("Brian", "x, y = " + xCoordinate + " "+ yCoordinate);
		
		GameState newBoard = new GameState(oldBoard);
		
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
		
		
		newBoard.set(y, x, ch);
		
		Bitmap newImage1= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
		Canvas canvas1= new Canvas(newImage1);
		
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, brown);
			}
		}
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, tan);
			}
		}		
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, brown);
			}
		}
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, tan);
			}
		}	
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(newBoard.get(i,j)=='o'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, black);
				}
				else if(newBoard.get(i,j)=='O'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, red);
				}
				else if(newBoard.get(i, j)=='t'){
					canvas1.drawCircle(j*32+15, i*32+15, 10, white);
				}
				else if(newBoard.get(i, j)=='T'){
					canvas1.drawCircle(j*32+15, i*32+15, 10,green);
				}
			}
		}
		ImageView gameBoard1= (ImageView)findViewById(R.id.correctionView);
		
		gameBoard1.setImageBitmap(newImage1);
	return newBoard;
	}
	/*
	public void sendBoard(View view){
		//GameState newBoard1 = new GameState(newBoard.board);
		Intent intent = new Intent(getApplicationContext(), Suggestion.class);
		intent.put
		startActivity(intent);
	 }
 	*/
}

	

	
	
	


