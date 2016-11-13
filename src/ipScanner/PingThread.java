package ipScanner;

/*
 * Implements thread 
 * Stores found IPs' in the AvailableIpAddresses arraylist
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingThread extends Thread {
	
	public void run(){
		
		try{
			while(!IpContainers.ipAddresses.isEmpty()){
				String ipInstance = IpContainers.ipAddresses.element(); //Fetch a queue element
				String command = "ping -c 3 "+ipInstance; //Create command
				String output = executeCommand(command); //Call execute method ( decleared below in this class)
				
				//Checks if the ping response is a success
				if(output.contains("Request timeout for icmp_seq 0")){
					System.out.println(ipInstance+" : Is not reachable :("); //Its not needed actually
				}//If the IP is not already in the list
				else if(!IpContainers.availableIpAddresses.contains(ipInstance)){ 
					IpContainers.availableIpAddresses.add(ipInstance); //add Ip in the list
					System.out.println(ipInstance+" : Is found yeee..");
				}else{
					//Do nothing
				}
				//remove the element from the queue to travarse the next element
				IpContainers.ipAddresses.remove();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Executes command for ping implementation
	private static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
	
}
