package chat;

import java.net.Socket;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	public ChatClientThread(Socket socket) {
		socket = this.socket;
	}
	@Override 
	public void run() {
		System.out.print(">>");
	}
}
