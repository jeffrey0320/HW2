import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
	public static void main(String []args) throws FileNotFoundException, ParseException {
	Bank myAcct = new Bank();

	char choice;
	boolean not_done = true;

	//File testFile = new File("testCases.txt");

	//Scanner Key = new Scanner(testFile);
	Scanner Key = new Scanner(System.in);

	//PrintWriter output = new PrintWriter("myoutput.txt");
	PrintWriter output = new PrintWriter(System.out);

	readAccts(myAcct);

	printAccts(myAcct, Key, output);
	
	//TransactionTicket type = new TransactionTicket();

	do {
		menu();
		choice = Key.next(".").charAt(0);
		switch (choice) {
		case 'q':
		case 'Q':
			not_done = false;
			printAccts(myAcct, Key, output);
			break;
		case 'b':
		case 'B':
			balance(myAcct, Key, output);
			//type = new TransactionTicket("Balance");
			break;
		case 'i':
		case 'I':
			//acctInfo(myAcct, Key, output);
			//type = new TransactionTicket("Account Information");
			break;
		case 'd':
		case 'D':
			deposit(myAcct, Key, output);
			//type = new TransactionTicket("Deposit");
			break;
		case 'w':
		case 'W':
			withdraw(myAcct, Key, output);
			//type = new TransactionTicket("Withdrawal");
			break;
		case 'c':
		case 'C':
			clearCheck(myAcct, Key, output);
			//type = new TransactionTicket("Clear a Check");
			break;
		case 'n':
		case 'N':
			//newAcct(myAcct, Key, output);
			//type = new TransactionTicket("New Account");
			break;
		case 'x':
		case 'X':
			//deleteAcct(myAcct, Key, output);
			//type = new TransactionTicket("Delete Account");
			break;
		default:
			output.println("Error: " + choice + " is an invalid selection -  try again");
			output.println();
			output.flush();
			break;
			}
	} while (not_done);
		Key.close();
		output.close();
		System.out.println();
		System.out.println("The program is terminating");
}

	public static void readAccts(Bank myAcct) throws FileNotFoundException 
	{
		String line;
	
		File myFile = new File("myinput.txt");
	
		Scanner cin = new Scanner(myFile);
	
		while (cin.hasNext()) 
		{
	
			line = cin.nextLine();
			String[] tokens = line.split(" ");
	
			Name myName = new Name(tokens[0], tokens[1]);
			Depositor myInfo = new Depositor(tokens[2], myName);
			Account acctInfo = new Account(Integer.parseInt(tokens[3]), tokens[4], Double.parseDouble(tokens[5]),
					myInfo);
			myAcct.openNewAcct(acctInfo);
		}
		cin.close();
	}
	
	public static void menu() {
		System.out.println();
		System.out.println("Select one of the following transactions:");
		System.out.println("\t****************************");
		System.out.println("\t    List of Choices         ");
		System.out.println("\t****************************");
		System.out.println("\t     W -- Withdrawal");
		System.out.println("\t     D -- Deposit");
		System.out.println("\t     C -- Clear Check");
		System.out.println("\t     N -- New Account");
		System.out.println("\t     B -- Balance Inquiry");
		System.out.println("\t     I -- Account Info");
		System.out.println("\t     X -- Delete Account");
		System.out.println("\t     Q -- Quit");
		System.out.println();
		System.out.print("\tEnter your selection: ");
	}
	
	public static void printAccts(Bank myAcct, Scanner key, PrintWriter output) {

		Name myName = new Name();
		Depositor myInfo = new Depositor();
		Account myAcc = new Account();

		output.println("\t\t\t\t\t\tDatabase of Bank Accounts");
		output.println();
		output.printf("%-27s%-9s%-16s%-8s%12s", "Name", "SSN", "Account Number", " Account Type", " Balance");
		output.println();

		for (int count = 0; count < myAcct.getNumAccts(); count++) {
			myAcc = myAcct.getAccts(count);
			myInfo = myAcc.getMyDep();
			myName = myInfo.getMyName();
			output.printf("%-12s", myName.getFirst());
			output.printf("%-12s", myName.getLast());
			output.printf("%-9s", myInfo.getSSN());
			myAcc = myAcct.getAccts(count);
			output.printf("%13s", myAcc.getAccNum());
			output.printf("%19s%-3s", myAcc.getAccType(), "");
			output.printf("$%9.2f", myAcc.getBalance());
			output.println();
		}
		// flush the output file
		output.println();
		output.flush();
	}
	
	public static void balance(Bank myAcct, Scanner key, PrintWriter output) {

		Account accInfo = new Account();
		TransactionReceipt info = new TransactionReceipt();
		TransactionTicket date = new TransactionTicket();
		Calendar transactionDate = Calendar.getInstance();

		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = myAcct.findAcct(requestedAccount);

		if (index == -1) // invalid account
		{
			transactionDate = Calendar.getInstance();
			date = new TransactionTicket(transactionDate, "Balance Inquiry");
			info = accInfo.getBalance(date, myAcct, accInfo, index, false);
			
			output.println("Transaction Requested: " + date.getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
		} 
		else // valid account
		{
			transactionDate = Calendar.getInstance();
			date = new TransactionTicket (transactionDate,"Balance Inquiry");
			info = accInfo.getBalance(date,myAcct, accInfo, index,true);
			
			output.println("Transaction Requested: " + date.getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Account Number: " + requestedAccount);
			output.printf("Current Balance: $" + info.getPostTransactionBalance());
			output.println();
		}
		output.println();
		output.flush();
	}
	
	public static void deposit(Bank myAcct,Scanner key,PrintWriter output) {
		
		Account accInfo = new Account();
		TransactionReceipt info = new TransactionReceipt();
		TransactionTicket date = new TransactionTicket();
		Calendar transactionDate = Calendar.getInstance();
		
		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = myAcct.findAcct(requestedAccount);
		
		if (index == -1) // invalid account
		{
			transactionDate = Calendar.getInstance();
			date = new TransactionTicket(transactionDate, "Deposit");
			info = accInfo.getBalance(date, myAcct, accInfo, index, false);
			
			output.println("Transaction Requested: " + date.getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
			output.println();
		} 
		else 
		{
			double amountToDeposit;
			System.out.println("Enter amount to deposit: ");
			amountToDeposit = key.nextDouble();
			
			if (amountToDeposit <= 0.00) { // invalid amount to deposit
				
				transactionDate = Calendar.getInstance();	
				date = new TransactionTicket(transactionDate,"Deposit");
				info = accInfo.makeDeposit(date,myAcct,accInfo,index,false,amountToDeposit);
				
				output.println("Transaction Requested: Deposit");
				output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account Number: " + requestedAccount);
				output.printf("Old Balance: $%.2f\n", info.getPreTransactionBalance());
				output.printf("Amount to Deposit: $%.2f\n", amountToDeposit);
				output.printf("Error: $%.2f is an invalid amount",
					 amountToDeposit);
				output.println();
				output.println();
			} else {
				
				transactionDate = Calendar.getInstance();
				date = new TransactionTicket(transactionDate,"Deposit");
				info = accInfo.makeDeposit(date,myAcct,accInfo,index,true,amountToDeposit);
				
				output.println("Transaction Requested: Deposit");
				output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account Number: " + requestedAccount);
				output.printf("Old Balance: $%.2f\n", info.getPreTransactionBalance());
				output.printf("Amount to Deposit: $%.2f\n", amountToDeposit);
				output.printf("New Balance: $%.2f\n", info.getPostTransactionBalance());
				output.println();
			}
		}
		output.flush();
	}
	
	public static void withdraw(Bank myAcct,Scanner key,PrintWriter output) 
	{
		Account accInfo = new Account();
		TransactionReceipt info = new TransactionReceipt();
		TransactionTicket date = new TransactionTicket();
		Calendar transactionDate = Calendar.getInstance();
		
		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = myAcct.findAcct(requestedAccount);
	
		if(index == -1) // Account doesnt exist
		{
			transactionDate = Calendar.getInstance();
			date = new TransactionTicket(transactionDate, "Withdraw");
			info = accInfo.getBalance(date, myAcct, accInfo, index, false);
			
			output.println("Transaction Requested: " + date.getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
			output.println();
		}
		else 
		{
			double amountToWithdraw;
			System.out.print("Enter amount to withdraw: ");
			amountToWithdraw = key.nextDouble();
			
			date = new TransactionTicket(transactionDate, "Withdraw");
			info = accInfo.makeWithdrawal(date, myAcct, accInfo, index, amountToWithdraw);
			 
			if(info.getTransactionSuccessIndicatorFlag()) 
			{
				output.println("Transaction Requested: " + info.getTransactionTicket().getTransactionType());
				output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account requested: " + requestedAccount);
				output.printf("Old Balance: $%.2f\n", info.getPreTransactionBalance());
				output.printf("Amount to Withdraw: $%.2f\n", amountToWithdraw);
				output.printf("New Balance: $%.2f\n", info.getPostTransactionBalance());
				output.println();
			}
			else
			{
				output.println("Transaction Requested: " + info.getTransactionTicket().getTransactionType());
				output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account requested: " + requestedAccount);
				output.printf("Old Balance: $%.2f\n", info.getPreTransactionBalance());
				output.printf("Amount to Withdraw: $%.2f\n", amountToWithdraw);
				output.println("Error:" + info.getTransactionFailureReason());
			}
		}
		output.flush();
	}
	
	public static void clearCheck(Bank myAcct, Scanner key, PrintWriter output) throws ParseException 
	{
		Account accInfo = new Account();
		TransactionReceipt info = new TransactionReceipt();
		TransactionTicket date = new TransactionTicket();
		Calendar transactionDate = Calendar.getInstance();
		
		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number
		
		index = myAcct.findAcct(requestedAccount);
		
		if(index == -1) 
		{
			//account not found
			transactionDate = Calendar.getInstance();
			date = new TransactionTicket(transactionDate, "Clear a check");
			info = accInfo.getBalance(date, myAcct, accInfo, index, false);
			
			output.println("Transaction Requested: " + date.getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
			output.println();
		}
		else 
		{
			accInfo = myAcct.getAccts(index);
			String accountType = accInfo.getAccType();
			
			if(accountType.equals("Checking"))
			{
				// is a checking account
				System.out.print("Enter check date: ");
				String dateOfCheck = key.next();
				
				System.out.print("Enter check amount: ");
				double checkAmount = key.nextDouble();
				
				date = new TransactionTicket(transactionDate,"Clear a check");
				Check checkInfo = new Check(requestedAccount,checkAmount,dateOfCheck); 
				info = accInfo.clearCheck(checkInfo,date,myAcct,accInfo,index);
				
				output.println("Transaction Requested: " + date.getTransactionType());
				output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account type: " + accountType);
				output.println("Error: " + info.getTransactionFailureReason());
				output.println();

			}
			else 
			{
				// not a checking account
				output.println("Error: Account type is not Checking.");
			}
		}

	}
}
	

