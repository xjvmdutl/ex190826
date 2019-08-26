package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	private BufferedReader br;
	public ChatClientThread(Socket socket,BufferedReader br) {
		socket = this.socket;
		this.br= br;
	}
	@Override 
	public void run() {
		String message;
		try {
			message = br.readLine();
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
