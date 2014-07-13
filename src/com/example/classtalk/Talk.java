package com.example.classtalk;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class Talk extends Activity implements Observer {
	
	private Model model;
	private String Building;
	private String Room;
	Heading heading;
	Chat chat;
	String personName;
	Client client;
	String server_hostname;
	String server_port;
	
	//blaite: indicate two views here as well

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Talk" , "Talk: on create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talk);
		
		model = new Model();
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			//blaite: save all the data
			Building = extras.getString("BuildingET");
			Room = extras.getString("RoomET");
			personName = extras.getString("PersonName");
			model.SetBuilding(Building);
			model.SetRoom(Room);
			model.setName(personName);
			Log.d("Talk" , Building + Room);
		}
		
		update(model, extras);
		
		client = new Client("ubuntu1204-002.student.cs.uwaterloo.ca",59787, model,this,0);
		client.requestServerInfo();
		server_hostname = client.getHost();
		server_port = client.getPort();
		Log.d("Talk", "on post create");
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		
		
		//blaite: create two views to the talk activity
		heading = new Heading(this, model);
		ViewGroup v1 = (ViewGroup) findViewById(R.id.mainactivity_1);
		v1.addView(heading);
		
		try {
			chat = new Chat(this, model,this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ViewGroup v2 = (ViewGroup) findViewById(R.id.mainactivity_2);
		v2.addView(chat);
		
		//initialize views
		model.initObservers();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		
	}
	
	public void Start(View v){
		finish();
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void updateModel(String s){
		model.setBuffer(s);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				model.initObservers();
			}
		});
	}
	
	String getServerHost(){
		return server_hostname;
	}
	
	String getServerPort(){
		return server_port;
	}
	
	public void restart(View v){
		Log.d("Talk" , "restarted");
		//blaite: methods that need to be implemented in model
		/*model.end();
		model.start();
		model.resetButtons();*/
	}
	
	public void Exit(View v) {
		finish();
		Log.d("Talk","exittttttttttttttttt");
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	//blaite: on save instance state and on restore instance state
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		
		
	}
	

}
