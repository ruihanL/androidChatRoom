import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class SimpleClient {

	public static OutputStream os = null;
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Client is running.");
		Socket socket = new Socket("192.168.1.111", 30000);
		
		new Thread(new ClientThread(socket, 0)).start();
		
		
		os = socket.getOutputStream();
		
		BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
		
		String readline = sin.readLine();
		
		
		while(!readline.equals("bye"))
		{
			writeString(readline);
			
			readline=sin.readLine();			
		}
		
		os.close(); //关闭Socket输出流
		
		socket.close(); //关闭Socket	
			
		
	}

	private static void writeString(String string)
	{
		try {
			os.write((string+"\n").getBytes("utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("socket write error.");
			e.printStackTrace();
		}
	}
}
