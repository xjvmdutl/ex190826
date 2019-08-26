package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

//과제
public class UDPTimeServer {

	public static final int PORT = 8000;
	public static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			//1. 소켓 생성
			socket=new DatagramSocket(PORT);
			while(true) {
			//2. 데이터 수신
				DatagramPacket receivePacket=new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);//udp는 버퍼사이즈를 고정히킨다.
				socket.receive(receivePacket);//blocking
				//3.data처리(확인)
				byte[] data=receivePacket.getData();
				int length= receivePacket.getLength();
				if(length==0) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					String date = format.format(new Date());
					System.out.println("[UDP Server Time] "+date);
					byte[] sendData=date.getBytes("UTF-8");
					DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
					socket.send(sendPacket);
					continue;
				}
				String message= new String(data,0,length,"UTF-8");
				System.out.println("[UDP EchoServer] received: "+message);
				//4.data 전송
				byte[] sendData=message.getBytes("UTF-8");
				DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket!=null&&socket.isClosed()==false) 
				socket.close();
		}
	}
}