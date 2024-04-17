package huffmanCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * This is the class responsible for creating the huffman tree.
 */
public class HuffmanTreeMake {

    private static String phrase;
    // frequencyConsTable is similar to frequencyChars, except it uses cons cells instead of trees.
    private static LinkedList<Cons> frequencyConsTable;

    /**
     * This is the method that initializes the process of constructing the huffman tree.
     * @return
     */
    public static TreeNode<String> makeTree(boolean readFile) {
        frequencyConsTable = new LinkedList<>();
        Scanner kbd = new Scanner(System.in);

        // frequencyChars is a linked list that represents the frequencies of
        // every character that appears in the input. It's used in building the
        // frequency table to be used for creating the huffman tree. Each frequency
        // count in the 'table' is represented by a binary tree node whose root datum is the
        // count in the form of a string and the left node being the character assigned
        // to that count.
        LinkedList<TreeNode> frequencyChars = new LinkedList<>();

        if (!readFile) {
            System.out.println("Enter a phrase for the Huffman tree:");
            phrase = kbd.nextLine();

        }

        else {
            try {
                phrase = readFromFile();
            } catch (IOException e) {
                System.out.println("Error: The file cannot be opened (did you double-check the path?)");
                throw new RuntimeException();
            }
        }
        buildFrequencyChars(frequencyChars);

        for (int i = 0, size = frequencyChars.size(); i < size; i++) {
            TreeNode<String> currentTree = frequencyChars.get(i);
            String currentText = currentTree.getLeftNode().getData();
            int currentCount = Integer.parseInt(currentTree.getData());
            frequencyConsTable.push(new Cons(currentText, currentCount));
        }


        printFrequencyTable(frequencyChars);
        return makeTreeHelper(frequencyChars);
    } //end of makeTree method


    public static String readFromFile() throws IOException {
        String str = "";
        Scanner kbd = new Scanner(System.in);
        System.out.println("Type the file's path: ");
        String path = kbd.nextLine();

            BufferedReader text = new BufferedReader(new FileReader(path));
            String line = text.readLine();
            while (line != null){
                str +=line;
                line = text.readLine();
            }

        return str;
    }

    /**
     * Method used for constructing a table that consists of the characters from the phrase
     * with their respective huffman encodings.
     * @param huffmanTree
     * @return
     */
    public static LinkedList<Cons> makeTable(TreeNode<String> huffmanTree) {
        LinkedList<Cons> table = new LinkedList<>();

        getBinaries(table, huffmanTree, "");

        printHuffmanTable(table);
        showStorageSaved(table);

        return table;
    } //end of makeTable method


    /**
     * Method used for computing the amount of storage saved by using Huffman encoding.
     * @param huffmanTable
     */
    public static void showStorageSaved(LinkedList<Cons> huffmanTable) {
        double fullBitsCount = phrase.length() * 7.0;
        double huffmanBitsCount = 0.0;
        for (int i = 0, size = frequencyConsTable.size(); i < size; i++) {
            Cons<String, Integer> currentCons = frequencyConsTable.get(i);
            String currentText = currentCons.car();
            int textCount = currentCons.cdr();

            huffmanBitsCount += HuffmanTranslator.referBinaryEquivalent(currentText, huffmanTable).length()
                    * textCount;
        }

        double storageSaved = 100.0 * ((fullBitsCount - huffmanBitsCount) / (fullBitsCount));
        System.out.println("Storage saved for using Huffman Encoding:");
        System.out.printf("((%.0f - %.0f) / (%.0f)) * 100.0 = %.2f%%\n", fullBitsCount, huffmanBitsCount, fullBitsCount,
                storageSaved);
        HuffmanMain.pressEnter();
    } //end of showStorageSaved method


    /**
     * Method used for printing the table of characters and their respective huffman encodings.
     * @param huffmanTable
     */
    public static void printHuffmanTable(LinkedList<Cons> huffmanTable) {

        // Print the header.
        System.out.println("\n\nHuffman Encodings Table");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("|%20s|%20s|%20s|\n", "Character", "Binary", "Number of Bits");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0, size = huffmanTable.size(); i < size; i++) {
            Cons<String, String> currentCell = huffmanTable.get(i);
            String currentChar = currentCell.car();
            String binaryPattern = currentCell.cdr();
            System.out.printf("|%20s|%20s|%20s|\n", currentChar, binaryPattern, binaryPattern.length());
            System.out.println("----------------------------------------------------------------");
        }
        HuffmanMain.pressEnter();
    } //end of printHuffmanTable method


    /**
     * A helper function for makeTable that recursively computes the huffman encodings of each
     * character from the string.
     * @param table
     * @param huffmanTree
     * @param currentBinaryPattern
     */
    public static void getBinaries(LinkedList<Cons> table,
                                   TreeNode<String> huffmanTree, String currentBinaryPattern) {

        // If the character of the node is just an empty string, do nothing with it.
        if (huffmanTree.getData().equals("")) return;

        // If the leaf node is reached and it is not assigned to an empty string,
        // push a cons cell containing the character and the current binary pattern as the contents.
        else if (huffmanTree.getLeftNode() == null && huffmanTree.getRightNode() == null) {
            Cons<String, String> record = new Cons(huffmanTree.getData(), currentBinaryPattern);
            table.push(record);
        }

        // If the above base cases were not achieved, continue the recursion through the left
        // and right child nodes while building the current binary patterns.
        else  {
            // Go to left node.
            getBinaries(table, huffmanTree.getLeftNode(), currentBinaryPattern + "0" );
            // Go to right node.
            getBinaries(table, huffmanTree.getRightNode(), currentBinaryPattern + "1" );
        }
    }

    /**
     * Prints the frequency table of the characters in the file
     *
     * @param frequencyChars A LinkedList of TreeNodes that contain the character and its frequency.
     */
    public static void printFrequencyTable(LinkedList<TreeNode> frequencyChars) {

        // Print the table header.
        System.out.println("\n\nHuffman Frequency Table");
        System.out.println("-------------------------------------------");
        System.out.printf("|%20s|%20s|\n", "Character", "Count");
        System.out.println("-------------------------------------------");

        // Print the rest of the table.
        for (int i = 0, size = frequencyChars.size(); i < size; i++) {
            TreeNode<String> currentTree = frequencyChars.get(i);
            String character = currentTree.getLeftNode().getData();
            int count = Integer.parseInt(currentTree.getData());
            System.out.printf("|%20s|%20s|\n", character, count);
            System.out.println("-------------------------------------------");
        }
        HuffmanMain.pressEnter();
    }

    /**
     * A helper function for the makeTree method that does the actual creation of the huffman tree.
     * @return
     */
    public static TreeNode<String> makeTreeHelper(LinkedList<TreeNode> frequencyChars) {

        TreeNode<String> huffmanTree;

        // While the huffman tree is not the sole element in frequencyChars...
        while (frequencyChars.size() > 1) {

            // First, find the two trees with the least count and pop them.
            TreeNode<String> tree1 = findLowest(frequencyChars);
            TreeNode<String> tree2 = findLowest(frequencyChars);
            int firstTreeWeight = Integer.parseInt(tree1.getData());
            int secondTreeWeight = Integer.parseInt(tree2.getData());

            // Next, use the two given trees to build the partial huffman tree.
            TreeNode<String> huffmanPart = new TreeNode<>();
            huffmanPart.setData(String.valueOf(firstTreeWeight + secondTreeWeight));

            // You can tell if one of the trees is a partial huffman tree if
            // the root node has children nodes on both sides; otherwise, the
            // data from the character node should be the only data extracted when
            // setting the children of the partial huffman tree.
            if (tree1.getRightNode() == null)
                huffmanPart.setLeft(tree1.getLeftNode());
            else
                huffmanPart.setLeft(tree1);

            if (tree2.getRightNode() == null)
                huffmanPart.setRight(tree2.getLeftNode());
            else
                huffmanPart.setRight(tree2);

            // Don't forget to push the partial huffman tree to the frequencyChars list.
            frequencyChars.push(huffmanPart);
        }

        // If for some reason the root of the huffman tree does not have children nodes on both sides,
        // that means the user only inputted one type of character in the phrase. We can still make a huffman
        // tree by assigning the root as the number of the character count, the left node being the character
        // itself, and the right node being simply an empty string.
        huffmanTree = frequencyChars.pop();
        if (huffmanTree.getLeftNode() == null || huffmanTree.getRightNode() == null) {
            TreeNode<String> right = new TreeNode<>();
            right.setData("");
            huffmanTree.setRight(right);
        }

        return huffmanTree;

    }

    /**
     * Used for finding the tree in frequencyChars that has the lowest weight
     * @param frequencyChars
     * @return
     */
    public static TreeNode<String> findLowest(LinkedList<TreeNode> frequencyChars) {
        TreeNode<String> lowestTree = frequencyChars.get(0);
        int indexOfLowest = 0;
        int lowestCount = Integer.parseInt(lowestTree.getData());

        for (int i = 0, size = frequencyChars.size(); i < size; i++) {
            // Use the root node's datum for obtaining the weight of the tree.
            TreeNode<String> currentTree = frequencyChars.get(i);
            int count = Integer.parseInt(currentTree.getData());

            if (count < lowestCount) {
                indexOfLowest = i;
                lowestTree = currentTree;
                lowestCount = count;
            }
            else continue;
        }
        return frequencyChars.remove(indexOfLowest);
    }

    /**
     * A method used for creating the linked list character frequency table.
     * @param frequencyChars
     */
    public static void buildFrequencyChars(LinkedList<TreeNode> frequencyChars) {
        String currentChar;

        //Keep scanning characters in the phrase until the end of the string.
        for (int i = 0, size = phrase.length(); i < size; i++) {
        currentChar = String.valueOf(phrase.charAt(i));

            // found flag used to check if a char count is present in the forest.
            boolean found = false;

            // Check if a char count record is present for the currentChar
            // in frequencyChars.
            for (int j = 0, size2 = frequencyChars.size(); j < size2; j++)  {
                TreeNode<String> charCount = frequencyChars.get(j);

                // If a count for the currentChar is already present in frequencyChars,
                // just increment the current number stored in the count record.
                if (charCount.getLeftNode().getData().equals(currentChar)) {
                    int count = Integer.parseInt(charCount.getData());
                    charCount.setData(String.valueOf(++count));
                    found = true;
                }
            }

            // If a count for the currentChar is not present, make a new one for the list.
            if (!found) {
                TreeNode<String> newRecord = new TreeNode<>();
                newRecord.setData("1");
                TreeNode<String> charNode = new TreeNode<>();
                charNode.setData(currentChar);
                newRecord.setLeft(charNode);
                frequencyChars.push(newRecord);
            }
        }
    }
}
