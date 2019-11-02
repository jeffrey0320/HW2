
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
		
		public TransactionReceipt openNewAcct(Account newAccount) {
			accts[numAccts] = newAccount;
			numAccts++;
			return null;
			
		}
		public TransactionReceipt openNewAcct(TransactionTicket Ticket) {
			TransactionReceipt openAcct = new TransactionReceipt();
			return openAcct;
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

		private void setNumAccts(int numAccts) {
			this.numAccts = numAccts;
		}

		public Account getAccts(int index) {
			return accts[index];
		}

		private void setAccts(Account[] accts) {
			this.accts = accts;
		}
}
