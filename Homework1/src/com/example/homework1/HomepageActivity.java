package com.example.homework1;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

public class HomepageActivity extends Activity implements OnItemClickListener {

	ImageView viewImage;
	Button img;
	Button save;
	private TextView tv1;
	private TextView welcome_msg;
	private TextView logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		Parse.initialize(this, "BmbiDoWmcHnINiTcLUymM6lcP84rHYd5XrfC2gFW",
				"KY1MGCdC2HawkP4msjtNx4Y1V3qNNWNEbSlEfG82");
		getIntent();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		// Convert currentUser into String
		String struser = currentUser.getUsername().toString();
		String email = currentUser.getEmail().toString();
		welcome_msg = (TextView) findViewById(R.id.txt_welcome);
		tv1 = (TextView) findViewById(R.id.txt_parse_email);
		logout = (TextView) findViewById(R.id.logout);
		viewImage = (ImageView) findViewById(R.id.viewImage);

		welcome_msg.setText("Welcome, " + struser);
		tv1.setText(email);

		
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Logout current user
				ParseUser.logOut();
				finish();
			}
		});

		// Locate Button in homepage.xml
		save = (Button) findViewById(R.id.saveprofile);

		img = (Button) findViewById(R.id.selectphoto);
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});


		
		
	}

	private void selectImage() {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				HomepageActivity.this);
		builder.setTitle("Add photo");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
