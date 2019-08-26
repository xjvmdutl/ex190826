package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private static final int PORT = 5000;//포트는 전역으로 관리
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		try {
			//1.서버소켓 생성
			serverSocket = new ServerSocket();
			InetAddress inetAddress=InetAddress.getLocalHost();
			String localhostAddress=inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress=new InetSocketAddress(localhostAddress,PORT);
			
			//1-1 Time-wait상태에서 서버 소켓을 즉시 사용하기 위해
			serverSocket.setReuseAddress(true);
			
			//2.Binding : Socket에 SocketAddress(IPAddress+port)을 연결
			serverSocket.bind(inetSocketAddress);
			System.out.println("[TCPServer] binding "+inetAddress.getHostAddress()+":"+PORT);
			//3.accept:
			
			// 클라이언트로 부터 연결요청(Connect)을 기다린다.
			Socket socket=serverSocket.accept();//blocking
			InetSocketAddress inetRemoteSocketAddress =(InetSocketAddress)socket.getRemoteSocketAddress();//다운 케스팅 필요
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort=inetRemoteSocketAddress.getPort();
			System.out.println("[TCPServer] Connected from client["+remoteHostAddress+":"+remoteHostPort+"]");
			//remoteAddress:연결될 client을 주소
			
			//4. IOStream 받아오기 
			try {
				InputStream is =socket.getInputStream();
				OutputStream os=socket.getOutputStream();
				while(true) {//서버소켓 IOException을 여기서 처리
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);//5.데이터 읽기, 읽은 만큼 데이터 길이 만큼 읽어 온다.
					if(readByteCount == -1) {
						//정상종료 : remoteSocket이 close()메소드를 통해서 정상적으로 소켓을 닫는 경우
						System.out.println("[TCPServer] closed by client");
						break;
					}
					String data = new String(buffer,0,readByteCount,"UTF-8");//직접 인코딩한 경우
					System.out.println("[TCPServer] received : "+data);//telnet개행을 하나 더 보내줘서 하나 더 받아준다.(신경 쓰지 않아도됨)
					//6.데이터 보내기
					try {
						Thread.sleep(1000);
						os.write(data.getBytes("UTF-8"));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(SocketException e){//소켓이 비정상적으로 종료가 되었을때
				System.out.println("[TCPServer] abnormal closed by client");
			}catch(IOException e) {
				e.printStackTrace();
			}finally {//7.socket자원 정리
				if(socket!=null && socket.isClosed()==false)
					socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//8. 서버 소켓 자원 정리
			try {
				if(serverSocket!=null && serverSocket.isClosed()==false)//서버 소켓이 이미 닫혀져 있을 수도 있기 떄문에 한번 더 물어주는 코드 필요
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
