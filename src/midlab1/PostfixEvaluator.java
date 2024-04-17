package midlab1;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class PostfixEvaluator {

    private static Scanner kbd;
    private static String strIn;
    private static StackNode<Token> operandStack;
    private static Token.TokenScanner tokenScanner;

    /**
     * The main function used for running the PostfixEvaluator class.
     */
    public static void run() {
        kbd = new Scanner(System.in);
        operandStack = new StackNode<>();
        double result = 0;

        System.out.print("Input -> ");
        strIn = kbd.nextLine();
        if (isNotPostfixExpression(strIn)) {
            System.out.println("Error: cannot evaluate the given expression.");
            System.out.println("Did you forget to add spaces?");
            return;
        }

        try {
            result = evaluate();
        } catch (Exception e) {
            System.out.println("Error: cannot fully evaluate the given expression.");
            System.out.println("Did you double-check your postfix expression?");
            return;
        }

        System.out.printf("Result = %.2f\n", result);

    }

    /**
     * This is where the postfix expression itself is evaluated for its computed value.
     * @return
     */
    public static double evaluate() {
        tokenScanner = new Token.TokenScanner(strIn);
        Token currentToken;
        String lineStr = "";
        for (int x = 0; x < 105; x++) lineStr += "-";

        // Print out the table header.
        System.out.println(lineStr);
        System.out.printf("|%-15s|%-25s|%-20s|%-20s|%-20s|\n", "Symbol", "Operand 1", "Operand 2", "Value", "Operand Stack");
        System.out.println(lineStr);

        // Scan each token that is present in the expression.
        while (tokenScanner.hasNextToken()) {
            currentToken = tokenScanner.nextToken();

            // If the current token is an operand, push it into the operand stack.
            if (currentToken.isOperand()) {
                operandStack.push(currentToken);
                System.out.printf("|%-15s|%-25s|%-20s|%-20s|%-20s|\n",
                        currentToken.toString(), "", "", "", operandStack.toString());
                System.out.println(lineStr);

            }
            // If the current token is an operator...
            else if (currentToken.isOperator()) {

                // Pop the top two elements from the operand stack.
                double operand2 = Double.parseDouble(operandStack.pop().toString());
                double operand1 = Double.parseDouble(operandStack.pop().toString());

                // Push the result of the operation with the current operator
                // and two operands to the operand stack.
                Token result = evaluateHelper(operand1, operand2, currentToken);
                operandStack.push(result);

                System.out.printf("|%-15s|%-25s|%-20s|%-20s|%-20s|\n",
                        currentToken.toString(), operand1, operand2, result.toString(), operandStack.toString());
                System.out.println(lineStr);
            }

        }

        // If there's not one remaining element in the operand stack, that means the postfix expression
        // was not valid since there should only be one element remaining.
        if (operandStack.size() != 1) {
            throw new RuntimeException();
        }
        else return Double.parseDouble(operandStack.pop().toString());
    }

    /**
     * A helper function for evaluating the two operands with the given operator token.
     * @param operand1
     * @param operand2
     * @param operator
     * @return
     */
    public static Token evaluateHelper(Double operand1, Double operand2, Token operator) {
        if (operator.toString() == "+") return new Token(String.valueOf(operand1 + operand2));
        else if (operator.toString() == "-") return new Token(String.valueOf(operand1 - operand2));
        else if (operator.toString() == "*") return new Token(String.valueOf(operand1 * operand2));
        else if (operator.toString() == "/") return new Token(String.valueOf(operand1 / operand2));
        else return new Token(String.valueOf(Math.pow(operand1, operand2)));
    }

    /**
     * Checks if the given postfix expression is not valid.
     * @return
     */
    public static boolean isNotPostfixExpression(String strIn) {
        return strIn.matches("(.*)?[^\\ 0123456789().+*/^A-Za-z-](.*)?");
    }

}
