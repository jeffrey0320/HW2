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
	
	public TransactionReceipt makeDeposit(TransactionTicket info,Bank acc, Account bal,int index,boolean flag,double depAmount) {
		if(flag && depAmount > 0.0) {
			bal = acc.getAccts(index);
			balance =  bal.getBalance();
			double newBal = balance + depAmount;
			TransactionReceipt newRec = new TransactionReceipt(info,true,balance,newBal);
			bal.setBalance(newBal);
			return newRec;
		}else {
			String reason = "Cant deposit amount less than 0";
			bal = acc.getAccts(index);
			TransactionReceipt newRec = new TransactionReceipt(info,false,reason,balance);
			balance =  bal.getBalance();
            return newRec;
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

		if(check.before(beforeSixMonths)) {

			String reason = "The date on the check is more than 6 months ago.";
			clearedCheck = new TransactionReceipt(info,false,reason);
			return clearedCheck;
		}
		else if(check.after(timeNow)){
			String reason = "The date on the check is after today's date. ";
			clearedCheck = new TransactionReceipt(info,false,reason);
			return clearedCheck;
		}
		else 
		{
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
