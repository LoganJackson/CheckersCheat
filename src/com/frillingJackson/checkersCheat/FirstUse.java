package com.frillingJackson.checkersCheat;

import org.opencv.core.Mat;

import java.io.File;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FirstUse extends Activity {
	private static final String TAG = "FirstUseAct";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_use);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_use, menu);
		return true;
	}
	
	public void takePicture(View view) {
		Intent intent = new Intent(this, CamActivity.class);
		startActivityForResult(intent,100); //maybe need to change this request code later
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == 100 && resultCode == RESULT_OK) {  
        	try {
        		File file = new File(getFilesDir(), "photo.jpg");
        		ImageView newView = (ImageView) findViewById(R.id.imageView1);
        		Log.d(TAG, "Trying to open " + file.getAbsolutePath());
        		Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath());
        		Log.d(TAG, "Rec'd size of photo" + photo.getWidth() + " x " + photo.getHeight()); 
        		photo = Bitmap.createScaledBitmap(photo, 1024, 1024, false);
        		newView.setImageBitmap(photo);	
       	
        		Log.d(TAG, "trying to make Mat");
        		Mat mat = new Mat();
        		Log.d(TAG, "trying to convert bitmap to mat");
        		Utils.bitmapToMat(photo, mat);
        		Log.d(TAG, "trying to make size");
        		Size boardSize = new Size (7,7);
        		MatOfPoint2f corners = new MatOfPoint2f() ;
        		Point[] recCornersArray;
        		MatOfPoint2f recCorners = new MatOfPoint2f() ;
        		//Mat homographyCorners = new Mat() ;
		    
        		boolean found = Calib3d.findChessboardCorners(mat, boardSize , corners, 0); //this mutates corners to hold list of corner locations 
        		if(!found){
        			Toast.makeText(getApplicationContext(), "The checkerboard was NOT found, please try again.",
        					Toast.LENGTH_LONG).show();
        		}else{
        			Log.d(TAG, "Found the board !!");
        			Toast.makeText(getApplicationContext(), "The checkerboard WAS found!",
        					Toast.LENGTH_LONG).show();
        			// find R; the set of rectified corner locations 
        			//for(int i =0; i<7;i++){
        			// 	for(int j = 0; j<7; j++){
        			//		recCornersArray.add(i+.5,j+.5);
        			//	}
        			//}
        			//recCorners.fromArray(recCornersArray);
        			
        		
        			//Mat homographyCorners = Calib3d.findHomography(corners, recCorners);
        		
        			//compute location of each piece 
        			//Mat locMulMat = ????
        			//int loc = 0;
        			//double res = 0;
        			//Mat location = new Mat();
        			//for(int row = 0; row <=7; row++){
        			//	for(int col = 0; col <= 7; col++){
        			//		loc = homographyCorners.get(row,col);
        			//      res = (loc*(.5+row))+(loc*(.5+col))+(loc); ??? 
        			//		location(row,col) = res;
        			//	}
        			//}
        			//
        			//this stuff can go into the loops above??
        			//StringBuilder stringBuilder = new StringBuilder();
        			//for(int row = 0; row <=7; row++){
        			//	for(int col = 0; col <= 7; col++){
        			//		check color at location(row)(col) and compair to colorcode
        			//		then add to gamestate
        			//		stringBuilder.append("Some text"); //
        			//		
        			//		
        			//	}
        			//}
        			//String state = stringBuilder.toString();
        			
        			//Intent intent2 = new Intent(this, CorrectionActivity.class);
                    //intent2.putExtra("boardStateString", state);
                    //startActivity(intent2);
        		
        		}
        	
        	}catch (Exception e) {
        		Log.d(TAG, "Exception in firstUse onActResult: "+ e.getMessage());
        	}
        }
    } 
	
	public void correct(View view){
		Intent intent1 = new Intent(this,CorrectionActivity.class);
		startActivity(intent1);
	}

}
