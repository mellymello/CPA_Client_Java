package cpa;

import java.util.Date;

import utils.AssociationInfo;
import utils.Client;
import utils.ServerError;
import utils.Token;
import utils.User;

public class Console_app {

	public static void main(String[] args) {
		System.out.println("CPA client started  !");

		String authProvider = "https://auth-cpa.ebu.io";
		String domain = "bbc1-cpa.ebu.io";
		String station = "myBBC_station";
		Date date = new Date();
		String currentTime;
		try {

			// register the client
			Client javaClient = DeviceFlow.registerClient(authProvider,	"JavaClient", "cpa_java", "1.0");
			
			System.out.println("*****\nRegistered client:" + javaClient	+ "\n*****");
			
			// ask for the client token
			Token javaClientToken = DeviceFlow.requestClientAccessToken(authProvider, javaClient.getId(), javaClient.getSecret(),domain);
			javaClient.setToken(javaClientToken);
			
			System.out.println("*****\nThe client token:" + javaClientToken + "\n*****");
			
			 //just to test we tag the station:
			 currentTime=Long.toString((date.getTime()/1000)); // as the js demo client is doing this we follow the same method
			 DeviceFlow.tagRadioClientMode(domain, station,currentTime , javaClient.getToken().getAccessToken());

			
			// try to associate a user:
			Client tmpClient =  DeviceFlow.registerClient(authProvider,	"JavaUser", "cpa_java", "1.0");
			System.out.println("*****\nRegistered user:" + tmpClient	+ "\n*****");
			
			AssociationInfo javaUserInfo = DeviceFlow.requestUserCode(authProvider, tmpClient.getId(), tmpClient.getSecret(),	domain);
			System.out.println("*****\nAssociating user:" + javaUserInfo+"*****\n");

			//print to the user where to go in order to authenticate his person and the code to provide
			System.out.println("*****\nTo associate your device please visit: "	+ javaUserInfo.getVerificationURI());
			System.out.println("and when asked type in the following code: "+ javaUserInfo.getUserCode() + "\n*****");
			
			// polls on /token with the device_code with given interval
			User javaUser = new User();
			do {
				
				try{
				javaUser = DeviceFlow.requestUserAccessToken(authProvider,tmpClient.getId(), tmpClient.getSecret(),javaUserInfo.getDeviceCode(), domain);
				//here pause the interval time
				Thread.sleep(javaUserInfo.getInterval()*1000);
				
				}catch(ServerError se){
					//in case the server is telling us to slow down the polls on /token
					if(se.getError().compareToIgnoreCase("slow_down")==0){
						javaUserInfo.setInterval(Integer.parseInt(se.getErrorDescription()));
					}
					else{
						throw se;
					}
				}

			} while (javaUser.getToken() == null && javaUser.getName() == null);
			
			System.out.println("*****\nUser auth done:" + javaUser+"\n*****");
			//now we can post on the resource as an authenticated user
			

			
			
		} catch (ServerError se) {

			System.out.println(se.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("CPA done ;-)");
	}
}
