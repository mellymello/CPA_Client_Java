package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;

public class Communication {

	/*
	 * private void sendGet(String url, ) throws Exception {
	 * 
	 * String url = "http://www.google.com/search?q=mkyong";
	 * 
	 * URL obj = new URL(url); HttpURLConnection con = (HttpURLConnection)
	 * obj.openConnection();
	 * 
	 * // optional default is GET con.setRequestMethod("GET");
	 * 
	 * //add request header con.setRequestProperty("User-Agent", USER_AGENT);
	 * 
	 * int responseCode = con.getResponseCode();
	 * System.out.println("\nSending 'GET' request to URL : " + url);
	 * System.out.println("Response Code : " + responseCode);
	 * 
	 * BufferedReader in = new BufferedReader( new
	 * InputStreamReader(con.getInputStream())); String inputLine; StringBuffer
	 * response = new StringBuffer();
	 * 
	 * while ((inputLine = in.readLine()) != null) { response.append(inputLine);
	 * } in.close();
	 * 
	 * //print result System.out.println(response.toString());
	 * 
	 * }
	 */

	// HTTP POST request
	public static ServerResponse sendPost(String url, HashMap<String, String> body,
			String accessToken) throws Exception {

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "JavaUserAgent");
		con.setRequestProperty("Content-Type", "application/json");
		if (accessToken != null) {
			con.setRequestProperty("Authorization", "Bearer " + accessToken);
		}
		
		// body json parsing
		JsonObject jsonObject = new JsonObject();
		for (String key : body.keySet()) {
			jsonObject.addProperty(key, body.get(key));
		}

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObject.toString());
		wr.flush();
		wr.close();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + jsonObject.toString());
		
		
		// getting server response
		ServerResponse response = new ServerResponse();
		
		int responseCode = con.getResponseCode();
		response.setCode(responseCode);
		

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer responseBody = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseBody.append(inputLine);
		}
		in.close();
		
		response.setBody(responseBody.toString());
		
		return response;

	}
}
