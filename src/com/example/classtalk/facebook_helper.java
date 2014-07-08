package com.example.classtalk;

import android.util.Log;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;

//facebook helper class   
public class facebook_helper {
	static Facebook fb;
	static Session activeSession;
	static String id = null;
	View view;
	String user_name = null;
	public facebook_helper(String fb_id) {
		// TODO Auto-generated constructor stub
		id = fb_id;
		fb = new Facebook(id);
	}
	void facebook_login(View v, Login login){
		view = v;
		if(activeSession != null && activeSession.isOpened()){
			Request.newMeRequest(activeSession, new Request.GraphUserCallback(){
				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					//if(user != null)
					user_name = user.getName();
					Log.d("user name",user_name);
				}
	        }).executeAsync();
		}
		else{
			activeSession = Session.getActiveSession();
	        if (activeSession == null || activeSession.getState().isClosed()) {
	            activeSession = new Session.Builder(login).setApplicationId(id).build();
	            Session.setActiveSession(activeSession);
	        }
	        StatusCallback callback = new StatusCallback() {
	            public void call(Session session, SessionState state, Exception exception) {
	                if (exception != null) {
	                    System.exit(-1);
	                }
	            }
	        };
	        activeSession.openForRead(new Session.OpenRequest(login).setCallback(callback));
	        //facebook_login(view, login);
		}
	}
	
	boolean isSessionValid(){
		if(activeSession == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	String getUserName(){
		if(this.user_name != null){
			return user_name;
		}
		Log.d("facebook user name","is null");
		return null;
	}

}

