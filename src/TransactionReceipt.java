import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionReceipt {
	private TransactionTicket Slip;
	private boolean successIndicatorFlag;
	private String reasonForFailureString;
	private double preTransactionBalance;
	private double postTransactionBalance;
	private Calendar postTransactionMaturityDate;
	
	public TransactionReceipt() {
		Slip = new TransactionTicket();
		successIndicatorFlag = false;
		reasonForFailureString = "";
		preTransactionBalance = 0;
		postTransactionBalance = 0;
	}
	//used to deposit or withdraw
	public TransactionReceipt(TransactionTicket info,boolean flag,double pre,double post) {
		Slip = info;
		successIndicatorFlag = flag;
		preTransactionBalance = pre;
		postTransactionBalance = post;
	}
	//Constructor for flag and reason for faliure
	public TransactionReceipt(boolean flag,String reason) {
			successIndicatorFlag = flag;
			reasonForFailureString = reason;
	}
	//Constructor sets TransactionTicket,successIndicatorFlag and pre and post balance
	//Used for getbalance
	public TransactionReceipt(TransactionTicket info,boolean flag,double balance) {
		Slip = info;
		successIndicatorFlag = flag;
		preTransactionBalance = balance;
		postTransactionBalance = balance;
	}
	
	public TransactionReceipt(TransactionTicket info,boolean flag,String reason) {
		Slip = info;
		successIndicatorFlag = flag;
		reasonForFailureString = reason;
	}
	
	public TransactionReceipt(TransactionTicket info,boolean flag,String reason,double current) {
		Slip = info;
		successIndicatorFlag = flag;
		reasonForFailureString = reason;
		preTransactionBalance = current;
	}

	public TransactionReceipt(TransactionTicket info, boolean flag,String reason,double preBal,double balFee){
		Slip = info;
		successIndicatorFlag = flag;
		reasonForFailureString = reason;
		preTransactionBalance = preBal;
		postTransactionBalance = balFee;
	}
	
	public TransactionReceipt(TransactionTicket info, boolean flag,String reason,String date) throws ParseException {
		Slip = info;
		successIndicatorFlag = flag;
		reasonForFailureString = reason;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date oDate = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);
		postTransactionMaturityDate = cal;
	}
	
	public TransactionTicket getTransactionTicket() {
		return Slip;
	}
	
	public boolean getTransactionSuccessIndicatorFlag() {
		return successIndicatorFlag;
	}
	
	public String getTransactionFailureReason() {
		return reasonForFailureString;
	}
	
	public double getPreTransactionBalance() {
		return preTransactionBalance;
	}
	
	public double getPostTransactionBalance() {
		return postTransactionBalance;
	}
	
	public Calendar getPostTransactionMaturityDate() {
		return postTransactionMaturityDate;
	}
}
