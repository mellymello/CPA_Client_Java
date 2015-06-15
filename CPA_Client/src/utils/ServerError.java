package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerError extends Exception {

	private static final long serialVersionUID = 219556197148204395L;
	private String error;
	private String errorDescription;

	public ServerError(String message) {
		super(message);

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(message);
		error = jo.get("error").getAsString();
		errorDescription = jo.get("error_description").getAsString();

	}

	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String toString() {
		return "Server returned: " + error + "\nDescription: "
				+ errorDescription;

	}
}
