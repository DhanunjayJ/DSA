import java.util.*;

class Converter {
    public String infixToPostfix(String s) {
        StringBuilder output = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') continue;

            // 1. If operand, add to output
            if (Character.isLetterOrDigit(c)) {
                output.append(c).append(" ");
            } 
            // 2. If '(', push to stack
            else if (c == '(') {
                stack.push(c);
            } 
            // 3. If ')', pop until '('
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop(); // Remove '('
            } 
            // 4. If operator
            else {
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       precedence(c) <= precedence(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" ");
        }

        return output.toString().trim();
    }

    private int precedence(char c) {
        if (c == '+' || c == '-') return 1;
        if (c == '*' || c == '/') return 2;
        if (c == '^') return 3;
        return -1;
    }
}