import java.util.Calendar;
import java.util.Date;

public class Account {
	private int accNum;
	private String accType;
	private double balance;
	private Depositor myDep;
	
	public Account() {
		accNum = 0;
		accType = "";
		balance = 0;
		myDep = new Depositor();
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
			TransactionReceipt receipt = new TransactionReceipt(info,true,balance);
			return receipt;
		}else {
			String reason = "Account number not found.";
			TransactionReceipt receipt = new TransactionReceipt(info,false,reason);
			return receipt;
		}
	}
	
	public TransactionReceipt makeDeposit(TransactionTicket info,Bank acc, Account bal,int index,boolean flag,double depAmount) {
		if(flag && depAmount > 0.0) {
			bal = acc.getAccts(index);
			balance =  bal.getBalance();
			double newBal = balance + depAmount;
			bal.setBalance(newBal);
			TransactionReceipt withDeposit = new TransactionReceipt(info,true,balance,newBal);
			return withDeposit;
		}else {
			String reason = "Cant deposit amount less than 0";
			bal = acc.getAccts(index);
			balance =  bal.getBalance();
			TransactionReceipt noDeposit = new TransactionReceipt(info,false,reason,balance);
			return noDeposit;
		}
	}
	
	public TransactionReceipt makeWithdrawal(TransactionTicket info, Bank acc, Account bal, int index, double drawAmount) 
	{	
		TransactionReceipt printReceipt = new TransactionReceipt();
		
		bal = acc.getAccts(index);
		balance =  bal.getBalance();
		
		if(drawAmount <= 0.0)
		{
			String reason = "Trying to withdraw invalid amount.";
			printReceipt = new TransactionReceipt(info,false,reason,balance);
			return printReceipt;
			
		}
		else if(drawAmount > balance) 
		{
			String reason = "Balance has insufficient funds.";
			printReceipt = new TransactionReceipt(info,false,reason,balance);
			return printReceipt;
		}
		else
		{	
			double newBal = balance - drawAmount;
			bal.setBalance(newBal);
			printReceipt = new TransactionReceipt(info,true,balance,newBal);
			return printReceipt;
		}
	}
	
	public TransactionReceipt clearCheck(Check checkInfo, TransactionTicket info, Bank acc, Account bal, int index) 
	{
		TransactionReceipt clearedCheck = new TransactionReceipt();
		Calendar timeNow = Calendar.getInstance();
		Calendar beforeSixMonths = Calendar.getInstance();
		beforeSixMonths.add(Calendar.MONTH, -6);
		Calendar check = checkInfo.getDate();

		Date currentTimeNow = timeNow.getTime();
		Date sixMonths = beforeSixMonths.getTime();
		Date checkDate = check.getTime();

		if(check.before(beforeSixMonths)) {

			String reason = "The date on the check is more than 6 months ago.";
			clearedCheck = new TransactionReceipt(info,false,reason);
			return clearedCheck;
		}
		else if(check.after(timeNow)){
			String reason = "THe date on the check is after todays date. ";
			System.out.println("Check after today date");
			clearedCheck = new TransactionReceipt(info,false,reason);
			return clearedCheck;
		}
		else 
		{
			bal = acc.getAccts(index);
			balance =  bal.getBalance();
			
			double drawAmount = checkInfo.getAmount();
			System.out.println("enter3");
			
			if(drawAmount <= 0.0)
			{
				System.out.println("enter4");
				String reason = "Trying to withdraw invalid amount.";
				clearedCheck = new TransactionReceipt(info,false,reason,balance);
				return clearedCheck;
				
			}
			else if(drawAmount > balance) 
			{
				System.out.println("enter5");
				String reason = "Balance has insufficient funds.";
				clearedCheck = new TransactionReceipt(info,false,reason,balance);
				return clearedCheck;
			}
			else
			{
				System.out.println("enter6");
				double newBal = balance - drawAmount;
				bal.setBalance(newBal);
				System.out.println("balance " + balance);
				clearedCheck = new TransactionReceipt(info,true,balance,newBal);
				return clearedCheck;
			}
		}
	}
	
	public TransactionReceipt notChecking(TransactionTicket info,String reason,boolean ToF) {
		return new TransactionReceipt(info,ToF,reason);
	}
	
	public Depositor getMyDep() {
		return myDep;
	}

	public void setMyDep(Depositor myDep) {
		this.myDep = myDep;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
