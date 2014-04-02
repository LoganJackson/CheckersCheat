package com.frillingJackson.checkersCheat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
 
public class CamActivity extends Activity {
 
    // Log tag for camera
    private static final String TAG = "CameraRecorderActivity";
    // set the type of media -&gt; for image code is 1
    public static final int MEDIA_TYPE_IMAGE = 1;
 
    private Camera mCamera;
    private CameraPreview mPreview;
 
    private ImageButton mSnapButton;
    private ImageView mOutline;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
 
        Boolean hasCamera = checkCameraHardware(getApplicationContext());
        if(!hasCamera){
            Toast.makeText(getApplicationContext(), "Camera Not Available", Toast.LENGTH_LONG).show();
        }
       
    }
 
    private Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            //mCamera.setDisplayOrientation(90);
 
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        	Log.d(TAG, "error in getCamInstance"+e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }
 
    private PictureCallback mPicture = new PictureCallback() {
 
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
 
//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if (pictureFile == null) {
//                Log.d(TAG,
//                        "Error creating media file, check storage permissions");
//                return;
//            }
// 
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                fos.close();
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
//            //mCamera.startPreview();
//            
//            // call alert dialog to display file saved location
//            alertDialog("File Saved",
//                    "Filse is saved at sdcard-&gt;Pictures-&gt;MyCameraApp");
//            
//            //mCamera.stopPreview();
//            
//            //Prepare to return an intent and finish
            Intent in1 = new Intent(getApplicationContext(), FirstUse.class);
            in1.putExtra("image",data);
            setResult(RESULT_OK, in1);
            
            releaseCamera(); //do we want to release the camera here? 
            
            finish(); 
        }
    };
 
    /** Create a File for saving an image */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
 
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "CheckersCheat");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
 
        return mediaFile;
    }
 
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // release the camera
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release(); // release the camera for other applications
            mCamera = null;
            RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
            preview.removeView(mPreview);
            mPreview = null;
        }
    }
 
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
 
        // Getting the camera resources back
        try {
        	mCamera = getCameraInstance();
        	mCamera.setDisplayOrientation(90);
             
            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
            
            mSnapButton = (ImageButton) findViewById(R.id.button_capture);
            mSnapButton.bringToFront();
             
            mOutline = (ImageView) findViewById(R.id.imageView1);
            mOutline.bringToFront();
      
            mCamera.setPreviewCallback(null);
             // capture image button
            mSnapButton.setOnClickListener(new OnClickListener() {
      
                 @Override
                 public void onClick(View v) {
                     // TODO Auto-generated method stub
                     // get an image from the camera
                     mCamera.takePicture(null, null, mPicture);     
                 }
             });
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera from onResume: " + e.getMessage());
        }
    }
 
    // Releasing the camera resources
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }
    }
 
    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
 
    // Alert Dialog to show saved Image location
    public void alertDialog(String title, String message) {
        AlertDialog.Builder flashErrorAlert = new AlertDialog.Builder(
                CamActivity.this);
        // set title
        flashErrorAlert.setTitle(title);
        flashErrorAlert.setMessage(message);
        flashErrorAlert.setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
 
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = flashErrorAlert.create();
        alertDialog.show();
    }
 
}
