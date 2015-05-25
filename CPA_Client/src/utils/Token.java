package utils;

public class Token {

	private String accessToken;
	private String tokenType;
	private int expiresIn;
	private String domain;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@Override
	public String toString() {
		return "Token [token=" + accessToken + ", tokenType=" + tokenType
				+ ", expiresIn=" + expiresIn + ", domain=" + domain + "]";
	}

	
	
}
