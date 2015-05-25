package cpa;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.Client;
import utils.Communication;
import utils.ServerError;
import utils.ServerResponse;

public class DeviceFlow {

	 /**
	   * Register the client with the Authentication Provider
	   *
	   * @see EBU Tech 3366, section 8.1
	   *
	   * @param authProvider Base url of the authorization provider
	   * @param clientName Name of this client
	   * @param softwareId Identifier of the software running on this client
	   * @param softwareVersion Version of the software running on this client
	   * @param done function(err, clientId, clientSecret) {}
	 * @throws Exception 
	   */
	public static Client registerClient(String authProvider, String clientName, String softwareId, String softwareVersion) throws Exception{
		
		Client c = new Client(clientName);
		
		HashMap<String, String> body = new HashMap<>();
		body.put("client_name", clientName);
		body.put("software_id", softwareId);
		body.put("software_version", softwareVersion);
		
		ServerResponse registrationResponse;
		registrationResponse=Communication.sendPost(authProvider+"/"+Endpoints.apRegister, body, null);
		// print result
		System.out.println("Response Code : " + registrationResponse.getCode());
		System.out.println(registrationResponse.getBody());
		
		if(registrationResponse.getCode()>=400){
			throw new ServerError(registrationResponse.getBody());
		}
		//parsing server response
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(registrationResponse.getBody());
		c.setId(jo.get("client_id").getAsString());
		c.setSecret(jo.get("client_secret").getAsString());
		
		return c;
		
	}
}
