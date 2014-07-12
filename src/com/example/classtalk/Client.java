package com.example.classtalk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	  
	  Client(String addr, int port, Model m,  Login login) throws UnknownHostException, IOException{
		  dstAddress = addr;
		  dstPort = port;
		  this.login = login;
		  model = m;
		  done = false;

	  }
	  Client(String addr, int port, Model m, Talk t){
	   dstAddress = addr;
	   dstPort = port;
	   model = m;
	   talk = t;
	   new Thread(new ConnectToServer()).start();
	  }
	  
	  
	  
	  class ConnectToBinder implements Runnable{
		  Client client;
		  String sign_log;
		  public ConnectToBinder(Client client, String sign_log) {
			  this.client = client;
			  this.sign_log = sign_log;
		  }
		@Override
		public void run() {
			try {
				client.clientSocket = new Socket(dstAddress,dstPort);
				if(client.clientSocket == null){
					Log.d("Socket","creation failed");
					System.exit(-1);
				}
				DataOutputStream out_stream = new DataOutputStream(client.clientSocket.getOutputStream());
				  
				  out_stream.writeInt(1);
				  out_stream.writeBytes(sign_log);
				  
				  Log.d("Client105105105" , sign_log);
				  
				  int length = personName.length();
				  out_stream.writeInt(length);
				  out_stream.writeBytes(personName);
				  out_stream.writeInt(password.length());
				  out_stream.writeBytes(password);

				//out.flush();
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
						//BufferedReader inputs = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						InputStream is = clientSocket.getInputStream();
							//BufferedReader inputt =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							byte[] buffer = new byte[1];
							is.read(buffer);
						//success = inputs.readLine();
							success = new String(buffer);
							Log.d("Client:103" , success);
						if(success.equals("F")) {
							Log.d("Client:105", "faileuresrlssf dsala!");
							login.feedback("F");
							break;
						}
						else if(success.equals("N")) {
							login.feedback("N");
							break;
						}
						else if(success.equals("S")) {
							while(true){
								int building;
								String room = null;
								
								byte[] buffer2 = new byte[1];
								is.read(buffer2);
								//success = inputs.readLine();
								success = new String(buffer2);
								Log.d("Client:line121", " read llllaaa  " +  success);
	
								if(success.equals("1")) {
									done = true;
									login.feedback("S");
									break;
								}
								else if(success.equals("0")) {
									Log.d("Client1010dfd01", " si ge");
									//input =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
									//inputs.read(buildings, 0, 4);
									byte[] buffer3 = new byte[2];
									int bu = is.read(buffer3);
									String buildings = new String(buffer3);
									
									Log.d("Client: 137 ","building is " + buildings) ;
									byte[] buffer4 = new byte[4];
									bu = is.read(buffer4);
									room = new String(buffer4);
									Log.d("Client: 141 ","the room number is "+room);
									
									login.addBuildingRooms(buildings, room);
									
								}
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

		  Log.d("Client105105105" , "person name is " + personName);
		  Log.d("Client105105105" , "password is " + password);
		  Log.d("Client105105105" , "name length is " + personName.length());
		  Log.d("Client105105105" , "password length is " + password.length());
		  
		  
		  new Thread(new ConnectToBinder(client, sign_log)).start();
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
