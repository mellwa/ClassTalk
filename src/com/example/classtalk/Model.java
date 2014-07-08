package com.example.classtalk;

import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class Model extends Observable{
	String Building;
	String Room;
	String personName;
	String user_password;
	
	StringBuffer buffer = new StringBuffer();
	
	boolean canEnter = false;
	
	public String getName() {
		return personName;
	}
	
	public void setName(String name) {
		personName = name;
	}
	
	public String getPassword() {
		return user_password;
	}
	
	public void setPasswrod(String password) {
		user_password = password;
	}
	
	public void EnableEnter() {
		canEnter = true;
		setChanged();
		notifyObservers();
	}
	
	public void DisableEnter() {
		canEnter = false;
		setChanged();
		notifyObservers();
	}
	
	//Set Building and Room
	public void SetBuilding(String name) {
		Building = name;
		setChanged();
		notifyObservers();
	}
	
	public void SetRoom(String name) {
		Room = name;
		setChanged();
		notifyObservers();
	}
	
	public String GetBuilding() {
		return Building;
	}
	
	public String GetRoom() {
		return Room;
	}
	
	//chat buffer
	
	public synchronized void setBuffer(String s) {
		buffer.append(s + "\n");
	}
	public synchronized String getBuffer(){
		return buffer.toString();
	}
	
	//Observer methods
	
	public synchronized void initObservers() {
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void addObserver(Observer observer) {
		Log.d("Model", "addObserver");
		
		super.addObserver(observer);
	}
	
	@Override
	public void notifyObservers() {
		Log.d ("Model", "notify observer");
		super.notifyObservers();
	}
}
