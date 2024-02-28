import java.util.HashMap;
import java.util.Map;

public class User {
    private int userId;
    private String name;
    private int pin;
    private Map<String, Account> accounts;
    private Transaction[] transactionHistory;
    private int transactionCount;

    public User(int userId, String name, int pin) {
        this.userId = userId;
        this.name = name;
        this.pin = pin;
        this.accounts = new HashMap<>();
        this.transactionHistory = new Transaction[50];
        this.transactionCount = 0;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }

    public void addAcount(Account account) {
        accounts.put(account.getAccountType(), account);
    }

    public Account getAccount(String accountType){
        return accounts.get(accountType);
    }

    public Transaction[] getTransactionHistory(){
        return transactionHistory;
    }

    public boolean withdraw(double amount){
        Account account = accounts.get("Checking");
        if (account == null || account.getBalance() < amount) {
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        addTransaction(new Transaction("Withdraw", -amount));
        return true;
    }

    public void deposit(double amount) {
        Account account = accounts.get("Checking");
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            addTransaction(new Transaction("Deposit", amount))
        }
    }

    public boolean transfer(User recipient, double amount) {
        Account senderAccount = accounts.get("Checking");
        Account recipientAccount = recipient.getAccount("Checking");
        if (senderAccount == null || recipientAccount == null || senderAccount.getBalance() < amount) {
            return false;
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        addTransaction(new Transaction("Transfer to " + recipient.getName(), -amount));
        recipient.addTransaction(new Transaction("Transfer from " + getName(), amount));
        return true;
    }

    private void addTransaction(Transaction transaction) {
        if (transactionCount < 100) {
            transactionHistory[transactionCount++] = transaction;
        }
    }
}
