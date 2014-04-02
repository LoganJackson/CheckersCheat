package com.frillingJackson.checkersCheat;

//import org.opencv.android.Utils;
//import org.opencv.core.Mat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class FirstUse extends Activity {

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
        	byte[] byteArray = data.getByteArrayExtra("image");
        	Bitmap photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        	ImageView newView = (ImageView) findViewById(R.id.imageView1);
        	newView.setImageBitmap(photo);
        	
        	
        	//Mat mat = new Mat();
        	//Utils.bitmapToMat(photo, mat);
        	//bool found = findChessboardCorners(mat, boardSize, ptvec, CV_CALIB_CB_ADAPTIVE_THRESH );
        	
            //Intent intent2 = new Intent(this, CorrectionActivity.class);
            //intent2.putExtra("photo", photo);
            //startActivity(intent2);
        }
    } 
	
	public void correct(View view){
		Intent intent1 = new Intent(this,CorrectionActivity.class);
		startActivity(intent1);
	}

}
