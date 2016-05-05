package com.androidChatRoomClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Thread thread;
	
	
	EditText editText;
	TextView textView;
	Button button;
	
	Handler handler;
	ClientThread clientThread;
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText)findViewById(R.id.editText1);
		textView = (TextView)findViewById(R.id.textView1);
		button = (Button)findViewById(R.id.button1);
		
		handler = new Handler()
		{

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0x123) {
					textView.append("\n"+msg.obj.toString());
					textView.setMovementMethod(ScrollingMovementMethod.getInstance());
				}				
			}			
		};
		
		clientThread = new ClientThread(handler);
		
		thread = new Thread(clientThread);
		thread.start();
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String textString = editText.getText().toString();
					Message msg = new Message();
					msg.what = 0x345;
					msg.obj = textString;
					clientThread.revHandler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			thread.stop();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
