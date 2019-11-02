
public class Depositor {
	private String SSN;
	private Name myName;

	public Depositor() {
		SSN = "";
		myName = new Name();
	}

	public Depositor(String string, Name myName2) {
		SSN = string;
		myName = myName2;
	}

	public Name getMyName() {
		return myName;
	}

	public void setMyName(Name myName) {
		this.myName = myName;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}
}
