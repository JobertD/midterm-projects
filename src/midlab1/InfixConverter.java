package midlab1;

import java.util.Scanner;
import java.util.Stack;

public class InfixConverter {
    static Scanner kbd;
    static String strIn;
    static StackNode<Token> operatorStack;

    static Token.TokenScanner tokenScanner;

    /**
     * method that creates a table and shows the solution and answer for the conversion
     *
     */
    public static void conversion() throws RuntimeException{
        kbd = new Scanner(System.in);
        operatorStack = new StackNode<>();
        String postfixOut = "";
        Stack<Token> operatorStack = new Stack<>();

        // Get the postfix expression from the user.
        System.out.print("Input -> ");
        strIn = kbd.nextLine();


        // If the given expression input contains something that shouldn't be in an arithmetic expression,
        // output the error!
        if (isNotValidExpression(strIn)) {
            System.out.println("Error: The given input cannot be converted into postfix expression.");
            System.out.println("Did you forget to remove the spaces?");
            return;
        }

        Token.TokenScanner tokenScanner = new Token.TokenScanner(strIn);

        System.out.printf("----------------------------------------------------------------\n");
        System.out.printf("|%-15s|%-25s|%-20s|\n", "Symbol", "Postfix Expression", "Operator Stack");
        System.out.printf("----------------------------------------------------------------\n");

        // Keep scanning every single token in the input
        while (tokenScanner.hasNextToken()) {
            Token currentToken = tokenScanner.nextToken();

            // If the current token is an operand,
            // concatenate it to the postfix expression.
            if (currentToken.isOperand()) postfixOut += currentToken.toString() + " ";

            // If the current token is an operator,
            // do the following:
            if (currentToken.isOperator()) {

                // If the current token is a ( token, push it into the stack.
                if (currentToken.toString() == "(") operatorStack.push(currentToken);

                // If the current token is a ) token, keep popping elements from the stack
                // and concatenate them to the postfix expression; keep doing this until
                // the ) token is found or if the stack is empty.
                else if (currentToken.toString() == ")") {
                    Token opToken;
                    while (!operatorStack.isEmpty() && (opToken = operatorStack.pop()).toString() != "(" ) {
                        postfixOut += opToken.toString() + " ";
                    }
                }

                // If the current token is an arithmetic operator, keep concatenating the top elements
                // of the stack so long as it is not empty and the top element has higher precedence
                // over the current token. After that, push the current token into the stack.
                else {
                    while (!operatorStack.isEmpty() && precedence(operatorStack.peek(), currentToken)) {
                        postfixOut += operatorStack.pop().toString() + " ";
                        System.out.printf("|%-15s|%-25s|%-20s|\n", currentToken, postfixOut, operatorStack.toString());
                        System.out.printf("----------------------------------------------------------------\n");
                    }
                    operatorStack.push(currentToken);
                }
            }

            System.out.printf("|%-15s|%-25s|%-20s|\n", currentToken, postfixOut, operatorStack.toString());
            System.out.printf("----------------------------------------------------------------\n");


        }

        // Concatenate whatever remaining operator is in the stack.
        while (!operatorStack.isEmpty()) {
            Token currentToken = operatorStack.pop();
            if (currentToken.toString() == "("); // do nothing.
            else postfixOut += currentToken.toString() + " ";
            System.out.printf("|%-15s|%-25s|%-20s|\n", "", postfixOut, operatorStack.toString());
            System.out.printf("----------------------------------------------------------------\n");
        }

        System.out.printf("\nPostfix: %s\n", postfixOut);

    }

    /**
     * Returns true if the first token argument is considered to have higher precedence
     * over the second token.
     * @param token1
     * @param token2
     * @return
     */
    public static boolean precedence(Token token1, Token token2) {
            // Same operators with same precedence should be considered true
            if (token1.toString() == token2.toString()) return true;

            // Different operators with same precedence should be considered true.
            else if ( (token1.toString() == "+" && token2.toString() == "-") ||
                    (token1.toString() == "-" && token2.toString() == "+") ||
                    (token1.toString() == "*" && token2.toString() == "/") ||
                    (token1.toString() == "/" && token2.toString() == "*")) return true;

            // If either of the token inputs are the ( operator, consider it as
            // false; otherwise, the ( operator may end up being concatenated
            // to the postfix string, which is incorrect!
            else if (token1.toString() == "(" || token2.toString() == "(") return false;
            else if (token1.toString() == "^" && token2.toString() != "^") return true;

            else return ( (token1.toString() == "*" || token1.toString() == "/" ) &&
                        (token2.toString() == "+" || token2.toString() == "-"));

    }

    /**
     * Checks if the given string expression is not valid by checking for any characters.
     * that is not either a number, letter variable, or any of the arithmetic operators.
     * @param expression
     * @return
     */
    public static boolean isNotValidExpression(String expression) {
        return strIn.matches("(.*)?[^0123456789().+*/^A-Za-z-](.*)?");
    }
}
