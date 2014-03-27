package com.frillingJackson.checkersCheat;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class CorrectionActivity extends Activity {
	
	ImageView correctionView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_correction);
		
		//Bundle bundle = this.getIntent().getExtras();
		//Bitmap photo = bundle.getParcelable("photo");
		//for(int x=0; x < photo.getHeight(); x+=2){
			//for(int y=0; y<photo.getWidth(); y+=2){
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
		
		GameState board = new GameState();
		
		//dummy gamestate
		
		board.set(0,1,'b');
		board.set(0,3,'b');
		board.set(0,5,'b');
		board.set(0,7,'b');
		
		board.set(1,0,'b');
		board.set(1,2,'b');
		board.set(1,4,'b');
		board.set(1,6,'b');
		
		board.set(2,3,'b');
		
		board.set(7,0,'t');
		board.set(7,2,'t');
		board.set(7,4,'t');
		board.set(7,6,'t');
		
		board.set(6,1,'t');
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
				canvas.drawRect(i, j, i+32, j+32, paint);
			}
		}
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=0; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint1);
			}
		}		
		for(int i=32; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint);
			}
		}
		for(int i=0; i<newImage.getWidth();i+=64){
			for(int j=32; j<newImage.getHeight();j+=64){
				canvas.drawRect(i, j, i+32, j+32, paint1);
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
					
				}
			}
		
		
		ImageView mImage = (ImageView)findViewById(R.id.correctionView);
		mImage.setImageBitmap(newImage);
		
	}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.correction, menu);
		return true;
	}
	
	
	
	
	
	public GameState correctBoard(GameState old){
	
		
	GameState board = new GameState();
	
	//dummy gamestate
	
	board.set(0,1,'b');
	board.set(0,3,'b');
	board.set(0,5,'b');
	board.set(0,7,'b');
	
	board.set(1,0,'b');
	board.set(1,2,'b');
	board.set(1,4,'b');
	board.set(1,6,'b');
	
	board.set(2,3,'b');
	
	board.set(7,0,'t');
	board.set(7,2,'t');
	board.set(7,4,'t');
	board.set(7,6,'t');
	
	board.set(6,1,'t');
	board.set(6,3,'t');
	board.set(6,5,'t');
	board.set(6,7,'t');
	
	board.set(5,4,'t');
	
	/*correctionView.setOnTouchListener(new View.OnTouchListener()
	{
		public boolean onTouch(){
			return true;
		
	}
		/*	
			public boolean onContextItemSelected(MenuItem item) {
			    
			   switch (item.getItemId()) {
			        case R.id.edit:
			            editNote(info.id);
			            return true;
			        case R.id.delete:
			            deleteNote(info.id);
			            return true;
			        default:
			            return super.onContextItemSelected(item);
			    }
			}
			
		}
	  */  
	
	
	return old;

}
}
