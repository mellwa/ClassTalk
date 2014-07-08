package com.example.classtalk;

import java.util.Observable;
import java.util.Observer;

import com.facebook.*;
import com.facebook.Session.StatusCallback;
import com.facebook.android.*;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.*;

import android.app.Activity;
import android.content.Intent;
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

public class Login extends Activity implements Observer, OnClickListener {

	Button LoginButt;
	Model model;
	EditText login_name;
	String personName="null";
	boolean getname = false;
	ImageView fb_login_button;
	Facebook fb;
	Session activeSession;
	String id;
	DialogListener dialoglistener;
	Session session;
	View view;
	
	EditText signup_name;
	EditText signup_password;
	EditText login_password;
	String user_password = "null";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "MainActivity:onCreat");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.login);
		rl.setBackgroundResource(R.drawable.dc);

		LoginButt = (Button)findViewById(R.id.button1);
		login_name = (EditText)findViewById(R.id.editText1);
		login_password = (EditText)findViewById(R.id.login_password);
		signup_name = (EditText)findViewById(R.id.signup_name);
		signup_password = (EditText)findViewById(R.id.signup_password);
		
		id = "855558154473328";
		fb = new Facebook(id);
		
		fb_login_button = (ImageView) findViewById(R.id.facebook_login_button);
		
		fb_login_button.setOnClickListener(this);
		
		
		LoginButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Start(arg0);
			}
		});
		
		login_name.addTextChangedListener(new TextWatcher() {

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
                personName = login_name.getText().toString();
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
            	user_password = login_password.getText().toString();
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
            	personName = login_password.getText().toString();
            }
        });
		
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
            }
        });
		
		model = new Model();
		model.addObserver(this);
		model.initObservers();
	}
	
		public void Start(View v) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("PersonName" , personName);
			startActivity(intent);
		}	
		
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		view = arg0;
		if(activeSession != null && activeSession.isOpened()){
			if(!getname){
				Request.newMeRequest(activeSession, new Request.GraphUserCallback(){
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						personName = user.getName();
						getname = true;
						Log.d("user name",personName);
						Start(view);
					}
		        }).executeAsync();
			}
		}
		else{
			activeSession = Session.getActiveSession();
	        if (activeSession == null || activeSession.getState().isClosed()) {
	            activeSession = new Session.Builder(this).setApplicationId(id).build();
	            Session.setActiveSession(activeSession);
	        }
	        StatusCallback callback = new StatusCallback() {
	            public void call(Session session, SessionState state, Exception exception) {
	                if (exception != null) {
	                    System.exit(-1);
	                }
	            }
	        };
	        activeSession.openForRead(new Session.OpenRequest(this).setCallback(callback));
		}
	}
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
}
