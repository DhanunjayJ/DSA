class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        int number = 0;
        int sign = 1; // 1 means positive, -1 means negative

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number = 10 * number + (c - '0');
            } else if (c == '+') {
                result += sign * number;
                number = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {
                // Push the result and the sign before the parenthesis
                stack.push(result);
                stack.push(sign);
                // Reset for the inside of the parenthesis
                sign = 1;   
                result = 0;
            } else if (c == ')') {
                result += sign * number;  
                number = 0;
                result *= stack.pop();    // This was the sign before '('
                result += stack.pop();    // This was the result before '('
            }
        }
        if (number != 0) result += sign * number;
        return result;
    }
}