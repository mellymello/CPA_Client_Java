package cpa;

import utils.AssociationInfo;
import utils.Client;
import utils.ServerError;
import utils.Token;

public class GUI_app {

	public static void main(String[] args) {
		System.out.println("CPA client started  !");
		
		String authProvider = "http://auth-cpa.ebu.io";
		String domain = "bbc1-cpa.ebu.io";
		try {
			
			//register the client
			Client javaClient = DeviceFlow.registerClient(authProvider, "JavaClient", "cpa_java", "1.0");
			System.out.println("Registered client:"+ javaClient);
			//ask for the client token
			Token javaClientToken = DeviceFlow.requestClientAccessToken(authProvider, javaClient.getId(), javaClient.getSecret(), domain);
			javaClient.setToken(javaClientToken);
			System.out.println("*****\nThe client token:"+javaClientToken);
			
			
			/*
			
			//try to associate a user:
			AssociationInfo javaUserInfo=DeviceFlow.requestUserCode(authProvider, javaClient.getId(), javaClient.getSecret(), domain);
			System.out.println("Associating user:"+ javaUserInfo);
			
			//here we have to adapt the client reaction... see spec 8.2.2 
			//as the server may respond in differents way
			
			//case 8.2.2.1
			System.out.println("*****\nTo associate your device please visit: "+javaUserInfo.getVerificationURI());
			System.out.println("and when asked type in the following code: "+ javaUserInfo.getUserCode() +"\n*****");
			// polls on /token with the device_code with given interval
			
			*/
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
