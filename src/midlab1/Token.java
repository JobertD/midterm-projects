package midlab1;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Token {


    /**
     * An inner class used for scanning tokens from a given string.
     * This class is declared static, which means it can be instantiated
     * even without having to declare the outer class first.
     */
    public static class TokenScanner {

        private Scanner tokenScanner;
        public TokenScanner(String inputStr) {

            // The token scanner class actually requires its tokens to be separated by spaces in order for it to
            // work properly. This line replaces each regex match with itself while adding spaces before and after
            // before passing it to the scanner object.
            String myString = inputStr.replaceAll("([A-Z]{1}|[a-z]{1}|[-+/*^]{1}|\\({1}|\\){1}|" +
                            "\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+){1}", "\\ $1\\ ");

            tokenScanner = new Scanner(myString);
        }


        /**
         * Method used for getting the next token unless if there are no more tokens.
         * @return
         * @throws RuntimeException
         */
        public Token nextToken() throws RuntimeException {
            try {
                return new Token(tokenScanner.next(Pattern.compile(
                        "([A-Z]{1}|[a-z]{1}|[-+/*^]{1}|\\({1}|\\){1}|" +
                                "\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+){1}"
                )));
            } catch (Exception e) {
                throw new RuntimeException("Cannot parse anymore tokens");
            }
        }

        /**
         * Checks if there are still anymore next tokens in the current string.
         * @return
         */
        public boolean hasNextToken() {
            return tokenScanner.hasNext(Pattern.compile("[+*/^-]|\\(|\\)"))
                    || tokenScanner.hasNextDouble() || tokenScanner.hasNext(Pattern.compile("[A-Z]{1}|[a-z]{1}"));
        }
    }


    private String token;
    private boolean isOperand = false;
    /**
     * A constructor for the token class.
     * @param tokenStr
     * @throws RuntimeException
     */
    public Token(String tokenStr) throws RuntimeException {
        switch (tokenStr) {
            case "+":
                token = "+";
                break;
            case "-":
                token = "-";
                break;
            case "*":
                token = "*";
                break;
            case "/":
                token = "/";
                break;
            case "^":
                token = "^";
                break;
            case "(":
                token = "(";
                break;
            case ")":
                token = ")";
                break;
            default:
                tryAsNum(tokenStr);
        }
    }

    /**
     * A helper class for the constructor which tries to check if the given string
     * is a parseable number, otherwise it throws an exception.
     * @param tokenStr
     * @throws RuntimeException
     */
    public void tryAsNum (String tokenStr) throws RuntimeException {

        try {
            try {
                Double.parseDouble(tokenStr);
                this.token = tokenStr;
                isOperand = true;
            } catch (NumberFormatException e ){
                this.token = String.valueOf(tokenStr.charAt(0));
                isOperand = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert given string into appropriate token.");
        }
    }

    /**
     * Checks if the token is an operand.
     * @return
     */
    public boolean isOperand() {
        return isOperand;
    }

    /**
     * Checks if the token is an operator (this includes the arithmetic operators as well as the
     * exponentiation and parentheses symbols).
     * @return
     */
    public boolean isOperator() {
        return !isOperand;
    }

    @Override
    public String toString() {
        return token;
    }

}
