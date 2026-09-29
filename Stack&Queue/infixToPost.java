import java.util.*;

class Solution {
    // Function to convert an infix expression to a postfix expression.
    public static String infixToPostfix(String s) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // 1. If it's an operand, add it to the result
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } 
            // 2. If '(', push to stack
            else if (c == '(') {
                stack.push(c);
            } 
            // 3. If ')', pop until '(' is found
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Remove '(' from stack
            } 
            // 4. If it's an operator
            else {
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       precedence(c) <= precedence(stack.peek())) {
                    
                    // Special case for '^' (Right-to-Left associativity)
                    // If both are '^', we don't pop the one on stack
                    if (c == '^' && stack.peek() == '^') break;
                    
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Pop all remaining operators from the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
}