package com.frillingJackson.checkersCheat;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
	Bitmap newImage;
	int x;
	int y;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_correction);
		ImageView gameBoard = (ImageView)findViewById(R.id.correctionView);
		registerForContextMenu(gameBoard);  
		//Bundle bundle = this.getIntent().getExtras();
			//	photo.setPixel(x, y, 0);
			//}
		//}
		//correctionView = (ImageView)findViewById(R.id.correctionView);
		//correctionView.setImageBitmap(photo);
		
		//P (pink) = king for the dark pieces 
		//b (brown)= pawn for the dark pieces 
		//G (green)= king for light piece
		//t (tan)  = pawn for light pieces
		//E (empty) = empty space on board
		//X  = unreachable space
		
		//GameState board = new GameState();
		
		//dummy gamestate
		
		board.set(0,1,'b');
		board.set(0,3,'b');
		board.set(0,5,'P');
		board.set(0,7,'b');
		
		board.set(1,0,'P');
		board.set(1,2,'b');
		board.set(1,4,'b');
		board.set(1,6,'b');
		
		board.set(2,3,'b');
		
		board.set(7,0,'G');
		board.set(7,2,'t');
		board.set(7,4,'t');
		board.set(7,6,'t');
		
		board.set(6,1,'G');
		board.set(6,3,'t');
		board.set(6,5,'t');
		board.set(6,7,'t');
		
		board.set(5,4,'t');
		
		
		
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setARGB(255, 0, 255, 0);
		
		Paint paint1 = new Paint();
		paint1.setStyle(Paint.Style.FILL);
		paint1.setARGB(255, 255, 0, 0);
		Bitmap newImage= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
		Canvas canvas= new Canvas(newImage);
		for(int i=0; i<newImage.getWidth();i+=64){
			for(int j=0; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint1);
			}
		}
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=0; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint);
			}
		}		
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint1);
			}
		}
		for(int i=0; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint);
			}
		}	
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(board.get(i,j)=='b'){
					Paint black= new Paint();
					black.setARGB(255, 100, 100, 100);
					black.setStyle(Paint.Style.FILL);
					canvas.drawCircle(j*32+15, i*32+15, 10, black);
				}
				else if(board.get(i,j)=='t'){
					Paint blue= new Paint();
					blue.setARGB(255, 0, 0, 255);
					blue.setStyle(Paint.Style.FILL);
					canvas.drawCircle(j*32+15, i*32+15, 10, blue);
				}
				else if(board.get(i, j)=='P'){
					Paint pink = new Paint();
					pink.setARGB(255, 50, 0, 200);
					pink.setStyle(Paint.Style.FILL);
					canvas.drawCircle(j*32+15, i*32+15, 10,pink );
				}
				else if(board.get(i, j)=='G'){
					Paint green = new Paint();
					green.setARGB(255, 0, 200, 0);
					green.setStyle(Paint.Style.FILL);
					canvas.drawCircle(j*32+15, i*32+15, 10,green);
				}
			}
		}
		
					
		gameBoard.setOnTouchListener(new ImageView.OnTouchListener() {
			
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN){
	                x=(int)event.getX();
	                y=(int)event.getY();
	                showPopupMenu(v);
	                
	                return true;
	                
	            }
	            if(event.getAction()==MotionEvent.ACTION_UP){
	            	
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
			  Toast.makeText(getApplicationContext(), String.valueOf(x),Toast.LENGTH_SHORT).show();
			  Toast.makeText(getApplicationContext(),String.valueOf(y), Toast.LENGTH_SHORT).show();
			  //GameState board1=new GameState();   
			   correctBoard(board.board, x,y, 'b');
		   case R.id.Blue:
			   //draw();
			   return true;
			   
			   
			   
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
	
	

	
	public void draw(){
		
		Paint black= new Paint();
		black.setARGB(255, 100, 100, 100);
		black.setStyle(Paint.Style.FILL);
		Bitmap newImage1= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
		Canvas canvas1=new Canvas(newImage1);
		canvas1.drawCircle(100, 100, 20, black);
		
		ImageView gameBoard1=(ImageView)findViewById(R.id.correctionView);
		gameBoard1.setImageBitmap(newImage1);
		
		
		
	}
	
	
	

	public GameState correctBoard(char[][] oldBoard, int xCoordinate, int yCooridnate, char ch){
	
		GameState newBoard = new GameState(oldBoard);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setARGB(255, 0, 255, 0);
		newBoard.set((yCooridnate-25)/73, (xCoordinate-25)/73, ch);
		Paint paint1 = new Paint();
		paint1.setStyle(Paint.Style.FILL);
		paint1.setARGB(255, 255, 0, 0);
		Bitmap newImage1= Bitmap.createBitmap(256, 256,Bitmap.Config.ARGB_8888); 
		Canvas canvas1= new Canvas(newImage1);
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, paint1);
			}
		}
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=0; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, paint);
			}
		}		
		for(int i=32; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, paint1);
			}
		}
		for(int i=0; i<newImage1.getWidth();i+=64){
			for(int j=32; j<newImage1.getHeight();j+=64){
				canvas1.drawRect(i, j, i+32, j+32, paint);
			}
		}	
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(newBoard.get(i,j)=='b'){
					Paint black= new Paint();
					black.setARGB(255, 100, 100, 100);
					black.setStyle(Paint.Style.FILL);
					canvas1.drawCircle(j*32+15, i*32+15, 10, black);
				}
				else if(newBoard.get(i,j)=='t'){
					Paint blue= new Paint();
					blue.setARGB(255, 0, 0, 255);
					blue.setStyle(Paint.Style.FILL);
					canvas1.drawCircle(j*32+15, i*32+15, 10, blue);
				}
				else if(newBoard.get(i, j)=='P'){
					Paint pink = new Paint();
					pink.setARGB(255, 50, 0, 200);
					pink.setStyle(Paint.Style.FILL);
					canvas1.drawCircle(j*32+15, i*32+15, 10,pink );
				}
				else if(newBoard.get(i, j)=='G'){
					Paint green = new Paint();
					green.setARGB(255, 0, 200, 0);
					green.setStyle(Paint.Style.FILL);
					canvas1.drawCircle(j*32+15, i*32+15, 10,green);
				}
			}
		}
		ImageView gameBoard1= (ImageView)findViewById(R.id.correctionView);
		Toast.makeText(getApplicationContext(), String.valueOf(gameBoard1.getHeight()), Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), String.valueOf(gameBoard1.getWidth()), Toast.LENGTH_SHORT).show();
		gameBoard1.setImageBitmap(newImage1);
	return newBoard;
	
			
		
	
	}


	
	
}
	
	
	


