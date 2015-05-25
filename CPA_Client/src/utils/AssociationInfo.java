package utils;

public class AssociationInfo {

	private String deviceCode;
	private String userCode;
	private String verificationURI;
	private int interval;
	private int expiresIn ;
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getVerificationURI() {
		return verificationURI;
	}
	public void setVerificationURI(String verificationURI) {
		this.verificationURI = verificationURI;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
public String toString(){
		return "AssocInfos [device_code,user_code,verification_uri,interval,expires_in]: [" +deviceCode+","+userCode+","+verificationURI+","+interval+","+expiresIn+"]"; 
	}
}
