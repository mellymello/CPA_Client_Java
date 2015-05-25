package cpa;

import java.util.Date;

import utils.AssociationInfo;
import utils.Client;
import utils.ServerError;
import utils.Token;
import utils.User;

public class GUI_app {

	public static void main(String[] args) {
		System.out.println("CPA client started  !");

		String authProvider = "http://auth-cpa.ebu.io";
		String domain = "bbc1-cpa.ebu.io";
		String station = "myBBC_station";
		Date date = new Date();
		String currentTime;
		try {

			// register the client
			Client javaClient = DeviceFlow.registerClient(authProvider,
					"JavaClient", "cpa_java", "1.0");
			System.out.println("*****\nRegistered client:" + javaClient
					+ "\n*****");
			// ask for the client token
			Token javaClientToken = DeviceFlow.requestClientAccessToken(
					authProvider, javaClient.getId(), javaClient.getSecret(),
					domain);
			javaClient.setToken(javaClientToken);
			System.out.println("*****\nThe client token:" + javaClientToken
					+ "\n*****");
			// //just to test we tag the station:
			// currentTime=Long.toString((date.getTime()/1000)); // as the js
			// demo client is doing this we follow the same method
			// DeviceFlow.tagRadioClientMode(domain, station,currentTime ,
			// javaClient.getToken().getAccessToken());

			// try to associate a user:
			AssociationInfo javaUserInfo = DeviceFlow.requestUserCode(
					authProvider, javaClient.getId(), javaClient.getSecret(),
					domain);
			System.out.println("*****\nAssociating user:" + javaUserInfo+"*****\n");

			// here we have to adapt the client reaction... see spec 8.2.2
			// as the server may respond in differents way
			// TO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO

			// case 8.2.2.1
			System.out.println("*****\nTo associate your device please visit: "
					+ javaUserInfo.getVerificationURI());
			System.out.println("and when asked type in the following code: "
					+ javaUserInfo.getUserCode() + "\n*****");
			// polls on /token with the device_code with given interval
			User javaUser;
			do {

				javaUser = DeviceFlow.requestUserAccessToken(authProvider,
						javaClient.getId(), javaClient.getSecret(),
						javaUserInfo.getDeviceCode(), domain);
				//here pause the interval time and catch specific error exception like slowdown...
				//TO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
				
				

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
