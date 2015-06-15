package cpa;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.AssociationInfo;
import utils.Client;
import utils.Communication;
import utils.ServerError;
import utils.ServerResponse;
import utils.Token;
import utils.User;

public class DeviceFlow {

	/**
	 * Register the client with the Authentication Provider
	 *
	 * @see EBU Tech 3366, section 8.1
	 *
	 * @param authProvider
	 *            Base url of the authorization provider
	 * @param clientName
	 *            Name of this client
	 * @param softwareId
	 *            Identifier of the software running on this client
	 * @param softwareVersion
	 *            Version of the software running on this client
	 * @throws Exception
	 */
	public static Client registerClient(String authProvider, String clientName,
			String softwareId, String softwareVersion) throws Exception {

		Client c = new Client(clientName);

		HashMap<String, String> body = new HashMap<>();
		body.put("client_name", clientName);
		body.put("software_id", softwareId);
		body.put("software_version", softwareVersion);

		ServerResponse registrationResponse;
		registrationResponse = Communication.sendPost(authProvider + "/"
				+ Endpoints.apRegister, body, null);
		// print result
		System.out.println("Response Code : " + registrationResponse.getCode());
		System.out.println(registrationResponse.getBody());

		if (registrationResponse.getCode() >= 400) {
			throw new ServerError(registrationResponse.getBody());
		}
		// parsing server response
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(registrationResponse
				.getBody());
		c.setId(jo.get("client_id").getAsString());
		c.setSecret(jo.get("client_secret").getAsString());

		return c;

	}

	/**
	 * Request a token for this client (Client Mode)
	 *
	 * @see EBU Tech 3366, section 8.3.1.1
	 *
	 * @param authProvider
	 *            Base url of the authorization provider
	 * @param clientId
	 *            Id of this client
	 * @param clientSecret
	 *            Secret of this client
	 * @param domain
	 *            Domain of the requested token
	 * @throws Exception
	 */
	public static Token requestClientAccessToken(String authProvider,
			String clientId, String clientSecret, String domain)
			throws Exception {

		Token t = new Token();

		HashMap<String, String> body = new HashMap<>();
		body.put("grant_type", "http://tech.ebu.ch/cpa/1.0/client_credentials");
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("domain", domain);

		ServerResponse tokenResponse;
		tokenResponse = Communication.sendPost(authProvider + "/"
				+ Endpoints.apToken, body, null);
		// print result
		System.out.println("Response Code : " + tokenResponse.getCode());
		System.out.println(tokenResponse.getBody());

		if (tokenResponse.getCode() >= 400) {
			throw new ServerError(tokenResponse.getBody());
		}
		// parsing server response

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse.getBody());
		t.setAccessToken(jo.get("access_token").getAsString());
		t.setTokenType(jo.get("token_type").getAsString());
		t.setExpiresIn(jo.get("expires_in").getAsInt());
		t.setDomain(jo.get("domain").getAsString());

		return t;
	}

	public static void tagRadioClientMode(String domain, String station,
			String time, String clientToken) throws Exception {

		HashMap<String, String> body = new HashMap<>();

		body.put("station", station);
		body.put("time", time);

		ServerResponse tagClientResponse;
		tagClientResponse = Communication.sendPost("https://" + domain + "/tag", body, clientToken);
		// print result
		System.out.println("Response Code : " + tagClientResponse.getCode());
		System.out.println(tagClientResponse.getBody());

		if (tagClientResponse.getCode() >= 400) {
			throw new ServerError(tagClientResponse.getBody());
		}

	}

	/**
	 * Request a user code
	 *
	 * @see EBU Tech 3366, section 8.2
	 *
	 * @param authProvider
	 *            Base url of the authorization provider
	 * @param clientId
	 *            Id of this client
	 * @param clientSecret
	 *            Secret of this client
	 * @param domain
	 *            Domain of the token for which the client is requesting an
	 *            association
	 * @throws Exception
	 */
	public static AssociationInfo requestUserCode(String authProvider,
			String clientId, String clientSecret, String domain)
			throws Exception {

		AssociationInfo ai = new AssociationInfo();

		HashMap<String, String> body = new HashMap<>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("domain", domain);

		ServerResponse userCodeResponse;
		userCodeResponse = Communication.sendPost(authProvider + "/"
				+ Endpoints.apAssociate, body, null);
		// print result
		System.out.println("Response Code : " + userCodeResponse.getCode());
		System.out.println(userCodeResponse.getBody());

		if (userCodeResponse.getCode() >= 400) {
			throw new ServerError(userCodeResponse.getBody());
		}
		// parsing server response
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(userCodeResponse
				.getBody());

		// Here we have to test for every possible response:
		// see EBU Tech 3366, section 8.2.2

		if (jo.get("device_code") != null) {
			ai.setDeviceCode(jo.get("device_code").getAsString());
		}
		if (jo.get("user_code") != null) {
			ai.setUserCode(jo.get("user_code").getAsString());
		}

		if (jo.get("verification_uri") != null) {
			ai.setVerificationURI(jo.get("verification_uri").getAsString());
		}

		if (jo.get("interval") != null) {
			ai.setInterval(jo.get("interval").getAsInt());
		}

		if (jo.get("expires_in") != null) {
			ai.setExpiresIn(jo.get("expires_in").getAsInt());
		}

		return ai;

	}

	/**
	 * Request a token for the user associated with this device. The association
	 * is represented by the device_code (User Mode)
	 *
	 * @see EBU Tech 3366, section 8.3.1.2
	 *
	 * @param authProvider
	 *            Base url of the authorization provider
	 * @param clientId
	 *            Id of this client
	 * @param clientSecret
	 *            Secret of this client
	 * @param deviceCode
	 *            Code returned by the authorization provider in order to check
	 *            if the user_code has been validated
	 * @param domain
	 *            Domain of the requested token
	 * @throws Exception
	 */
	public static User requestUserAccessToken(String authProvider,
			String clientId, String clientSecret, String deviceCode,
			String domain) throws Exception {
		User u = new User();

		HashMap<String, String> body = new HashMap<>();
		body.put("grant_type", "http://tech.ebu.ch/cpa/1.0/device_code");
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("device_code", deviceCode);
		body.put("domain", domain);

		ServerResponse tokenResponse;
		tokenResponse = Communication.sendPost(authProvider + "/"
				+ Endpoints.apToken, body, null);
		// print result
		System.out.println("Response Code : " + tokenResponse.getCode());
		System.out.println(tokenResponse.getBody());

		if (tokenResponse.getCode() >= 400) {
			throw new ServerError(tokenResponse.getBody());
		} else if (tokenResponse.getCode() == 202) {
			u.setName(null);
			u.setToken(null);

		} else if (tokenResponse.getCode() == 200) {
			// parsing server response
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse
					.getBody());

			u.setName(jo.get("user_name").getAsString());
			Token t = new Token();
			t.setAccessToken(jo.get("access_token").getAsString());
			t.setTokenType(jo.get("token_type").getAsString());
			t.setExpiresIn(jo.get("expires_in").getAsInt());
			t.setDomain(jo.get("domain").getAsString());
			u.setToken(t);
		}

		return u;
	}
}
