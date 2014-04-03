package com.frillingJackson.checkersCheat;

//import org.opencv.android.Utils;
//import org.opencv.core.Mat;

import java.io.File;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

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
		    //newView.setImageBitmap(photo);
		    photo = Bitmap.createScaledBitmap(photo, 1024, 1024, false);
		    newView.setImageBitmap(photo);	
       	
        	Mat mat = new Mat();
        	Utils.bitmapToMat(photo, mat);
        	
		    //Size boardSize = new Size (7,7);
//		    ptvec = ;
        	//bool found = findChessboardCorners(mat, boardSize , ptvec, CV_CALIB_CB_ADAPTIVE_THRESH );
        	//boardSize = 7x7
        	
            //Intent intent2 = new Intent(this, CorrectionActivity.class);
            //intent2.putExtra("boardStateString", state);
            //startActivity(intent2);
        	}
        	catch (Exception e) {
        		Log.d(TAG, "Exception.");
        	}
        }
    } 
	
	public void correct(View view){
		Intent intent1 = new Intent(this,CorrectionActivity.class);
		startActivity(intent1);
	}

}
