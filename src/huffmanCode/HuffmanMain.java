package huffmanCode;

import java.util.Scanner;
import java.util.LinkedList;

/**
 * The main class used for the creation encoding, decoding, and creating huffman trees.
 */
public class HuffmanMain {

    private static TreeNode<String> huffmanTree;
    private static LinkedList<Cons> huffmanTable;


    /**
     * The method to run the program
     */
    public static void main(String[] args) {

        int choice;


        while (true) {
            printMenu("\nHuffman Coding Project\n", "Create a Huffman tree (by typing the phrase)",
                    "Create a Huffman tree (by reading from a file)", "Print the Current Huffman Table",
                    "Encode a text into Huffman code", "Decode a Huffman code into text", "Exit");
            choice = getChoice("Input -> ", 1, 6);

            switch (choice) {
                case 1:
                    huffmanTree = HuffmanTreeMake.makeTree(false);
                    huffmanTable = HuffmanTreeMake.makeTable(huffmanTree);
                    break;
                case 2:
                    try {
                        huffmanTree = HuffmanTreeMake.makeTree(true);
                        huffmanTable = HuffmanTreeMake.makeTable(huffmanTree);
                    } catch (Exception e) {
                        huffmanTree = null;
                        huffmanTable = null;
                    }
                    break;
                case 3:
                    if (huffmanTable != null) HuffmanTreeMake.printHuffmanTable(huffmanTable);
                    else {
                        System.out.println("Error: You need to make a Huffman tree first!");
                        pressEnter();
                    }
                    break;
                case 4:
                    HuffmanTranslator.encode(huffmanTable);
                    break;
                case 5:
                    HuffmanTranslator.decode(huffmanTable);
                    break;
                case 6:
                    System.out.println("Goodbye...");
                    System.exit(0);
            }
        }
    } // end of main method


    /**
     * Prints the menu.
     * @param header
     * @param choices
     */
    public static void printMenu(String header, String...choices) {

        int choiceNum = 1;
        System.out.println(header);

        for (String choice: choices) {
            System.out.printf("%d. %s\n", choiceNum, choice);
            choiceNum++;
        }
    } // end of printMenu method


    /**
     * Gets input from the user.
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    public static int getChoice(String prompt, int min, int max) {
        Scanner kbd = new Scanner(System.in);
        int choice;

        while (true) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(kbd.nextLine());
                if (choice < min || choice > max) {
                    System.out.printf("Error: your input must be between %d and %d.\n", min, max);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: your input must be an integer value.\n");
                continue;
            }
        } // end of getChoice method

        return choice;
    }

    /**
     * Lets the user press enter to continue.
     */
    public static void pressEnter() {
        Scanner kbd = new Scanner(System.in);
        System.out.println("Press enter to continue...\n");
        kbd.nextLine();
    }
} // end of pressEnter method
