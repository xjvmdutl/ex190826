package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP="192.168.1.2";
	private static int SERVER_PORT=5000;
	public static void main(String[] args) {
		Scanner sc = null;
		Socket socket = null;
		try {
			sc = new Scanner(System.in);
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			//join 프로토콜 
			System.out.print("닉 네임>>");
			String nickname = sc.nextLine();
			pw.println("join:"+nickname);
			pw.flush();
			new ChatClientThread(socket,br).start();
			
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(">>");
				String input = sc.nextLine();
				if("quit".equals(input)) {
					pw.println("quit:");
					break;
				}else {
					pw.println("message:"+input);
					continue;
				}
			}
		}catch(IOException e) {
			log("error : "+e);
		}finally {
			try {
				if(socket!=null&&socket.isClosed()==false)
					socket.close();
				if(sc!=null)
					sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void log(String log) {
		System.out.println("[Client] "+log);
	}

}
