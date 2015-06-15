package utils;

public class Client {

	private String name;
	private String id;
	private String secret;
	private Token token;
	
	
	
	public Client(String name) {
		this.name = name;
	}
	
	public Client(){
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	


	@Override
	public String toString() {
		return "Client [name=" + name + ", id=" + id + ", secret=" + secret
				+ ", token=" + token + "]";
	}


	public Token getToken() {
		return token;
	}


	public void setToken(Token token) {
		this.token = token;
	}
	
	
}
