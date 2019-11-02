
public class Name {
	private String first;
	private String last;
	
	public Name() {
		last = "";
		first = "";
	}
	
	public Name(String tokens, String tokens2) {
		first = tokens;
		last = tokens2;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

}
