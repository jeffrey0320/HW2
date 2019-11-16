import javax.swing.text.DefaultEditorKit;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
	public static void main(String []args) throws FileNotFoundException, ParseException {
	Bank myAcct = new Bank();

	char choice;
	boolean not_done = true;

	File testFile = new File("testCases.txt");
	Scanner Key = new Scanner(testFile);
	//Scanner Key = new Scanner(System.in);

	PrintWriter output = new PrintWriter("myoutput.txt");
	//PrintWriter output = new PrintWriter(System.out);

	readAccts(myAcct);

	printAccts(myAcct, Key, output);
	
	TransactionTicket transTicket = new TransactionTicket();

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
			acctInfo(myAcct, Key, output);
			//type = new TransactionTicket("Account Information");
			break;
		case 'd':
		case 'D':
			deposit(myAcct, Key, output,transTicket);
			break;
		case 'w':
		case 'W':
			withdraw(myAcct, Key, output,transTicket);
			//type = new TransactionTicket("Withdrawal");
			break;
		case 'c':
		case 'C':
			clearCheck(myAcct, Key, output);
			//type = new TransactionTicket("Clear a Check");
			break;
		case 'n':
		case 'N':
			newAcct(myAcct, Key, output);
			//type = new TransactionTicket("New Account");
			break;
		case 'x':
		case 'X':
			deleteAcct(myAcct, Key, output);
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

	public static void readAccts(Bank myAcct) throws FileNotFoundException {
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
	
	public static void deposit(Bank myAcct,Scanner key,PrintWriter output,TransactionTicket ticket) throws ParseException {

		Account customerAcct = new Account();
		Calendar currentDate = Calendar.getInstance();

		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number
		// call findAcct to search if requestedAccount exists
		index = myAcct.findAcct(requestedAccount);

		if(index == -1){
			currentDate = Calendar.getInstance();
			ticket = new TransactionTicket(currentDate, "Deposit");
			TransactionReceipt info = new TransactionReceipt(ticket,false,"Account is not found.");

			output.println("Transaction Requested: " + info.getTransactionTicket().getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
			output.println();
		}else{
			customerAcct = myAcct.getAccts(index);
			String accType = customerAcct.getAccType();

			System.out.print("Enter amount to deposit: ");
			double amountToDeposit = key.nextDouble();

			if(accType.equals("CD")){

				System.out.print("Enter CD term: ");
				int amountOfTerm = key.nextInt();

				System.out.print("Enter maturity date: ");
				String dateOfMature = key.next();

				currentDate = Calendar.getInstance();
				ticket = new TransactionTicket(currentDate,"Deposit",amountToDeposit,amountOfTerm);
				TransactionReceipt depReceipt = customerAcct.makeDepositCD(ticket,myAcct,index,dateOfMature);

				if(depReceipt.getTransactionSuccessIndicatorFlag()){
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account Number: " + requestedAccount);
					output.printf("Previous Balance: $%.2f\n" , depReceipt.getPreTransactionBalance());
					output.printf("Amount to deposit: $%.2f\n", depReceipt.getTransactionTicket().getTransactionAmount());
					output.printf("Current Balance: $%.2f\n", depReceipt.getPostTransactionBalance());
					output.println("New maturity date: " + depReceipt.getPostTransactionMaturityDate().getTime());
					output.println();
				}else{
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + depReceipt.getTransactionFailureReason());
					output.println();
				}
			}else{
				currentDate = Calendar.getInstance();
				ticket = new TransactionTicket(currentDate,"Deposit",amountToDeposit);
				TransactionReceipt depReceipt = customerAcct.makeDeposit(ticket,myAcct,index);

				if(depReceipt.getTransactionSuccessIndicatorFlag()) {
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account Number: " + requestedAccount);
					output.printf("Previous Balance: $%.2f\n" , depReceipt.getPreTransactionBalance());
					output.printf("Current Balance: $%.2f\n" , depReceipt.getPostTransactionBalance());
					output.println();
				}else{
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + depReceipt.getTransactionFailureReason());
					output.println();
				}
			}
		}
		output.flush();
	}
	
	public static void withdraw(Bank myAcct,Scanner key,PrintWriter output,TransactionTicket ticket) throws ParseException {
		Account customerAcct = new Account();
		Calendar currentDate = Calendar.getInstance();

		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number

		System.out.print("Enter amount to deposit: ");
		double amountToDeposit = key.nextDouble();

		// call findAcct to search if requestedAccount exists
		index = myAcct.findAcct(requestedAccount);

		if (index == 1) {
			currentDate = Calendar.getInstance();
			ticket = new TransactionTicket(currentDate, "Withdrawal");
			TransactionReceipt info = new TransactionReceipt(ticket, false, "Account is not found.");

			output.println("Transaction Requested: " + info.getTransactionTicket().getTransactionType());
			output.println("Date of Transaction: " + info.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + info.getTransactionFailureReason());
			output.println();
		}else{
			customerAcct = myAcct.getAccts(index);
			String accType = customerAcct.getAccType();
			if(accType.equals("CD")) {

				System.out.print("Enter new CD term, Choose from 6, 12, 18, or 24 months: ");
				int amountOfTerm = key.nextInt();

				System.out.print("Enter maturity date: ");
				String dateOfMature = key.next();

				currentDate = Calendar.getInstance();
				ticket = new TransactionTicket(currentDate, "Withdrawal", amountToDeposit, amountOfTerm);
				TransactionReceipt depReceipt = customerAcct.makeWithdrawalCD(ticket, myAcct, index, dateOfMature);

				if(depReceipt.getTransactionSuccessIndicatorFlag()) {
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account Number: " + requestedAccount);
					output.printf("Previous Balance: $%.2f\n", depReceipt.getPreTransactionBalance());
					output.printf("Current Balance: $%.2f\n", depReceipt.getPostTransactionBalance());
					output.println("New maturity date: " + depReceipt.getPostTransactionMaturityDate().getTime());
					output.println();
				}else{
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + depReceipt.getTransactionFailureReason());
					output.println();
				}
			}
			else{
				currentDate = Calendar.getInstance();
				ticket = new TransactionTicket(currentDate,"Withdrawal",amountToDeposit);
				TransactionReceipt depReceipt = customerAcct.makeWithdrawal(ticket, myAcct, index);

				if(depReceipt.getTransactionSuccessIndicatorFlag()) {
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account Number: " + requestedAccount);
					output.printf("Previous Balance: $%.2f\n", depReceipt.getPreTransactionBalance());
					output.printf("Current Balance: $%.2f\n", depReceipt.getPostTransactionBalance());
					output.println();
				}else{
					output.println("Transaction Requested: " + depReceipt.getTransactionTicket().getTransactionType());
					output.println("Date of Transaction: " + depReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + depReceipt.getTransactionFailureReason());
					output.println();
				}
			}
		}
		output.flush();
	}

	public static void clearCheck(Bank myAcct, Scanner key, PrintWriter output) throws ParseException {

		Account accInfo = new Account();
		TransactionTicket newTicket = new TransactionTicket();
		TransactionReceipt receiptInfo = new TransactionReceipt();
		Calendar transactionDate = Calendar.getInstance();
		
		int requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter the account number: ");
		requestedAccount = key.nextInt(); // read-in the account number
		
		index = myAcct.findAcct(requestedAccount); // index of account
		
		if(index == -1) 
		{
			//account not found
			transactionDate = Calendar.getInstance();
			newTicket = new TransactionTicket(transactionDate, "Clear a check");
			receiptInfo = accInfo.getBalance(newTicket, myAcct, accInfo, index, false);
			
			output.println("Transaction Requested: " + newTicket.getTransactionType());
			output.println("Date of Transaction: " + receiptInfo.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + receiptInfo.getTransactionFailureReason());
			output.println();
		}
		else 
		{	// Account is found
			accInfo = myAcct.getAccts(index);				// get Account type
			String accountType = accInfo.getAccType();
			
			if(accountType.equals("Checking"))
			{
				// is a checking account
				System.out.print("Enter check date: ");
				String dateOfCheck = key.next();			// read-in newTicket of the check
				
				System.out.print("Enter check amount: ");
				double checkAmount = key.nextDouble();		// read-in amount of check
				
				newTicket = new TransactionTicket(transactionDate,"Clear a check");
				Check checkInfo = new Check(requestedAccount,checkAmount,dateOfCheck); 
				receiptInfo = accInfo.clearCheck(checkInfo,newTicket,myAcct,accInfo,index);

				if(receiptInfo.getTransactionSuccessIndicatorFlag()){
					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + receiptInfo.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account type: " + accountType);
					output.println("Account requested: " + requestedAccount);
					output.printf("Old Balance: $%.2f\n", receiptInfo.getPreTransactionBalance());
					output.printf("Amount to Withdraw: $%.2f\n", checkAmount);
					output.printf("New Balance: $%.2f\n", receiptInfo.getPostTransactionBalance());
					output.println();
				}else{
					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + receiptInfo.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account type: " + accountType);
					output.println("Error: " + receiptInfo.getTransactionFailureReason());
					output.println();
				}
			}
			else 
			{
				// not a checking account
				String reason = "Account is not Checking";
				transactionDate = Calendar.getInstance();
				newTicket = new TransactionTicket(transactionDate, "Clear a check");
				receiptInfo = accInfo.notChecking(newTicket,reason,false);
				
				output.println("Transaction Requested: " + newTicket.getTransactionType());
				output.println("Date of Transaction: " + receiptInfo.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account type: " + accountType);
				output.println("Error: " + receiptInfo.getTransactionFailureReason());
				output.println();
			}
		}
		output.flush();
	}
	
	public static void acctInfo(Bank myAcct, Scanner key, PrintWriter output) {
		Account accInfo = new Account();
		TransactionReceipt info = new TransactionReceipt();
		TransactionTicket date = new TransactionTicket();
		Calendar transactionDate = Calendar.getInstance();
		
		String requestedAccount;
		int index;
		// prompt for account number
		System.out.print("Enter social security number: ");
		requestedAccount = key.next(); // read-in the SSN

		for (int count = 0; count < myAcct.getNumAccts(); count++) {
			accInfo = myAcct.getAccts(count);
			if (accInfo.getMyDep().getSSN().equals(requestedAccount)) {
				output.println("Transaction Requested: Account Info");
				output.println("Name: " + accInfo.getMyDep().getMyName().getFirst() + " " +
						accInfo.getMyDep().getMyName().getLast());
				output.println("SSN: " + accInfo.getMyDep().getSSN());
				output.println("Account number: " + accInfo.getAccNum());
				output.println("Account Type: " + accInfo.getAccType());
				output.printf("Balance: $%.2f\n" , accInfo.getBalance());
				output.println();
			}
		}
		output.flush();
	}
	
	public static void newAcct(Bank myAcct, Scanner key, PrintWriter output) {
		TransactionTicket newTicket = new TransactionTicket();
		TransactionReceipt newReceipt = new TransactionReceipt();

		System.out.println("Enter New Account Number: ");
		int newAccount = key.nextInt();

		int index = myAcct.findAcct(newAccount);

		if(index == -1){
			//Account doesnt exist open account
			if (newAccount <= 999999 && newAccount >= 100000) {

				System.out.println("Please enter your full name, Social Security Number and Account type.");
				String first = key.next();
				String last = key.next();

				String ssn = key.next();
				String type = key.next();

				if (first.isEmpty() || last.isEmpty()) {
					Calendar date = Calendar.getInstance();
					newTicket = new TransactionTicket(date,"Open an Account");
					newReceipt = new TransactionReceipt(newTicket,false,"First or Last name is missing.");

					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + newReceipt.getTransactionFailureReason());
					output.println();
				} else if (ssn.isEmpty()) {
					Calendar date = Calendar.getInstance();
					newTicket = new TransactionTicket(date,"Open an Account");
					newReceipt = new TransactionReceipt(newTicket,false,"Social Security Number is needed.");

					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + newReceipt.getTransactionFailureReason());
					output.println();
				} else if (type.isEmpty()) {
					Calendar date = Calendar.getInstance();
					newTicket = new TransactionTicket(date,"Open an Account");
					newReceipt = new TransactionReceipt(newTicket,false,"Account type is needed.");

					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Error: " + newReceipt.getTransactionFailureReason());
					output.println();
				}else{
					String[] customerInfo = {first,last,ssn,type};
					Account newAccounts = new Account(customerInfo[0],customerInfo[1],customerInfo[2],customerInfo[3],newAccount);

					Calendar date = Calendar.getInstance();
					newTicket = new TransactionTicket(date,"Open an Account");
					newReceipt = myAcct.openNewAcct(newTicket,customerInfo,newAccounts);

					output.println("Transaction Requested: " + newTicket.getTransactionType());
					output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
					output.println("Account number " + newAccount + " has been created.");
					output.println("Customer name: " + newAccounts.getMyDep().getMyName().getFirst() + " " +
									newAccounts.getMyDep().getMyName().getLast());
					output.println("SSN: " + newAccounts.getMyDep().getSSN());
					output.println("Account type: " + newAccounts.getAccType());
					output.printf("Balance: $%.2f\n" , newAccounts.getBalance());
					output.println();
				}

			}else{
				Calendar date = Calendar.getInstance();
				newTicket = new TransactionTicket(date,"Open an Account");
				newReceipt = new TransactionReceipt(newTicket,false,"Account number is too small or too large.");

				output.println("Transaction Requested: " + newTicket.getTransactionType());
				output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Error: " + newReceipt.getTransactionFailureReason());
				output.println();
			}
		}else{
			Calendar date = Calendar.getInstance();
			newTicket = new TransactionTicket(date,"Open an Account");
			newReceipt = new TransactionReceipt(newTicket,false,"Account number exists");

			output.println("Transaction Requested: " + newTicket.getTransactionType());
			output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + newReceipt.getTransactionFailureReason());
			output.println();
		}
	}
	
	public static void deleteAcct(Bank myAcct, Scanner key, PrintWriter output) {
		TransactionTicket newTicket = new TransactionTicket();
		TransactionReceipt newReceipt = new TransactionReceipt();
		Account accInfo = new Account();
		System.out.print("Enter Account Number: ");
		int delAccount = key.nextInt();

		int index = myAcct.findAcct(delAccount);

		if(index == -1){
				//Account doesnt exist
			Calendar date = Calendar.getInstance();
			newTicket = new TransactionTicket(date,"Delete an Account");
			newReceipt = new TransactionReceipt(newTicket,false,"Account number doesn't exist");

			output.println("Transaction Requested: " + newTicket.getTransactionType());
			output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
			output.println("Error: " + newReceipt.getTransactionFailureReason());
			output.println();
		}else{
			Calendar date = Calendar.getInstance();
			newTicket = new TransactionTicket(date,"Delete an Account");
			newReceipt = myAcct.deleteAcct(newTicket,myAcct,accInfo,index,delAccount);

			if(newReceipt.getTransactionSuccessIndicatorFlag()){
				//delete account
				output.println("Transaction Requested: " + newTicket.getTransactionType());
				output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Account " + delAccount + " has been deleted.");
				output.println();
			}else{
				//cant delete
				output.println("Transaction Requested: " + newTicket.getTransactionType());
				output.println("Date of Transaction: " + newReceipt.getTransactionTicket().getDateOfTransaction().getTime());
				output.println("Error: " + newReceipt.getTransactionFailureReason());
				output.println();
			}
		}
		output.flush();
	}
}
	

