import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Account {
	private int accNum;
	private String accType;
	private double balance;
	private Depositor myDep;
	private Calendar maturityDate;
	
	public Account() {
		accNum = 0;
		accType = "";
		balance = 0;
		myDep = new Depositor();
	}

	public Account(Calendar maturityDate){
	    this.maturityDate = maturityDate;
    }
	
	public Account(int parseInt, String string, double parseDouble, Depositor myInfo) {
		accNum = parseInt;
		accType = string;
		balance = parseDouble;
		myDep = myInfo;
	}

	public Account (String f, String l, String s, String t,int num){
		accType = t;
		myDep = new Depositor(s,f,l);
		accNum = num;
	}

	public Account(double balance){
		this.balance = balance;
	}
	
	public TransactionReceipt getBalance(TransactionTicket info,Bank acc,Account bal,int index,boolean flag) {
		
		if(flag) {
			bal = acc.getAccts(index);
			balance =  bal.getBalance();
			bal.setBalance(balance);
            return new TransactionReceipt(info,true,balance);
		}else {
			String reason = "Account number not found.";
            return new TransactionReceipt(info,false,reason);
		}
	}
	
	public TransactionReceipt makeDeposit(TransactionTicket info,Bank acc,int index) {
		TransactionReceipt newRec = new TransactionReceipt();

		if(info.getTransactionAmount() <= 0.00){
			String reason = "Trying to deposit an invalid amount";
			newRec = new TransactionReceipt(info,false,reason);
			return  newRec;
		}else{
			Account accInfo = acc.getAccts(index);
			double balance = accInfo.getBalance();
			double newBalance = balance + info.getTransactionAmount();
			newRec = new TransactionReceipt(info,true,balance,newBalance);
			accInfo.setBalance(newBalance);
			return newRec;
		}
	}

	public TransactionReceipt makeDepositCD(TransactionTicket info,Bank acc,int index,String matDate) throws ParseException {
		TransactionReceipt cdRec = new TransactionReceipt();
		Calendar timeNow = Calendar.getInstance();
		Calendar newDate = Calendar.getInstance();
		Account accInfo = new Account();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date oDate = sdf.parse(matDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		if(cal.before(timeNow) || cal.equals(timeNow)){
			if(info.getTransactionAmount() <= 0.00){
				String reason = "Invalid amount.";
				cdRec = new TransactionReceipt(info,false,reason);
				return  cdRec;
			}else{
				accInfo = acc.getAccts(index);
				double balance = accInfo.getBalance();
				double newBalance = balance + info.getTransactionAmount();
				newDate.add(Calendar.MONTH,info.getTermOfCD());
				cdRec = new TransactionReceipt(info,true,balance,newBalance,newDate);
				accInfo.setBalance(newBalance);
				return cdRec;
			}
		}else{
			String reason = "Term has not ended.";
			cdRec = new TransactionReceipt(info,false,reason);
			return cdRec;
		}
	}
	
	public TransactionReceipt makeWithdrawal(TransactionTicket info, Bank acc, int index)
	{
		TransactionReceipt newRec = new TransactionReceipt();
		Account bal = acc.getAccts(index);
		double balance = bal.getBalance();

		if(info.getTransactionAmount() <= 0.0) {
			String reason = "Trying to withdraw invalid amount.";
			newRec = new TransactionReceipt(info,false,reason,balance);
			return newRec;

		}
		else if(info.getTransactionAmount() > balance) {
			String reason = "Balance has insufficient funds.";
			newRec = new TransactionReceipt(info,false,reason,balance);
			return newRec;
		}
		else {
			double newBal = balance - info.getTransactionAmount();
			newRec = new TransactionReceipt(info,true,balance,newBal);
			bal.setBalance(newBal);
			return newRec;
		}
	}

	public TransactionReceipt makeWithdrawalCD(TransactionTicket ticket, Bank obj, int index, String openDate) throws ParseException {
		TransactionReceipt cdRec = new TransactionReceipt();
		Calendar timeNow = Calendar.getInstance();
		Calendar newDate = Calendar.getInstance();
		Account accInfo = new Account();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date oDate = sdf.parse(openDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		accInfo = obj.getAccts(index);
		double Balance = accInfo.getBalance();

		if(cal.before(timeNow) || cal.equals(timeNow)){
			if(ticket.getTransactionAmount() <= 0.00){
				String reason = "Invalid amount to deposit.";
				cdRec = new TransactionReceipt(ticket,false,reason);
				return  cdRec;
			}else if(ticket.getTransactionAmount() > balance)
			{
				String reason = "Balance has insufficient funds.";
				cdRec = new TransactionReceipt(ticket,false,reason,balance);
				return cdRec;
			}else{
				double newBalance = Balance - ticket.getTransactionAmount();
				newDate.add(Calendar.MONTH,ticket.getTermOfCD());
				cdRec = new TransactionReceipt(ticket,true,Balance,newBalance,newDate);
				accInfo.setBalance(newBalance);
				return cdRec;
			}
		}else{
			String reason = "Term has not ended.";
			cdRec = new TransactionReceipt(ticket,false,reason);
			return cdRec;
		}
	}
	
	public TransactionReceipt clearCheck(Check checkInfo, TransactionTicket info, Bank acc, Account bal, int index) 
	{
		TransactionReceipt clearedCheck = new TransactionReceipt();
		Calendar timeNow = Calendar.getInstance();
		Calendar checkDate = checkInfo.getDate();
		Calendar expiration = checkDate;
		expiration.add(Calendar.MONTH,6);

		if(timeNow.before(expiration)) {

			double drawAmount = checkInfo.getAmount();
			bal = acc.getAccts(index);
			balance =  bal.getBalance();

			if(drawAmount <= 0.0)
			{
				String reason = "Trying to withdraw invalid amount.";
				clearedCheck = new TransactionReceipt(info,false,reason,balance);
				return clearedCheck;
			}
			else if(drawAmount > balance)
			{
				String reason = "Balance has insufficient funds. You have been charged a $2.50 service fee. ";
				final double fee = 2.50;
				double newBal = balance - fee;
				clearedCheck = new TransactionReceipt(info,false,reason,balance,newBal);
				bal.setBalance(newBal);
				return clearedCheck;
			}
			else
			{
				double newBal = balance - drawAmount;
				clearedCheck = new TransactionReceipt(info,true,balance,newBal);
				bal.setBalance(newBal);
				return clearedCheck;
			}
		}
		else 
		{
			String reason = "The date on the check is more than 6 months ago.";
			clearedCheck = new TransactionReceipt(info,false,reason);
			return clearedCheck;
		}
	}
	
	public TransactionReceipt notChecking(TransactionTicket info,String reason,boolean ToF) {
		return new TransactionReceipt(info,ToF,reason);
	}
	
	public Depositor getMyDep() {
		return myDep;
	}

	private void setMyDep(Depositor myDep) {
		this.myDep = myDep;
	}

	public int getAccNum() {
		return accNum;
	}

	private void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public String getAccType() {
		return accType;
	}

	private void setAccType(String accType) {
		this.accType = accType;
	}
	
	public double getBalance() {
		return balance;
	}

	private void setBalance(double balance) {
		this.balance = balance;
	}

	public Calendar getMaturityDate(){
        return maturityDate;
    }

}
