package com.example.classtalk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class Client{
	  String dstAddress;
	  int dstPort;
	  String response = "";
	  Socket clientSocket = null;
	  PrintWriter out = null;
	  BufferedReader input = null;
	  BufferedReader input2 = null;
	  Model model;
	  Talk talk;
	  String personName;
	  Login login;
	  boolean done = false;
	  String password;
	  
	  Client(String addr, int port, Model m,  Login login){
		  dstAddress = addr;
		  dstPort = port;
		  this.login = login;
		  model = m;
		  done = false;
		   new Thread(new ConnectToBinder()).start();
	  }
	  Client(String addr, int port, Model m, Talk t){
	   dstAddress = addr;
	   dstPort = port;
	   model = m;
	   talk = t;
	   new Thread(new ConnectToServer()).start();
	  }
	  
	  class ConnectToBinder implements Runnable{

		@Override
		public void run() {
			try {
				clientSocket = new Socket(dstAddress,dstPort);
				if(out == null)
				out  = new PrintWriter(clientSocket.getOutputStream(), true);
				Log.d("done the socket create","start to write to binder");
				//out.write("cc");//tell binder this is a client
				//out.write(1);//tell binder this is a client

				out.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				Log.d("from server","auiwhduidhauiwd we are catched");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("from server","iudahwdiuwhdi we are catched");

			}

			  while(true){
					String success = null;
					try {
						while(clientSocket == null){}
							Log.d("Socket ","create successfully");
						BufferedReader inputs = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						//BufferedReader inputt =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							Log.d("Client 10101020" , "od");
						success = inputs.readLine();
							Log.d("Client 10101020" , success);
						if(success.equals("F")) {
							Log.d("Client101001", "faileuresrlssf dsala!");
							login.feedback("F");
							Log.d("Client101001", "faileuresrlssf dsala!");
							break;
						}
						else if(success.equals("N")) {
							login.feedback("N");
							break;
						}
						else if(success.equals("S")) {
							int building;
							char[] buildings = new char[4];
							String emptystring = null;
							String room1 = null;
							String room2 = null;
							String room3 = null;
							String room4 = null;
							String room = null;
							
							success = inputs.readLine();
							Log.d("Client1010dfd01", " read llllaaa  " +  success);

							if(success.equals("1")) {
								done = true;
								login.feedback("S");
								break;
							}
							else if(success.equals("0")) {
								Log.d("Client1010dfd01", " si ge");
								//input =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
								inputs.read(buildings, 0, 4);
								int bu = Integer.parseInt(buildings.toString());
								
								Log.d("from binder ","building is " + bu + " " + buildings.toString()) ;
//								building = Integer.parseInt(buildings);
								
//								Log.d("Client1010dfd01", "Building " + building);
								
//								emptystring = inputs.readLine();
								room1 = inputs.readLine();
								Log.d("from binder ",room1);
								room2 = inputs.readLine();
								Log.d("from binder ",room2);
								room3 = inputs.readLine();
								Log.d("from binder ",room3);
								room4 = inputs.readLine();
								Log.d("from binder ",room4);
								
								room = room1 + room2 + room3 + room4;
								Log.d("from binder ",room + " " + buildings);
								
//								login.addBuildingRooms(buildings, room);
							}
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
		}
	  }
	  
	  boolean doneconnecttobinder(Client client , String sign_log) throws IOException{
		  personName = model.getName();
		  password = model.getPassword();
		  DataOutputStream out_stream = new DataOutputStream(client.clientSocket.getOutputStream());
		  
		  
		  out_stream.writeInt(1);
		  out_stream.writeBytes(sign_log);
		  
		  Log.d("Client105105105" , sign_log);
		  Log.d("Client105105105" , "person name is " + personName);
		  Log.d("Client105105105" , "password is " + password);
		  Log.d("Client105105105" , "name length is " + personName.length());
		  Log.d("Client105105105" , "password length is " + password.length());
		  
		  int length = personName.length();
		  out_stream.writeInt(length);
		  out_stream.writeBytes(personName);
		  out_stream.writeInt(password.length());
		  out_stream.writeBytes(password);
		  new Thread(new ConnectToBinder()).start();
		return done;
		
		  
	  }
	  
	  class ConnectToServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("from server","auiwhduidhauiwd we are catched");

			//PrintWriter out;
			try {
				clientSocket = new Socket(dstAddress,dstPort);
				if(out == null)
				out  = new PrintWriter(clientSocket.getOutputStream(), true);
//				out.println("hello server\n");
//				out.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				Log.d("from server","auiwhduidhauiwd we are catched");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("from server","iudahwdiuwhdi we are catched");

			}

			while(true){
				String message = null;
				try {
					while(clientSocket == null){}
					input =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					message = input.readLine();
					//while(clientSocket == null){}
					//input2 =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					//message = input.readLine();
					Log.d("from server ",message + " " + personName);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				talk.updateModel(message);
				Log.d("from server",message);
			}
		}
		  
	  }
	  



	public PrintWriter getPrintWriter(){
		int i = 0;
		while(out == null){
			//Log.d("client" , "out shi ge null" + i );
			i++;
		}
		return out;
	}

}
