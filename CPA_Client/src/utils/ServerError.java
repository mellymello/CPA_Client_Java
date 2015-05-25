package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerError extends Exception {

	private String error;
	private String errorDescription;
	
	public ServerError(String message){
		super(message);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(message);
		error = jo.get("error").getAsString();
		errorDescription=jo.get("error_description").getAsString();
		
		
	}
	
	public String toString(){
		return "Server returned: " + error +"\nDescription: "+errorDescription;
		
	}
}
