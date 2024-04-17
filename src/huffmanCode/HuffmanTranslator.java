package huffmanCode;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class used for encoding and decoding text to binary and vice versa.
 */
public class HuffmanTranslator {

    /**
     * Method used for translating strings into binary huffman encodings.
     * @param huffmanTable
     */
    public static void encode(LinkedList<Cons> huffmanTable) {

        // Get the phrase from the user.
        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter phrase to be encoded:");
        String phrase = kbd.nextLine();
        String binaryEncoding = "";

        try {
            for (int i = 0, size = phrase.length(); i < size; i++) {
                String currentChar = String.valueOf(phrase.charAt(i));
                binaryEncoding += referBinaryEquivalent(currentChar, huffmanTable);
            }

        } catch (Exception e) {
            // If an unknown character that was not included from the huffman tree was sent as input,
            // print the error.
            System.out.println("Error: unknown characters excluded from huffman table are present.");
            return;
        }

        System.out.printf("Binary Equivalent:\n%s\n", binaryEncoding);
        HuffmanMain.pressEnter();
        return;
    }

    /**
     * Decodes a binary pattern into the text equivalent.
     * @param huffmanTable
     */
    public static void decode(LinkedList<Cons> huffmanTable) {

        // Get binary pattern from the user.
        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter bit pattern to be decoded:");
        String pattern = kbd.nextLine();
        String textDecoding = "";

        // Check first if there are any miscellaneous characters in the bit pattern.
        if (!isBinary(pattern)) {
            System.out.println("Error: your input must only be a binary bit pattern.");
            HuffmanMain.pressEnter();
            return;
        }

        else {
            try {
                String currentBitPattern = "";

                for (int bitPointer1 = 0, bitPointer2 = bitPointer1, size = pattern.length();
                     bitPointer1 < size; bitPointer2 = bitPointer1, currentBitPattern = "") {

                    currentBitPattern += String.valueOf(pattern.charAt(bitPointer1));
                    String returnedText = referTextEquivalent(currentBitPattern, huffmanTable);

                    // If a huffman code was found from the given segment of bit pattern, add its
                    // text equivalent to the text decoding.
                    if (!returnedText.equals("")) {
                        textDecoding += returnedText;
                        // Set the proper value for bitPointer1
                        bitPointer1 = bitPointer2 == bitPointer1 ? bitPointer1 + 1 : bitPointer2 + 1;
                        continue;
                    }

                    // Otherwise...
                    else {

                        // Keep adding to the currentBitPattern until a possible match can be made.
                        while (returnedText.equals("")) {
                            bitPointer2++;
                            currentBitPattern += String.valueOf(pattern.charAt(bitPointer2));
                            returnedText = referTextEquivalent(currentBitPattern, huffmanTable);
                        }
                        textDecoding += returnedText;
                        // Set the proper value for bitPointer1
                        bitPointer1 = bitPointer2 == bitPointer1 ? bitPointer1 + 1 : bitPointer2 + 1;
                        continue;

                    }

                }

                // When the for loop is done, print the text equivalent.
                System.out.println("Text:");
                System.out.println(textDecoding);
                HuffmanMain.pressEnter();
                return;

            } catch (Exception e) {
                // An exception happens if bitPointer2 from the above for loop goes past the length of the
                // bit pattern; this happens if the user entered a bit pattern that cannot be translated
                // into its text equivalent.
                System.out.println("Error: unknown bit pattern cannot be decoded into text.");
                HuffmanMain.pressEnter();
                return;
            }
        }
    }

    /**
     * Checks if a given string input is a proper binary bit pattern.
     * @param bitPattern
     * @return
     */
    public static boolean isBinary(String bitPattern) {
        return bitPattern.matches("^[01]+$");
    }

    /**
     * Method used for referring to the huffman equivalent of given string characters.
     * @param givenChar
     * @param huffmanTable
     * @return
     * @throws RuntimeException
     */
    public static String referBinaryEquivalent(String givenChar, LinkedList<Cons> huffmanTable)
     throws RuntimeException {

        for (int i = 0, size = huffmanTable.size(); i < size; i++) {
            Cons<String, String> currentCons = huffmanTable.get(i);

            if (givenChar.equals(currentCons.car())) return currentCons.cdr();
            else continue;
        }

        throw new RuntimeException("Error: could not refer to the binary equivalent.");
    }

    /**
     * Method used for referring to the text equivalent of the huffman code; it returns
     * an empty string if it could not find a match.
     * @param bitPattern
     * @param huffmanTable
     * @return
     */
    public static String referTextEquivalent(String bitPattern, LinkedList<Cons> huffmanTable) {

        for (int i = 0, size = huffmanTable.size(); i < size; i++) {
            Cons<String, String> currentCons = huffmanTable.get(i);

            if (bitPattern.equals(currentCons.cdr())) return currentCons.car();
            else continue;
        }

        return "";
    }
}
