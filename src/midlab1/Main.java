package midlab1;

import java.util.Scanner;

/**
 * A class used for reading and evaluating postfix expressions.
 */
public class Main {
    static Scanner kbd = new Scanner(System.in);

    /**
     * Method to show the Main Menu
     */
    public static void showMenu(){
        System.out.println("----===== MAIN MENU =====----" +
                "\n 1. Convert an Infix to Postfix" +
                "\n 2. Evaluate a Postfix Expression" +
                "\n 3. Quit");
    }

    /**
     * Method to read the choice of the user
     */
    public static int readChoice(int min, int max){
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            try {
                System.out.println("What do you want to do?");
                choice = Integer.parseInt(sc.nextLine());
                System.out.println("\n\n");
                if (choice > max || choice < min) {
                    System.out.println("Invalid choice! Please pick from numbers " + min + " ~ " + max);
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid choice! Please pick from numbers " + min + " ~ " + max);
                continue;
            }
        }
        return choice;
    }

    /**
     * Runs a simple program that does nothing but evaluate or convert to post-fix expressions.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int choice;
        do {
            showMenu();
            choice = readChoice(1, 3);
            switch (choice) {
                case 1:
                    InfixConverter.conversion();
                    break;
                case 2:
                    PostfixEvaluator.run();
                    break;
                case 3:
                    System.out.println("Thank you for using my program.");
                    System.out.println("Enjoy the rest of your day.");
                    break;
            } //end switch
        } while (choice != 3);
    } //end main method
}
