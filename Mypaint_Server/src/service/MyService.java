package service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class MyService {
    List<Socket> socketlist;
    ServerSocket serverSocket = null;
    Socket socket = null;
    boolean flag = true;
  
	public MyService() {
		// TODO Auto-generated constructor stub
		
		socketlist = new ArrayList<Socket>();
		try {
			serverSocket = new ServerSocket(8010);
			System.out.println("服务器启动服务.....");
			
			  while(true){
				  socket = serverSocket.accept();
				System.out.println("服务器与客户端建立连接");
				socketlist.add(socket);//将该客户端的socket保存到socletlist中
				new InteractiveService(socket).start();//创建一个单独的线程与该客户端进行交互
			    
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
	
class InteractiveService extends Thread{
	 private Socket socket = null;
	 private InputStream inputStream = null;
	
	 public InteractiveService(Socket socket){
		 this.socket = socket;
	 }
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(flag){
		String result;
		try {
			result = ReadData(inputStream);
			 if(!result.equals("")&&result!=null){
				 //  System.out.println(result);
				   send(result);
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		}
	}
}
private String ReadData(InputStream inputStream) throws IOException{
	 
	 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 String result = "";
	 byte[] data = new byte[1024];
		int len = 0;
	if(inputStream!=null&&inputStream.available()!=0){
		 
		try {
			if ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
				result = new String(data,0,len);
				
			}
			result = new String(outputStream.toByteArray(), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		return result;
	}
	return result;
	
}
private void send(String str){
	OutputStream out = null;
	 for(int i=0;i<socketlist.size();i++){
		 socket = socketlist.get(i);
		 try {
			 out = socket.getOutputStream();
			out.write(str.getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
public static void main(String[] args) {
	
		new MyService();
	
}
}
