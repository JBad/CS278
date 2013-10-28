package com.example.snapclone;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;

public class DetailView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_view);
		ImageView pictureView = (ImageView)findViewById(R.id.pictureView);
		Intent intent = getIntent();
		String filename = intent.getStringExtra("picFile");
		pictureView.setImageURI(Uri.parse(filename));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_view, menu);
		return true;
	}

}
