package gsls.api.mc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerPingAPI {
	
	private Socket socket = new Socket();
	private String host = null;
	private int port = 0;
	private OutputStream out = null;
	private InputStream in = null;
	private String[] data = new String[999];
	  
	public ServerPingAPI(String host1, int port1) {
		this.host = host1;
	    this.port = port1;
	    try {
	    	this.socket.connect(new InetSocketAddress(this.host, this.port));
	        this.out = this.socket.getOutputStream();
	        this.in = this.socket.getInputStream();
	        this.out.write(254);
	      
	        StringBuffer str = new StringBuffer();
	        int b;
	        while ((b = this.in.read()) != -1) {
	        	if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
	        		str.append((char)b);
	        	}
	        } 
	        this.data = str.toString().split("§");
	        this.data[0] = this.data[0].substring(1, this.data[0].length());
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
	}
	  
	public String getMotd() {
		return this.data[0]; 
	}
	
	public int getOnline() {
		return Integer.parseInt(this.data[1]);
	}
	
	public int getMax() {
		return Integer.parseInt(this.data[2]);
	}
	
	public void update() {
		try {
			this.socket.close();
	        this.socket = new Socket();
	        this.socket.connect(new InetSocketAddress(this.host, this.port));
	        this.out = this.socket.getOutputStream();
	        this.in = this.socket.getInputStream();
	        this.out.write(254);
	      
	        StringBuffer str = new StringBuffer();
	        int b;
	        while ((b = this.in.read()) != -1) {
	        	if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
	        		str.append((char)b);
	        	}
	        } 
	        this.data = str.toString().split("§");
	        this.data[0] = this.data[0].substring(1, this.data[0].length());
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
	}

}
