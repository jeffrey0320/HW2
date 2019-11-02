import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Check {
	private int accNum;
	private double checkAmount;
	private String dateOfCheck;
	private Calendar calFormat;
	
	public Check() {
		accNum = 0;
		checkAmount = 0;
		dateOfCheck = "";	
	}
	
	public Check(int acc,double amount,String date) throws ParseException {
		accNum = acc;
		checkAmount = amount;
		dateOfCheck = date;
		calFormat = newDate(date);
	}
	
	public Calendar newDate(String ate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = sdf.parse(ate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public int getAccNum() {
		return accNum;
	}
	
	public double getAmount() {
		return checkAmount;
	}
	
	public Calendar getDate() {
		return calFormat;
	}
}
