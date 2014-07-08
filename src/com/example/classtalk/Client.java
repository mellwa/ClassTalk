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
	  MainActivity main;
	  boolean done = false;
	  
	  Client(String addr, int port, MainActivity main){
		  dstAddress = addr;
		  dstPort = port;
		  this.main = main;
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
				out.write("cc");//tell binder this is a client
				out.write(1);//tell binder this is a client

				out.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				Log.d("from server","auiwhduidhauiwd we are catched");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("from server","iudahwdiuwhdi we are catched");

			}

			while(true){
				int num;
				int building;
				String room = null;
				try {
					while(clientSocket == null){}
					Log.d("Socket ","create successfully");
					input =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					num = input.read();
					if(num == 1){
						done = true;
						break;
					}
					building = input.read();
					room = input.readLine();
					Log.d("from binder",room);
					main.addBuildingRooms(building, room);
					//while(clientSocket == null){}
					//input2 =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					//message = input.readLine();
					Log.d("from server ",room + " " + building);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	  }
	  
	  boolean doneconnecttobinder(Client client) throws IOException{
		  DataOutputStream out_stream = new DataOutputStream(client.clientSocket.getOutputStream());
		  out_stream.writeInt(1);
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
