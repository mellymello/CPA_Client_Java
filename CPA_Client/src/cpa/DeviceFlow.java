package cpa;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.AssociationInfo;
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
	
	/**
	   * Request a user code
	   *
	   * @see EBU Tech 3366, section 8.2
	   *
	   * @param authProvider Base url of the authorization provider
	   * @param clientId Id of this client
	   * @param clientSecret Secret of this client
	   * @param domain Domain of the token for which the client is requesting an association
	   * @param done Callback done(err)
	 * @throws Exception 
	   */
	public static AssociationInfo requestUserCode(String authProvider, String clientId, String clientSecret, String domain) throws Exception{
		
		AssociationInfo ai = new AssociationInfo();
		
		HashMap<String, String> body = new HashMap<>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("domain", domain);
		
		ServerResponse userCodeResponse;
		userCodeResponse=Communication.sendPost(authProvider+"/"+Endpoints.apAssociate, body, null);
		// print result
		System.out.println("Response Code : " + userCodeResponse.getCode());
		System.out.println(userCodeResponse.getBody());
		
		if(userCodeResponse.getCode()>=400){
			throw new ServerError(userCodeResponse.getBody());
		}
		//parsing server response
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(userCodeResponse.getBody());
		
		ai.setDeviceCode(jo.get("device_code").getAsString());
		ai.setUserCode(jo.get("user_code").getAsString());
		ai.setVerificationURI(jo.get("verification_uri").getAsString());
		ai.setInterval(jo.get("interval").getAsInt());
		ai.setExpiresIn(jo.get("expires_in").getAsInt());
		
		return ai;
		
		
	}
}
