package com.frillingJackson.checkersCheat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
 
public class CamActivity extends Activity {
    // Log tag for camera
    private static final String TAG = "CamActivity";

    // set the type of media -&gt; for image code is 1
    public static final int MEDIA_TYPE_IMAGE = 1;
 
    private Camera mCamera;//This activity's camera reference
    private CameraPreview mPreview; //This activity's preview view (...?)
 
    private ImageButton mSnapButton;
    private ImageView mOutline;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
 
        Boolean hasCamera = checkCameraHardware(getApplicationContext());
        if(!hasCamera){
            Toast.makeText(getApplicationContext(), "Camera Not Available", Toast.LENGTH_LONG).show();
	    finish();//This activity is useless otherwise --bpj
        }
    }
 
    private Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90); //As soon as the camera is opened, the display orientation is set --bpj
 
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
	    Log.d(TAG, "error in getCamInstance"+e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }
    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

   	 	//final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

   	 	int h= (int) (newHeight);
   	 	int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

   	 	return Bitmap.createScaledBitmap(photo, w, h, true);
   	 }

    private PictureCallback mPicture = new PictureCallback() { 
        public void onPictureTaken(byte[] data, Camera camera) {
        	Log.d(TAG, "Don't skip this.");
        	
        	Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length);
        	Log.d(TAG, "Size of photo" + photo.getWidth() + " x " + photo.getHeight());
        	photo = scaleDownBitmap(photo, 500, getApplicationContext());
        	Log.d(TAG, "Size of photo" + photo.getWidth() + " x " + photo.getHeight());
        	
        	
        	Log.d(TAG, "onPicTaken 1:");
            Intent in1 = new Intent(getApplicationContext(), FirstUse.class);
            Log.d(TAG, "onPicTaken 2:");
            in1.putExtra("image", photo);
            Log.d(TAG, "onPicTaken 3:");
            setResult(RESULT_OK, in1);  
            Log.d(TAG, "onPicTaken 4:");
         
            finish(); 
        }
    };
 
    @Override
    protected void onPause() {
        super.onPause();
        
	if (mCamera != null) {
		Log.d(TAG, "onPause 1:");
	    //mCamera.setPreviewCallback(null); 
	    Log.d(TAG, "onPause 2:");
	    mPreview.getHolder().removeCallback(mPreview);//What does this do? --bpj
	    Log.d(TAG, "onPause 3:");
	    //mCamera = null;
	    mCamera.release(); // release the camera for other applications
        }
	Log.d(TAG, "onPause 4:");     
	RelativeLayout preview = (RelativeLayout)findViewById(R.id.camera_preview);
	preview.removeView(mPreview);
	mPreview = null;            
    }
 
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
 
	mCamera = getCameraInstance();
	mCamera.setPreviewCallback(null); 
             
	// Create our Preview view and set it as the content of our activity.
	mPreview = new CameraPreview(this, mCamera);
	RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
	preview.addView(mPreview);
            
	mSnapButton = (ImageButton) findViewById(R.id.button_capture);
	mSnapButton.bringToFront();
             
	mOutline = (ImageView) findViewById(R.id.imageView1);
	mOutline.bringToFront();
      
	mSnapButton.setOnClickListener(new OnClickListener() {
		@Override
		    public void onClick(View v) {
		    mCamera.takePicture(null, null, mPicture);     
		}
	    });
    }
 
    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
