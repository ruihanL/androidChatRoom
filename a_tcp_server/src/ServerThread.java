import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ServerThread implements Runnable{
	Socket s = null;
	BufferedReader br = null;
	int num = 0;
	
	OutputStream os = null;
	
	public ServerThread(Socket socket,int n)
	{
		s = socket;
		num = n;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));
			os = s.getOutputStream();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			writeString(" is in.");
			System.out.println("["+num+"] is in.");
			
			String content = null;
			while ((content = readFromClient())!=null) {
				
				for(Socket socket : SimpleServer.sockets)
				{
					OutputStream os = socket.getOutputStream();
					writeString(content,os);
					
				}
				
				System.out.println("["+num+"] "+content);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			SimpleServer.sockets.remove(s);
		}
	}

	
	private String readFromClient()
	{
		try {
			return br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			SimpleServer.sockets.remove(s);
		}
		return null;
	}
	
	private void writeString(String string)
	{
		try {
			os.write(("["+num+"] "+string+"\n").getBytes("utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("socket write error.");
			e.printStackTrace();
		}
	}
	
	private void writeString(String string,OutputStream os)
	{
		try {
			os.write(("["+num+"] "+string+"\n").getBytes("utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("socket write error.");
			e.printStackTrace();
		}
	}
}
