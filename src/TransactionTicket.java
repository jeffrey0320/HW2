import java.util.Calendar;
import java.util.Date;

public class TransactionTicket {
	private Calendar dateOfTransaction;
	private String typeOfTransaction;
	private double amountOfTransaction;
	private int termOfCD;
	
	//no args-constructor
	public TransactionTicket() {
		typeOfTransaction = "";
		amountOfTransaction = 0;
		termOfCD = 0;
		dateOfTransaction = Calendar.getInstance();
	}
	//Constructor for withdrawal or deposit transaction
	public TransactionTicket(Calendar date, String type, double amount) {
		dateOfTransaction = date;
		typeOfTransaction = type;
		amountOfTransaction = amount;
		dateOfTransaction = Calendar.getInstance();
	}
	//Constructor to get balance
	public TransactionTicket(Calendar date, String type) {
		dateOfTransaction = date;
		typeOfTransaction = type;
	}
	
	public TransactionTicket(Calendar date, String type, int term) {
		dateOfTransaction = date;
		typeOfTransaction = type;
		termOfCD = term;
	}

	public TransactionTicket(Calendar currentDate, String deposit, double amountToDeposit, int amountOfTerm) {
		dateOfTransaction = currentDate;
		typeOfTransaction = deposit;
		amountOfTransaction = amountToDeposit;
		termOfCD = amountOfTerm;
	}

	public Calendar getDateOfTransaction() {
		return dateOfTransaction;
	}
	
	public Date getDate(Calendar date) {
		return date.getTime();
	}
	
	public String getTransactionType() {
		return typeOfTransaction;
	}
	
	public double getTransactionAmount() {
		return amountOfTransaction;
	}
	
	public int getTermOfCD() {
		return termOfCD;
	}
}
