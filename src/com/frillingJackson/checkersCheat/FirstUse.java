package com.frillingJackson.checkersCheat;

import org.opencv.core.Mat;

import java.io.File;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
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
	
	//private CameraBridgeViewBase mOpenCvCameraView;
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
            case LoaderCallbackInterface.SUCCESS:
            {
                Log.i(TAG, "OpenCV loaded successfully");
                //System.loadLibrary("libName");
            } 
            break;
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
		setContentView(R.layout.activity_first_use);
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
	}
	
    @Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
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
        		Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath()); 
        		photo = Bitmap.createScaledBitmap(photo, 1024, 1024, false);
        		//following section is for the calibration steps  
        		//find 4 pieces and get their colors 
        		//RGB[] pieceArray = new RGB[4];
        		//for(int i =0; i<4; i++){
        		//	find rgb color and put in pieceArray
        		//	start at the upper left (p2King) then upper right, lower left (p1King), lower right
        		//  
        		//	pieceArray[i] = rgb;
        		//}
        		//send those colors to home using an intent
//    			Intent homeIntent = new Intent(this, Home.class);
//              homeIntent.putExtra("p1Paun", pieceArray[3]); //these should be rgb vals 
//              homeIntent.putExtra("p1King", pieceArray[2]);
//              homeIntent.putExtra("p2Paun", pieceArray[1]);
//              homeIntent.putExtra("p2King", pieceArray[0]);
//              startActivity(homeIntent);
        	
        		newView.setImageBitmap(photo);	//this will be deleted after testing
        		
        		//from here down this code will be moved to the home activities onActResult() 
        		
        		Mat photoMat = new Mat();
        		Utils.bitmapToMat(photo, photoMat); 

        		Size boardSize = new Size (7,7);
        		MatOfPoint2f corners = new MatOfPoint2f() ;
        		Point[] recCornersArray = new Point[49];
        		Mat homographyCorners = new Mat() ;
		    
        		boolean found = Calib3d.findChessboardCorners(photoMat, boardSize , corners, 0);  
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
        			for(int i =1; i<=7;i++){
        			 	for(int j = 1; j<=7; j++){
        			 		Point newPoint= new Point(i,j);
        					recCornersArray[count]= newPoint;
        					count = count + 1;
        				}
        			}
        			MatOfPoint2f recCorners = new MatOfPoint2f(recCornersArray);
        			homographyCorners = Calib3d.findHomography(recCorners,corners);
//        		
//        			double[] H = new double [9];
//        			H[0] = homographyCorners.get(0, 0)[0];
//        			H[1] = homographyCorners.get(0, 1)[0];
//        			H[2] = homographyCorners.get(0, 2)[0];
//        			H[3] = homographyCorners.get(1, 0)[0];
//        			H[4] = homographyCorners.get(1, 1)[0];
//        			H[5] = homographyCorners.get(1, 2)[0];
//        			H[6] = homographyCorners.get(2, 0)[0];
//        			H[7] = homographyCorners.get(2, 1)[0];
//        			H[8] = homographyCorners.get(2, 2)[0];
//        			for (int i = 0; i < 9; i++) Log.d("Homography", i + ": " + H[i]);
//        			
        			//compute location of each piece 
<<<<<<< HEAD
<<<<<<< HEAD
        			//Mat locMulMat = new Mat();
        			
        			double res = 0;
        			Mat location = new Mat();
        			//StringBuilder stringBuilder = new StringBuilder();
        			
        			for(int row = 0; row <=7; row++){
        				for(int col = 0; col <= 7; col++){
        					double[] loc = homographyCorners.get(row,col);
        					res = (loc[0]*(.5+row))+(loc[1]*(.5+col))+(loc[2]);
        					location.put(row, col, res);
//        					//check color at location(row,col) and compair to colorcode
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
//                			//stringBuilder.append(name); 
        				}
=======
        		
        			Point[] location = new Point[64];
        			int locationIndex =0;
        			for(double row = 0; row <=7; row++){
        				for(double col = 0; col <= 7; col++){
        					double wPrime =  ((homographyCorners.get(2, 0)[0]*row) + (homographyCorners.get(2, 1)[0]*col) + homographyCorners.get(2, 2)[0]);
        					double xPrime = ((homographyCorners.get(0,0)[0]*row) + (homographyCorners.get(0,1)[0]*col) + homographyCorners.get(0,2)[0]) /wPrime;
        					double yPrime = ((homographyCorners.get(1, 0)[0]*row) + (homographyCorners.get(1, 1)[0]*col) + homographyCorners.get(1, 2)[0]) /wPrime;
        					Point res = new Point(xPrime,yPrime);
        					
        					location[locationIndex] = res;
        					locationIndex = locationIndex+1;
        				}
        			}
        			
=======
        		
        			Point[] location = new Point[64];
        			int locationIndex =0;
        			for(double row = 0; row <=7; row++){
        				for(double col = 0; col <= 7; col++){
        					double wPrime =  ((homographyCorners.get(2, 0)[0]*row) + (homographyCorners.get(2, 1)[0]*col) + homographyCorners.get(2, 2)[0]);
        					double xPrime = ((homographyCorners.get(0,0)[0]*row) + (homographyCorners.get(0,1)[0]*col) + homographyCorners.get(0,2)[0]) /wPrime;
        					double yPrime = ((homographyCorners.get(1, 0)[0]*row) + (homographyCorners.get(1, 1)[0]*col) + homographyCorners.get(1, 2)[0]) /wPrime;
        					Point res = new Point(xPrime,yPrime);
        					
        					location[locationIndex] = res;
        					locationIndex = locationIndex+1;
        				}
        			}
        			
>>>>>>> 3aac61fa72b873f00757b4d825fcecaf0854c8ba
        			StringBuilder stringBuilder = new StringBuilder();
        			double[] redColor = {255, 0, 0} ;
    				double[] greenColor = {0, 128, 0};
    				double[] tanColor = {238, 203, 173};
    				double[] grayColor = {128, 128, 128};
    				double[] whiteColor = {255, 255, 255} ;
    				double[] blackColor = {0,0,0};
    				
        			for(int index = 0; index <64; index++){
        					//check color at location[index] and compair to colorcode
        				int intx  = (int) location[index].x;
        				int inty = (int) location[index].y;
        				double[] pieceColor = photoMat.get(inty, intx);
        				Log.d("piece", "red " + pieceColor[0] + " green " + pieceColor[1] + " blue " + pieceColor[2]);
        				
        				//pieceColor[0] =red
        				//pieceColor[1] =green
        				//pieceColor[2] =blue
        				
        				double[] dists = new double[6];
        				double distToRed = dist(pieceColor, redColor);
        				dists[0] = distToRed;
        				double distToGreen = dist(pieceColor, greenColor);
        				dists[1] = distToGreen;
        				double distToTan = dist(pieceColor, tanColor);
        				dists[2] = distToTan;
        				double distToGray = dist(pieceColor, grayColor);
        				dists[3] = distToGray;
        				double distToWhite = dist(pieceColor, whiteColor);
        				dists[4] = distToWhite;
        				double distToBlack = dist(pieceColor, blackColor);
        				dists[5] = distToBlack;
        				
        				int minIndex = getMinIndex(dists);
        				
        				String name = "";
        				if(minIndex == 0){ //these may need to be ranges of rgb values 
        					name = "o";
        				}else if(minIndex == 1){
       						name = "O";
        				}else if(minIndex == 2){
       						name = "t";
        				}else if(minIndex == 3){
       						name = "T";
        				}else if(minIndex == 4){  //pieceColor = white
       						name = "E";
        				}else{    //pieceColor = black
       						name = "X";
       					}
                		stringBuilder.append(name); 
<<<<<<< HEAD
>>>>>>> 3aac61fa72b873f00757b4d825fcecaf0854c8ba
=======
>>>>>>> 3aac61fa72b873f00757b4d825fcecaf0854c8ba
        			}
        			String state = stringBuilder.toString();
        			//GameState boardState = new GameState(state);
        			Log.d("State:", state);
        			
        			Intent intent2 = new Intent(this, CorrectionActivity.class);
        			intent2.putExtra("boardStateString", state);
//        			intent2.putExtra("boardStateString", boardState);
        			startActivity(intent2);
        		}
        	}catch (Exception e) {
        		Log.d(TAG, "Exception in firstUse onActResult: "+ e.getMessage());
        	}
        }
    } 
	
	public double dist(double[] fromImage, double[] idealColor){
		double dist = Math.sqrt( Math.pow((idealColor[0]-fromImage[0]),2) 
				+ Math.pow((idealColor[1]-fromImage[1]),2)+ Math.pow((idealColor[2]-fromImage[2]),2));
		return dist;
	}
	
	public int getMinIndex(double[] array){  
	     double minValue = array[0]; 
	     int minIndex = 0;
	     for(int i=1;i<array.length;i++){  
	    	 if(array[i] < minValue){  
	    		 minValue = array[i];
	    		 minIndex = i;
	        }  
	     }  
	    return minIndex;  
	}  
	
	public void correct(View view){
		Intent intent1 = new Intent(this,CorrectionActivity.class);
		startActivity(intent1);
	}
}
