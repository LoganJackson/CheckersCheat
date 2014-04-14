package com.frillingJackson.checkersCheat;


import java.io.File;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity {
	private static final String TAG = "FirstUseAct";
	
	//private CameraBridgeViewBase mOpenCvCameraView;
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
            case LoaderCallbackInterface.SUCCESS:
            {
                Log.i(TAG, "OpenCV loaded successfully");
                //System.loadLibrary("libName");
            } break;
            default:
            {
                super.onManagerConnected(status);
            } break;
            }
        }
    };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
	        		//ImageView newView = (ImageView) findViewById(R.id.imageView1);
	        		Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath()); 
	        		photo = Bitmap.createScaledBitmap(photo, 1024, 1024, false);
	        		//newView.setImageBitmap(photo);	
	     
	        		Mat mat = new Mat();
	        		Log.d(TAG, "trying to convert bitmap to mat");
	        		Utils.bitmapToMat(photo, mat); //OpenCV_for_Tegra(26288): Tegra Version detected: 0

	        		Log.d(TAG, "trying to make size");
	        		Size boardSize = new Size (7,7);
	        		MatOfPoint2f corners = new MatOfPoint2f() ;
	        		Point[] recCornersArray = new Point[49];
	        		Mat homographyCorners = new Mat() ;
			    
	        		boolean found = Calib3d.findChessboardCorners(mat, boardSize , corners, 0);  
	        		if(!found){
	        			Log.d(TAG, "Didnt find the board");
	        			Toast.makeText(getApplicationContext(), "The checkerboard was NOT found, please try again.",
	        					Toast.LENGTH_LONG).show();
	        		}else{
	        			Log.d(TAG, "Found the board !!");
	        			Toast.makeText(getApplicationContext(), "The checkerboard WAS found!",
	        					Toast.LENGTH_LONG).show();
	        			// find R; the set of rectified corner locations 
	        			int count = 0;
	        			for(int i =0; i<7;i++){
	        			 	for(int j = 0; j<7; j++){
	        			 		Point newPoint= new Point(i+.5,j+.5);
	        					recCornersArray[count]= newPoint;
	        					count = count + 1;
	        				}
	        			}
	        			MatOfPoint2f recCorners = new MatOfPoint2f(recCornersArray);
	        			homographyCorners = Calib3d.findHomography(corners, recCorners);
	        		
	        			//compute location of each piece 
	        			Mat locMulMat = new Mat();
	        			
	        			double res = 0;
	        			Mat location = new Mat();
	        			StringBuilder stringBuilder = new StringBuilder();
	        			
	        			for(int row = 0; row <=7; row++){
	        				for(int col = 0; col <= 7; col++){
	        					double[] loc = homographyCorners.get(row,col);
	        					res = (loc[0]*(.5+row))+(loc[1]*(.5+col))+(loc[2]);
	        					location.put(row, col, res);
//	        					//check color at location(row,col) and compair to colorcode
	        					//if(piece == p1paun){
	        					//	String name = ;
	        					//}else if(piece == p1king){
	        					//	String name = ;
	        					//}else if(piece == p2paun){
	        					//	String name = ;
	        					//}else if(piece == p2king){
	        					//	String name = ;
	        					//}else if(piece == empty){
	        					//	String name = "E";
	        					//}else{
	        					//	String name = "X";
	        					//}
//	                			//stringBuilder.append(name); 
	        				}
	        			}
//	        			String state = stringBuilder.toString();
//	        			
//	        			Intent intent2 = new Intent(this, CorrectionActivity.class);
//	                    intent2.putExtra("boardStateString", state);
//	                    startActivity(intent2);
	        		
	        		}
	        	}catch (Exception e) {
	        		Log.d(TAG, "Exception in firstUse onActResult: "+ e.getMessage());
	        	}
	        }  
    } 
}
