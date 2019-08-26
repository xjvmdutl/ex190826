package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {

	private static final String  SERVER_IP="192.168.1.2";
	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;
		try {
			//1.키보드 연결
			sc= new Scanner(System.in);
			//2.소켓생성
			socket=new DatagramSocket();//서버가 아니라 포트 필요없다
			while(true) {
				//3.사용자 입력을 받음
				System.out.print(">>");
				String message=sc.nextLine();
				if("quit".equals(message))
					break;
				byte[] sendData;
				if("".equals(message)) {
					sendData=new byte[0];
				}else {
					sendData=message.getBytes();
				}//4메시지 전송
				DatagramPacket sendPacket=new DatagramPacket(
						sendData,sendData.length,
						new InetSocketAddress(SERVER_IP,UDPEchoServer.PORT)
						);
				socket.send(sendPacket);
				DatagramPacket receivePacket=new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],UDPEchoServer.BUFFER_SIZE);//udp는 버퍼사이즈를 고정히킨다.
				socket.receive(receivePacket);//blocking
				byte[] data=receivePacket.getData();
				int length= receivePacket.getLength();
				message= new String(data,0,length,"UTF-8");
				System.out.println("<<: "+message);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(sc != null)
				sc.close();
			if(socket!=null && socket.isClosed()==false)
				socket.close();
		}
	}

}
