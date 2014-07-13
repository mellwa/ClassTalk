package com.example.classtalk;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.facebook.*;
import com.facebook.Session.StatusCallback;
import com.facebook.android.*;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity implements Observer, OnClickListener {

	Button LoginButt;
	Model model;
	EditText login_name;
	String personName="null";
	boolean getname = false;
	ImageView fb_login_button;
	facebook_helper fb_helper;
	Session activeSession;
	String id;
	DialogListener dialoglistener;
	Session session;
	View view;
	
	EditText signup_name;
	EditText real_name;
	EditText signup_password;
	EditText login_password;
	String user_password = "null";
	String personName_real =  "null";
	Button SignUpButt;
	Button goToSignUpButt;
	Button goToLogin;
	ArrayList<String> DCrooms;
	ArrayList<String> MCrooms;
	Client client;
	String feedback1;
	Login login;
	AlertDialog alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "MainActivity:onCreat");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.login);
		rl.setBackgroundResource(R.drawable.dc);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		LoginButt = (Button)findViewById(R.id.login_button);
		login_name = (EditText)findViewById(R.id.login_name);
		login_name.setBackground(getResources().getDrawable(R.drawable.login_rec));
		login_password = (EditText)findViewById(R.id.login_password);
		login_password.setBackground(getResources().getDrawable(R.drawable.login_rec));
		signup_name = (EditText)findViewById(R.id.signup_name);
		signup_name.setBackground(getResources().getDrawable(R.drawable.login_rec));
		signup_password = (EditText)findViewById(R.id.signup_password);
		signup_password.setBackground(getResources().getDrawable(R.drawable.login_rec));
		SignUpButt = (Button)findViewById(R.id.signup);
		goToSignUpButt = (Button)findViewById(R.id.goToSignUp);
		goToLogin = (Button)findViewById(R.id.goToLogin);
		goToLogin.setVisibility(View.INVISIBLE);
//		real_name = (EditText)findViewById(R.id.real_name);
		
		id = "855558154473328";
		fb_helper = new facebook_helper(id);
		
		fb_login_button = (ImageView) findViewById(R.id.facebook_login_button);
		
		fb_login_button.setOnClickListener(this);
		
		
		LoginButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
	            	personName = login_name.getText().toString();
	            	model.setName(personName);
	            	user_password = login_password.getText().toString();
	            	model.setPassword(user_password);
					while(client.doneconnecttobinder(client, "SIGN_INN"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("connect to binder","ERROR");
					e.printStackTrace();
				}
				
			}
		});
		
		SignUpButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					while(client.doneconnecttobinder(client, "SIGN_UPP"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("User sign up","ERROR");
					e.printStackTrace();
				}
			
			}
		});
		SignUpButt.setVisibility(View.INVISIBLE);
		
		login_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
		
		login_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
		
		
		signup_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            	personName = signup_name.getText().toString();
            	model.setName(personName);
            }
        });
		signup_name.setVisibility(View.INVISIBLE);
		
		signup_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub              
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            	user_password = signup_password.getText().toString();
            	model.setPassword(user_password);
            }
        });
		signup_password.setVisibility(View.INVISIBLE);
		
		goToSignUpButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SignUpButt.setVisibility(View.VISIBLE);
				signup_name.setVisibility(View.VISIBLE);
				signup_password.setVisibility(View.VISIBLE);
				LoginButt.setVisibility(View.INVISIBLE);
				login_name.setVisibility(View.INVISIBLE);
				login_password.setVisibility(View.INVISIBLE);
				goToLogin.setVisibility(View.VISIBLE);
				goToSignUpButt.setVisibility(View.INVISIBLE);
			}
		});
		
		goToLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				SignUpButt.setVisibility(View.INVISIBLE);
				signup_name.setVisibility(View.INVISIBLE);
				signup_password.setVisibility(View.INVISIBLE);
				LoginButt.setVisibility(View.VISIBLE);
				login_name.setVisibility(View.VISIBLE);
				login_password.setVisibility(View.VISIBLE);
				goToSignUpButt.setVisibility(View.VISIBLE);
				goToLogin.setVisibility(View.INVISIBLE);
			}
		});
		
		model = new Model();
		model.addObserver(this);
		model.initObservers();
		try {
			client = new Client("ubuntu1204-002.student.cs.uwaterloo.ca",59787, model, this);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//connect to binder
	}
	
	public void addBuildingRooms(String building,String room){
		if(building.equals("MC")){
			Log.d("Login: 263","add MC room into list");
			if(MCrooms == null){
				MCrooms = new ArrayList<String>();
			}
			MCrooms.add(room);
		}
		else if(building.equals("DC")){
			Log.d("Login: 267","add DC room into list");
			if(DCrooms == null){
				DCrooms = new ArrayList<String>();
			}
			DCrooms.add(room);
		}
		else{
			Log.d("Login: 270","add rooms failed");;
		}
		model.setMCrooms(MCrooms);
		model.setDCrooms(DCrooms);
	}
	
	public void Start() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("PersonName" , personName);
		intent.putExtra("passwrod", user_password);
		intent.putExtra("mcrooms" , MCrooms);
		intent.putExtra("dcrooms" , DCrooms);
		intent.putExtra("Model" , model);
		
		startActivity(intent);
	}	
	
	public void feedback (String feedback) {
		feedback1 = feedback;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(feedback1.equals("F")) {
					Log.d("Client101001", "faileuresrlssf dsala!");
					login_name.setText("");
					login_password.setText("");
					login.loginFailed();
				}
				else if(feedback1.equals("F_Signup")){
					login.signupFailed();
				}
				else if(feedback1.equals("N")) {
					Log.d("Client101001", " yea yea no server la");
					finish();
				}
				else if(feedback1.equals("S")) {
					Log.d("Client101001", "s  sss s tttt araa r rrr t");
//					client.doneconnecttobinder(client, "SIGN_INN")
					Start();
				}
			}
		});
		
	}
	
	void signupSuccessfull(){
		login = this;
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						login);
		 
					// set title
					alertDialogBuilder.setTitle("Congratulations!");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Signup successfully!")
						.setCancelable(false)
						.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
		});
	}
		
	void signupFailed(){
		login = this;
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						login);
		 
					// set title
					alertDialogBuilder.setTitle("Sign up failed");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("There is already such a user name!")
						.setCancelable(false)
						.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
		});
	}
	
	void loginFailed(){
		login = this;
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						login);
		 
					// set title
					alertDialogBuilder.setTitle("Login failed");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Login failed!")
						.setCancelable(false)
						.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
		});
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		fb_helper.facebook_login(arg0, this);
	}
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
}
