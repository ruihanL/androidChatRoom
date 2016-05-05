import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientThread implements Runnable{

	Socket s = null;
	BufferedReader br = null;
	int num = 0;	
	OutputStream os = null;
	BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
	
	
	public ClientThread(Socket socket,int n)
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
			

			String content = null;
			while ((content = readFromServer())!=null) {
				System.out.println(content);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}

	private String readFromServer()
	{
		try {
			return br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return null;
	}
	


}
