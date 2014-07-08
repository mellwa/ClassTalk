package com.example.classtalk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

@SuppressLint("NewApi")
public class Chat extends LinearLayout implements Observer{

	private TextView chat;
	private ScrollView scroll;
	private EditText input;
	private Button send;
	private String inputString = "";
		
	Model model;
	Client client;
	PrintWriter out;
	String received_message;
	Talk talk;
	int sendBuilding = 1;
	Socket clientSocket = null;
	BufferedReader inputBuffer = null;
	String hostname;
	int PortNumber;
	int littleToBig(int i)
	{
	    int b0,b1,b2,b3;

	    b0 = (i&0xff)>>0;
	    b1 = (i&0xff00)>>8;
	    b2 = (i&0xff0000)>>16;
	    b3 = (i&0xff000000)>>24;
	    int a = ((b0<<24)|(b1<<16)|(b2<<8)|(b3<<0));
		Log.d("Chat" , " get into chat"+a + " " + b0 + " " + b1 + " " + b2 + " " + b3);
	    return a;
	}
	public Chat(Context context , Model m, Talk t) throws IOException {
		super(context);
		model = m;
		talk = t;
		model.addObserver(this);
		

		
		View.inflate(context, R.layout.activity_chat, this);
		
		Log.d("Chat" , " get into chat" );
		
		chat = (TextView) findViewById(R.id.textView1);	
		scroll = (ScrollView) findViewById(R.id.scrollView1);
		input = (EditText) findViewById(R.id.input);
		send = (Button) findViewById(R.id.send);
		
		input.addTextChangedListener( new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				inputString = input.getText().toString();
				Log.d("MainActivity" , "Building "+inputString);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
		});
	
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String name = model.getName();
				if (sendBuilding == 1) {
					//String BuildRoom = model.GetBuilding() + model.GetRoom();
					
					sendBuilding = 0;
				}
				if(out != null){
					out.write(name +":"+ inputString);
					out.flush();
				}
				model.setBuffer(name + ":"+ inputString);
				model.initObservers();
				input.setText("");
			}
		});
		Log.d("Chat" , " here chat" );
		
		client = new Client("ubuntu1204-006.student.cs.uwaterloo.ca",56171, model,talk);
		//String message = null;
		Log.d("Chat" , " connect server" );
		/*inputBuffer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		hostname = inputBuffer.readLine();
		PortNumber = inputBuffer.read();
		
		client = new Client(hostname,PortNumber, model,talk);*/
		
		//client.execute();
		Log.d("Chat" , " execute server" );
		out = client.getPrintWriter();
		DataOutputStream out_stream = new DataOutputStream(client.clientSocket.getOutputStream());
		Log.d("Chat" , " out server" );
		String BuildRoom = model.GetBuilding() + model.GetRoom();
		out_stream.writeInt(1);
		//Log.d("Chat" , " write server" );
		//out.flush();
		//out.write("Chao");
		out.flush();
		Log.d("Chat" , " finish chat" );
	}

	public void receivedMessage(String s){
		received_message = s;
	}
	@Override
	public synchronized void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(chat != null){
			chat.setText(model.getBuffer());
			scroll.scrollTo(0,chat.getBottom());
		}
	}

}