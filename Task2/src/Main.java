import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerNum = 1; //Lower number of the range
        int higherNum = 100; //Higher number of the range
        int correctNum = random.nextInt(higherNum - lowerNum + 1) + lowerNum; //Generate a random number within the given range.
        int maxAttempts = 5; //Number of attempts
        int attempts = 0;
        int score = 100; //Initial score

        System.out.println("GUESS THE NUMBER");
        System.out.println("You have to guess a number between "+ lowerNum + " and " +  higherNum + ". \nYou have 5 attempts. Try guessing it.\n");

        while (attempts < maxAttempts) {
            System.out.println("Enter a number: ");
            int num = scanner.nextInt();
            attempts++;

            if (num == correctNum) {
                System.out.println("Nice work! You guessed the number in " + attempts + "attempts.");
                System.out.println("Your score: " +score);
                break;
            } else if ( num < correctNum) {
                System.out.println("The number you guessed is lower than the correct number. Try again.");
            } else {
                System.out.println("The number you guessed is higher than the correct number. Try again.");
            }

            score -= 20;
        }

        if (attempts == maxAttempts){
            System.out.println("Sorry no more attempts. The correct number was " + correctNum + ".");
            System.out.println("Your score: 0");
        }

        scanner.close();



    }
}