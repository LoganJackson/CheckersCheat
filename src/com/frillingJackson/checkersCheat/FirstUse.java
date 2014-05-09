package com.frillingJackson.checkersCheat;

import org.opencv.core.Mat;
import java.io.File;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.imgproc.Imgproc;
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
		//OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
	}
	
    @Override
    public void onResume(){
        super.onResume();
       OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
        		Bitmap photo = BitmapFactory.decodeFile(file.getAbsolutePath()); 
        		photo = Bitmap.createScaledBitmap(photo, 1024, 1024, false);
        		Mat photoMat = new Mat();
        		Utils.bitmapToMat(photo, photoMat); 
        		Size boardSize = new Size (7,7);
        		MatOfPoint2f corners = new MatOfPoint2f() ;
        		Point[] recCornersArray = new Point[49];
        		Mat homographyCorners = new Mat() ;
        		
        		boolean found = Calib3d.findChessboardCorners(photoMat, boardSize , corners, 0);  
        		if(!found){
        			Toast.makeText(getApplicationContext(), "The checkerboard was NOT found, please try again.",
        					Toast.LENGTH_LONG).show();
        		}else{
        			Toast.makeText(getApplicationContext(), "The checkerboard WAS found!",
        					Toast.LENGTH_LONG).show();
        			// find R; the set of rectified corner locations 
        			int count = 0;
        			for(int i =1; i<=7;i++){ //7
        			 	for(int j = 1; j<=7; j++){
        			 		Point newPoint= new Point(i,j);
        					recCornersArray[count]= newPoint;
        					count = count + 1;
        			 	}
        			}
        		}
        		MatOfPoint2f recCorners = new MatOfPoint2f(recCornersArray);
        		homographyCorners = Calib3d.findHomography(recCorners,corners);
        		
        		Point[] location = new Point[64]; 
        		int locationIndex =0;
    			for(double y = 0.5; y < 8; y++){ 
    				for(double x = 0.5; x < 8; x++){
        				double wPrime =  ((homographyCorners.get(2, 0)[0]*x) + (homographyCorners.get(2, 1)[0]*y) + homographyCorners.get(2, 2)[0]);
        				double xPrime = ((homographyCorners.get(0,0)[0]*x) + (homographyCorners.get(0,1)[0]*y) + homographyCorners.get(0,2)[0]) /wPrime;
        				double yPrime = ((homographyCorners.get(1, 0)[0]*x) + (homographyCorners.get(1, 1)[0]*y) + homographyCorners.get(1, 2)[0]) /wPrime;
        				Point res = new Point(xPrime,yPrime);
        					
        				location[locationIndex] = res;
        				locationIndex = locationIndex+1;
        			}
        		}
        		Mat gausPhotoMat = new Mat();
        		Size size = new Size(0,0);
        		Imgproc.GaussianBlur(photoMat, gausPhotoMat, size, 1);
        		
        		double[][] colorCalibration = new double[4][3];
        		double[] pieceColor = new double[3];
        		for(int index = 0; index <64; index++){ 
        			int intx  = (int) location[index].x;
        			int inty = (int) location[index].y;
        			double[] photoColor = gausPhotoMat.get(inty, intx);
        			for (int i = 0; i < 3; i++) pieceColor[i] = photoColor[i];
        			if(index == 1){
        				colorCalibration[0][0] = pieceColor[0];	//player2King
        				colorCalibration[0][1] = pieceColor[1];
        				colorCalibration[0][2] = pieceColor[2];
        			}else if(index == 8){
        				colorCalibration[1][0] = pieceColor[0];	
        				colorCalibration[1][1] = pieceColor[1];
        				colorCalibration[1][2] = pieceColor[2];	//player2Pawn
        			}else if(index == 17){					
        				colorCalibration[2][0] = pieceColor[0];	
        				colorCalibration[2][1] = pieceColor[1];
        				colorCalibration[2][2] = pieceColor[2];	//player1King
        			}else if(index == 10){					
        				colorCalibration[3][0] = pieceColor[0];	
        				colorCalibration[3][1] = pieceColor[1];
        				colorCalibration[3][2] = pieceColor[2];	//player1Pawn
        			}
        		}        		
        		
    			Intent homeIntent = new Intent(this, Home.class);
    			homeIntent.putExtra("p2KingR", (int) colorCalibration[0][0]);  
    			homeIntent.putExtra("p2KingG", (int) colorCalibration[0][1]);
    			homeIntent.putExtra("p2KingB", (int) colorCalibration[0][2]);
    			homeIntent.putExtra("p2PawnR", (int) colorCalibration[1][0]);
    			homeIntent.putExtra("p2PawnG", (int) colorCalibration[1][1]);
    			homeIntent.putExtra("p2PawnB", (int) colorCalibration[1][2]);
    			homeIntent.putExtra("p1KingR", (int) colorCalibration[2][0]);
    			homeIntent.putExtra("p1KingG", (int) colorCalibration[2][1]);
    			homeIntent.putExtra("p1KingB", (int) colorCalibration[2][2]);
    			homeIntent.putExtra("p1PawnR", (int) colorCalibration[3][0]);
    			homeIntent.putExtra("p1PawnG", (int) colorCalibration[3][1]);
    			homeIntent.putExtra("p1PawnB", (int) colorCalibration[3][2]);
    			startActivity(homeIntent);
    			
        	}catch (Exception e) {
        		Log.d(TAG, "Exception in firstUse onActResult: "+ e.getMessage());
        		Toast.makeText(getApplicationContext(), "We were not able to calibrate from that picture, please try again.",
   					Toast.LENGTH_LONG).show();
        	}	
        }
	}

	public double dist(double[] fromImage, double[] idealColor){
		double r = (idealColor[0] - fromImage[0]) / 255.0;
		double g = (idealColor[1] - fromImage[1]) / 255.0;
		double b = (idealColor[2] - fromImage[2]) / 255.0;
		final double s3 = Math.sqrt(3);
		return Math.sqrt(r * r + g * g + b * b) / s3;
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
}
