package cpa;

import utils.AssociationInfo;
import utils.Client;
import utils.ServerError;

public class GUI_app {

	public static void main(String[] args) {
		System.out.println("CPA client started  !");
		
		String authProvider = "http://auth-cpa.ebu.io";
		String domain = "bbc1-cpa.ebu.io";
		try {
			
			Client javaClient = DeviceFlow.registerClient(authProvider, "JavaClient", "cpa_java", "1.0");
			System.out.println("Registered client:"+ javaClient);
			
			//try to associate a client:
			AssociationInfo javaUserInfo=DeviceFlow.requestUserCode(authProvider, javaClient.getId(), javaClient.getSecret(), domain);
			System.out.println("Associating user:"+ javaUserInfo);
			
			System.out.println("*****\nTo associate your device please visit: "+javaUserInfo.getVerificationURI());
			System.out.println("and when asked type in the following code: "+ javaUserInfo.getUserCode() +"\n*****");
			
			
		}catch(ServerError se){

			System.out.println(se.toString());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("CPA done ;-)");
	}
}
