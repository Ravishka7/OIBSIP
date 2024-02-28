import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
    private static Map<Integer, User> userDatabase = new HashMap<>();
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
}