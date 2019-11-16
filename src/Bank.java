
public class Bank {
		final int MAX_NUM = 50;

		private Account[] accts;
		private int numAccts;

		public Bank() {
			accts = new Account[MAX_NUM];
			numAccts = 0;
		}
		
		public Bank(int accts) {
			numAccts = accts;
		}
		
		public Bank(Account[] acct) {
			accts = acct;
		}
		
		public void openNewAcct(Account newAccount) {
			accts[numAccts] = newAccount;
			numAccts++;
		}
		public TransactionReceipt openNewAcct(TransactionTicket Ticket,String[] info,Account accInfo) {
			TransactionReceipt openAcct = new TransactionReceipt();
			accts[numAccts] = accInfo;
			numAccts++;
			openAcct = new TransactionReceipt(Ticket,true,0);
			return openAcct;
		}

		public TransactionReceipt deleteAcct(TransactionTicket info,Bank acc, Account accInfo, int index, int num){
			TransactionReceipt delAcct = new TransactionReceipt();
			String reason;
			int tempNumAccts = numAccts;

			accInfo = acc.getAccts(index);
			double bal =  accInfo.getBalance();
			accInfo = new Account(bal);

			if(acc.getAccts(index).getBalance()<0){
				reason = "Account has a negative balance and cannot be deleted.";
				delAcct = new TransactionReceipt(info,false,reason);
				return delAcct;
			}else if(acc.getAccts(index).getBalance()>0){
				reason = "Account has a balance, Withdraw your balance and try again.";
				delAcct = new TransactionReceipt(info,false,reason);
				return delAcct;
			}else {
				for (int i = 0; i < accts.length; i++) {
					if (accts[i].getAccNum() == num) {
						for (int j = i; j < accts.length - 1; j++) {
							accts[index] = accts[tempNumAccts-1];
						}
						break;
					}
				}
				numAccts--;
				delAcct = new TransactionReceipt(info,true,bal);
				return delAcct;
			}
		}
		
		public int findAcct(int reqAccount) {
			for (int index = 0; index < numAccts; index++)
				if (accts[index].getAccNum() == reqAccount)
					return index;
			return -1;
		}

		public int getNumAccts() {
			return numAccts;
		}

		public Account getAccts(int index) {
			return accts[index];
		}

}
