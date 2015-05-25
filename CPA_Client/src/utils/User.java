package utils;

public class User {

	private String name;
	private Token token;
	
	public User(){
		name=null;
		token=null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "User [Name=" + name + ", token=" + token + "]";
	}
	
	
}
