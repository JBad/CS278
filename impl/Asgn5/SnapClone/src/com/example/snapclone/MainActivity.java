package com.example.snapclone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public String currentPic = "";
	public static String SERVER_ADDRESS = "10.67.135.63";
	private File recentPic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void takePicture(View view) throws IOException{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, 1);
		String timeStamp = 
		        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = timeStamp + "_";
		File image = File.createTempFile(imageFileName, ".jpeg");
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
		recentPic = image;
		socketLogic logic = new socketLogic("send");
		Thread handle = new Thread(logic);
		handle.start();
	}
	
	public void viewImage(View view) {
		socketLogic logic = new socketLogic("pull");
		Thread handle = new Thread(logic);
		handle.start();
		Intent intent = new Intent(MainActivity.this, DetailView.class);
		intent.putExtra("picFile", recentPic.toString());
		MainActivity.this.startActivity(intent);
	}
	
	public void register(View view) {
		socketLogic logic = new socketLogic("user");
		Thread handle = new Thread(logic);
		handle.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class socketLogic implements Runnable {
		private Socket socket;
		private String logic;
		socketLogic(String toLogic) {
			logic = toLogic;
		}
		
		public void run () {
			try{
				socket = new Socket("10.67.135.63", 5555);
				if(logic.equals("user")){
					EditText user = (EditText) findViewById(R.id.userName);
					DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
					outStream.write(("newUser;" + user.getText().toString()).getBytes());
				}
				else if(logic.equals("picture")) {
					DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
					outStream.write(("newImage;").getBytes());
					
					long length = recentPic.length();
					byte[] bytes = new byte[(int) length];
	        		FileInputStream fileIn = new FileInputStream(recentPic);
	        		BufferedInputStream bufFile = new BufferedInputStream(fileIn);
	        		BufferedOutputStream toClient = new BufferedOutputStream(socket.getOutputStream());
	        		
	        		int count;
	        		while ((count = bufFile.read(bytes)) > 0) {
	        			toClient.write(bytes, 0, count);
	        		}

	        		toClient.flush();
	        		toClient.close();
	        		fileIn.close();
	        		bufFile.close();

	        		DataInputStream in = new DataInputStream (socket.getInputStream());
	        		currentPic = in.readLine();
				}
				else if(logic.equals("pull")) {
					DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
					outStream.writeBytes("pull;" + currentPic);
					

		        	InputStream fileStream = socket.getInputStream();
		            int bufferSize = socket.getReceiveBufferSize();
					FileOutputStream tempFile = new FileOutputStream(recentPic);
		            BufferedOutputStream bufStream = new BufferedOutputStream(tempFile);
		            byte[] bytes = new byte[bufferSize];
		            int count;
		            while ((count = fileStream.read(bytes)) > 0) {
		            	bufStream.write(bytes, 0, count);
		            }
		            
		            bufStream.flush();
		            bufStream.close();
		            fileStream.close();
		            
		            //update number
		            DataInputStream in = new DataInputStream (socket.getInputStream());
	        		currentPic = in.readLine();   
				}

				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
