package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	public static void main(String[] args) {
		try {
			InetAddress inetAddress=InetAddress.getLocalHost();//ip주소와 관련된 정보들이 들어있는 클래스
			String hostname=inetAddress.getHostName();
			String hostAddress=inetAddress.getHostAddress();//4바이트를 string으로 받아온다.
			byte[] ipAddress=inetAddress.getAddress();
			
			System.out.println(hostname);
			System.out.println(hostAddress);
			for(byte address : ipAddress)
				System.out.print((address & 0x000000ff)+".");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
