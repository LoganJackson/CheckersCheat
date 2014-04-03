package com.frillingJackson.checkersCheat;

//import org.opencv.android.Utils;
//import org.opencv.core.Mat;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
        		File file = (File)data.getExtras().get("image");
        		Log.d(TAG, "Trying to open " + file.getAbsolutePath());
        		InputStream stream = getContentResolver().openInputStream(Uri.parse(file.getAbsolutePath()));
        		Log.d(TAG, "Opened input stream");
        		Bitmap photo = BitmapFactory.decodeStream(stream);
        		Log.d(TAG, "Rec'd size of photo" + photo.getWidth() + " x " + photo.getHeight()); 

        	//byte[] byteArray = data.getByteArrayExtra("image");
        	
//        	BitmapFactory.Options options = new BitmapFactory.Options();
//        	options.inJustDecodeBounds = true;
//        	BitmapFactory.decodeResource(getResources(), R.id.imageView1, options);
//        	
//        	Log.d(TAG,"Height"+options.outHeight);
//        	Log.d(TAG,"Width"+options.outWidth);
//        	
        	
//        	int pow = 0;
//        	while (options.outHeight >> pow > reqHeight || options.outWidth >> pow > reqWidth)
//        	    pow += 1;
//        	options.inSampleSize = 1 << pow; 
//        	options.inJustDecodeBounds = false;
//        	
//        	image = BitmapFactory.decodeStream(imagefile, null, options);
//        	int imageHeight = options.outHeight;
//        	int imageWidth = options.outWidth;
//        	String imageType = options.outMimeType;
//        	
//        	Bitmap photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        	ImageView newView = (ImageView) findViewById(R.id.imageView1);
        	newView.setImageBitmap(photo);
        	
        	
        	//Mat mat = new Mat();
        	//Utils.bitmapToMat(photo, mat);
        	//bool found = findChessboardCorners(mat, boardSize, ptvec, CV_CALIB_CB_ADAPTIVE_THRESH );
        	
            //Intent intent2 = new Intent(this, CorrectionActivity.class);
            //intent2.putExtra("photo", photo);
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
