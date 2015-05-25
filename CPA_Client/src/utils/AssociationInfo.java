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
	@Override
	public String toString() {
		return "AssociationInfo [deviceCode=" + deviceCode + ", userCode="
				+ userCode + ", verificationURI=" + verificationURI
				+ ", interval=" + interval + ", expiresIn=" + expiresIn + "]";
	}
	

}
