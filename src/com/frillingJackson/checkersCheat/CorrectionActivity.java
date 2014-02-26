package com.frillingJackson.checkersCheat;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class CorrectionActivity extends Activity {
	
	ImageView correctionView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_correction);
		
		Bundle bundle = this.getIntent().getExtras();
		Bitmap photo = bundle.getParcelable("photo");
		for(int x=0; x < photo.getHeight(); x+=2){
			for(int y=0; y<photo.getWidth(); y+=2){
				photo.setPixel(x, y, 0);
			}
		}
		correctionView = (ImageView)findViewById(R.id.correctionView);
		correctionView.setImageBitmap(photo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.correction, menu);
		return true;
	}

}
