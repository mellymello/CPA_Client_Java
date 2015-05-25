package cpa;

import utils.Client;

public class GUI_app {

	public static void main(String[] args) {
		System.out.println("CPA client started  !");
		
		try {
			
			Client javaClient = DeviceFlow.registerClient("http://auth-cpa.ebu.io", "JavaClient", "cpa_java", "1.0");
			System.out.println("Registered client:"+ javaClient);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("CPA done ;-)");
	}
}
