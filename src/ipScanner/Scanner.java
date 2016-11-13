package ipScanner;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.Enumeration;


public class Scanner {
	
	//starts threads
	private static void startThread(){
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	    new PingThread().start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ip=null;
		int prefix=0; //Subnet mask prefix
	    try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp())
	                continue;

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                if(iface.getDisplayName().equals("en0")){
	                	//This is the IP address of the machine
	                	ip = addr.getHostAddress();
	                	NetworkInterface interface1 = NetworkInterface.getByInetAddress(addr);
	                	for(InterfaceAddress address: interface1.getInterfaceAddresses())
	                	{
	                		prefix=address.getNetworkPrefixLength();
	                	}
	                	
	                }
	                }
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
	    /*
	     * Split the IP string
	     * make the address capable of logical and operation
	     */
	    String[] ipSplits = ip.split("\\.");
	    long netAddressInteger =0;
	    for(int i=0;i<4;i++)
	    {
	    	int number = Integer.parseInt(ipSplits[i]);
	    	netAddressInteger= netAddressInteger<<8;
	    	netAddressInteger+=number;
	    	
	    }
	    
	    /*
	     * create subnet mask
	     */
	    long subnetMaskNumber=0;
	    for(int count=0;count<prefix;count++){
	    	subnetMaskNumber = subnetMaskNumber << 1;
	    	subnetMaskNumber = subnetMaskNumber+1;
	    }
	    long hostBit = 32-prefix;
	    subnetMaskNumber = subnetMaskNumber << hostBit;
	    //create network and broadcast address
	    long networkAddressInteger = netAddressInteger & subnetMaskNumber;
	    long broadcastAddressInteger = networkAddressInteger + (int)Math.pow(2, hostBit) - 1;
	   
	    //Generate the IP list
	    IpContainers.callArraylistMaker(networkAddressInteger, broadcastAddressInteger);
	    //launch threads
	    startThread();
	    
	}
	


}
