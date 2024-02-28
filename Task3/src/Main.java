import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
    private static Map<Integer, User> usersDatabase = new HashMap<>();
    private static User currentUser;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        intializeUsers();

        Scanner scanner = new Scanner(System.in);
        int loginAttempts = 0;
        boolean authenticated = false;

        while (!authenticated && loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("Enter user ID: ");
            int userId = scanner.nextInt();
            System.out.println("Enter PIN: ");
            int pin = scanner.nextInt();

            if (authenticateUser(userId, pin)){
                System.out.println("Authentication successful. Welcome, " + currentUser.getName() + "!");
                authenticated = true;
            } else {
                System.out.println("Authentication failed. Please try again.");
                loginAttempts++;
            }
        }

        if (authenticated) {
            performTransactions(scanner);
        } else {
            System.out.println("Maximum login attempts reached. Exiting.");
        }
        scanner.close();
    }

    private static void intializeUsers() {
        User user1 = new User(123456, "David Jones", 1234);
        user1.addAcount(new Account("Savings", 1000.0));
        user1.addAcount(new Account("Checking", 2000.0));

        User user2 = new User(456789, "Mary Diaz", 4567);
        user2.addAcount(new Account("Savings", 1500.0));
        user2.addAcount(new Account("Checking", 2500.0));

        usersDatabase.put(user1.getUserId(), user1);
        usersDatabase.put(user2.getUserId(), user2);
    }

    private static boolean authenticateUser(int userId, int pin){
        User user = usersDatabase.get(userId);
        if (user != null && user.getPin() == pin) {
            currentUser = user;
            return true;
        }
        return false;
    }

    private static void performTransactions(Scanner scanner){
        boolean quit = false;
        while (!quit) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.println("\nChoose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    quit = true;
                    System.out.println("Thank you. Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : currentUser.getTransactionHistory()){
            if (transaction != null) {
                System.out.println(transaction);
            }
        }
    }
    private static void withdraw(Scanner scanner) {
        System.out.println("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount <= 0){
            System.out.println("Invalid amount");
            return;
        }
        if (currentUser.withdraw(amount)){
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds");
        }
    }

    private static void deposit(Scanner scanner){
        System.out.println("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount <= 0){
            System.out.println("Invalid amoount");
            return;
        }
        currentUser.deposit(amount);
        System.out.println("Deposit Successful");
    }

    private static void transfer(Scanner scanner){
        System.out.println("Enter recipient's account number: ");
        int recipientId = scanner.nextInt();
        System.out.println("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        User recipient = usersDatabase.get(recipientId);
        if (recipient == null){
            System.out.println("Invalid number.");
            return;
        }
        if (amount <= 0){
            System.out.println("Invalid amount.");
            return;
        }
        if (currentUser.transfer(recipient, amount)) {
            System.out.println("Transfer Successful");
        } else {
            System.out.println("Transfer failed");
        }
    }
}