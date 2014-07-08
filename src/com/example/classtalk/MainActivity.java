package com.example.classtalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends Activity implements Observer {
	
	Spinner BuildingSpinner;
	Spinner RoomSpinner;
	Button EnterButt;
	String inputBuilding;
	String inputRoom;
	Model model;
	ArrayList<String[]> Rooms;
	String[] buildings = {"DC","MC"};
	ArrayList<String> DCrooms;
	ArrayList<String> MCrooms;
	ArrayAdapter<String> adapter, adapter1, adapter2;
	String personName;
	Client client;
	//GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "MainActivity:onCreat");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.MainActivity);
		rl.setBackgroundResource(R.drawable.dc);
		client = new Client("ubuntu1204-004.student.cs.uwaterloo.ca",33787,this);
		
		BuildingSpinner = (Spinner) findViewById(R.id.Building_Spinner);
		RoomSpinner = (Spinner) findViewById(R.id.Room_Spinner);
		EnterButt = (Button) findViewById(R.id.Enter_Button);
		try {
			while(client.doneconnecttobinder(client));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//adapters for spinnners
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,buildings);
		BuildingSpinner.setAdapter(adapter);
		adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,DCrooms);
		adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,MCrooms);
		
		EnterButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Start(arg0);
			}
		});
		
//		BuildingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//			RelativeLayout rl = (RelativeLayout) findViewById(R.id.MainActivity);
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				
//				if(BuildingSpinner.getSelectedItem().toString().equalsIgnoreCase("DC")){
//					RoomSpinner.setAdapter(adapter1);	
//					rl.setBackgroundResource(R.drawable.dc);
//				}
//				else{
//					RoomSpinner.setAdapter(adapter2);
//					rl.setBackgroundResource(R.drawable.mc);
//				}
//				inputBuilding = BuildingSpinner.getSelectedItem().toString();
//			}
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub	
//			}		
//		});
//		
//		RoomSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				inputRoom = RoomSpinner.getSelectedItem().toString();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		model = new Model();
		model.addObserver(this);
		model.initObservers();
	}
	
	public void addBuildingRooms(int building,String room){
		if(building == 1){
			MCrooms.add(room);
		}
		else if(building == 2){
			DCrooms.add(room);
		}
		else;
	}
	
	public void Start(View v) {
		Intent intent = new Intent(this, Talk.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("BuildingET" , inputBuilding);
		intent.putExtra("RoomET", inputRoom);
		intent.putExtra("PersonName", personName);
		Log.d("MainActivity!!!!!!!!!!!!!" , inputBuilding + inputRoom);
		startActivity(intent);
	}

	public void Exit(View v) {
		finish();
		System.exit(0);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("chaochen","back here");
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			//do things on resume mode
			personName = extras.getString("PersonName");
			Log.d("chaochen","not null");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*for rotation*/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//save all the data
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//do all restore stuff
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
