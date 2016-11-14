package ipScanner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 * Lists all IPs in the network
 * creates queue from a specific IP start point
 */

public class IpContainers {
	public static Queue<String> ipAddresses = new LinkedList<String>();
	public static ArrayList<String> availableIpAddresses = new ArrayList<String>();
	public static String startIp = "";
        public static Table table;
	
	//convert integerAddresses to String IP address to form an arraylist
	//return string
		private static String convertAddressInteger(long addressInteger){
			Stack<Long> stack = new Stack<>();
			String addressString="";
			
			for(int i= 0;i<4;i++)
		    {
		    	stack.push(addressInteger & 255);
		    	
		    	addressInteger = addressInteger>>8;
		    }
		    
		    while(!stack.isEmpty())
		    {
		    	addressString+=stack.pop();
		    	if(!stack.isEmpty())
		    	{
		    		addressString+=".";
		    	}
		    }
		    
			return addressString;
		}
		
		/*
		 * makes Ip list in the queue
		 * calls the converter method
		 * return queue
		 */

		private static Queue<String> makeArrayList(long networkAddressInteger, long broadcastAddressInteger){
			Queue<String> al=new LinkedList<String>();
                        if(startIp.equals("")){
                            startIp = convertAddressInteger(networkAddressInteger+2);
                        }
			int a = 0;
			for(networkAddressInteger= networkAddressInteger+2;
					networkAddressInteger<broadcastAddressInteger;networkAddressInteger++){
				String obj = convertAddressInteger(networkAddressInteger);
				
				/*
				 * generates IP list from a start point
				 * there should be some input process to get the startIp address 
				 */
				//startIp = "192.168.0.100";
				if(!obj.equals(startIp) && a==0){
					continue;
				}else{
					a=1;
					al.add(obj);
				}
				
			}
			
			return al;
		}
		
		/*
		 * Initializes list generate process
		 * return void
		 * receive call from main method
		 */
		public static void callArraylistMaker(long networkAddressInteger, long broadcastAddressInteger){
			ipAddresses = makeArrayList(networkAddressInteger, broadcastAddressInteger);
		}
		

}
