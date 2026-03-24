import java.util.*;

class Solution {
    public int calculate(String s) {
        Deque<Character> operators = new ArrayDeque<>();
        Deque<Integer> operands = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') continue;

            if (Character.isDigit(c)) {
                int val = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    val = val * 10 + (s.charAt(i) - '0');
                    i++;
                }
                operands.push(val);
                i--; // Step back because the for-loop increments i
            } else {
                // While top of stack has higher or equal precedence, solve it
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    process(operands, operators);
                }
                operators.push(c);
            }
        }

        // Final cleanup of remaining operators
        while (!operators.isEmpty()) {
            process(operands, operators);
        }

        return operands.pop();
    }

    private int precedence(char c) {
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return 0;
    }

    private void process(Deque<Integer> operands, Deque<Character> operators) {
        int val2 = operands.pop();
        int val1 = operands.pop();
        char op = operators.pop();
        
        switch (op) {
            case '+' -> operands.push(val1 + val2);
            case '-' -> operands.push(val1 - val2);
            case '*' -> operands.push(val1 * val2);
            case '/' -> operands.push(val1 / val2);
        }
    }
}