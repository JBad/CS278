import java.net.InetAddress;

public class Client {
	InetAddress address;
	String userName;

	public Client(InetAddress newAddr, String newUserName) {
		address = newAddr;
		userName = newUserName;
	}
	
	public InetAddress getAddr() {
		return address;
	}

	public String getName() {
		return userName;
	}
}
