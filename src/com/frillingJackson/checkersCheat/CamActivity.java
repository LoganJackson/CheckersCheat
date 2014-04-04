package com.frillingJackson.checkersCheat;

import java.io.File;
import java.io.FileOutputStream;

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

    private PictureCallback mPicture = new PictureCallback() { 
	    public void onPictureTaken(byte[] data, Camera camera) {
        	Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length);
        	Log.d(TAG, "Orig. size of photo" + photo.getWidth() + " x " + photo.getHeight()); 
           
        	File pictureFile = new File(getFilesDir(), "photo.jpg");
        	try {
        		FileOutputStream fos = new FileOutputStream(pictureFile);
        		fos.write(data);
        		fos.close();
        		Toast.makeText(getApplicationContext(), "New Image saved:" + pictureFile,
				   Toast.LENGTH_LONG).show();
        	} catch (Exception error) {
        		Toast.makeText(getApplicationContext(), "Image could not be saved.", Toast.LENGTH_LONG).show();
        	}
        	Intent in1 = new Intent(getApplicationContext(), FirstUse.class);
        	in1.putExtra("image", pictureFile);
        	setResult(RESULT_OK, in1);  
        	finish(); 
        }
    };
 
    @Override
    protected void onPause() {
        super.onPause();
        
	if (mCamera != null) {
	    mPreview.getHolder().removeCallback(mPreview);//What does this do? --bpj
	    mCamera.release(); // release the camera for other applications
        }    
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
