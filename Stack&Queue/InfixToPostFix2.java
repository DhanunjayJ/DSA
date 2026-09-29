import java.util.*;

class Solution {
    public static String infixToPostfix(String s) {
        Deque<String> ope = new ArrayDeque<>();
        // Changed to Character for easier comparison and better performance
        Deque<Character> opr = new ArrayDeque<>();
        
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(Character.isLetterOrDigit(c)){
                ope.push(c + "");
            } else if(c == '('){
                opr.push(c);
            } else if(c == ')'){
                // Use == for characters
                while(!opr.isEmpty() && opr.peek() != '('){
                    process(ope, opr);
                }
                opr.pop();
            } else {
                while(!opr.isEmpty() && opr.peek() != '(' &&
                       precedence(c) <= precedence(opr.peek())){
                    
                    // Correct check for right-associativity
                    if(c == '^' && opr.peek() == '^') break;
                    process(ope, opr);
                }
                opr.push(c);
            }
        }
        while(!opr.isEmpty()){
            process(ope, opr);
        }
        return ope.pop();
    }
    
    // Using char here is much simpler than String
    public static int precedence(char c){
        if(c == '^') return 3;
        else if(c == '*' || c == '/') return 2;
        else if(c == '+' || c == '-') return 1;
        else return 0;
    }
    
    public static void process(Deque<String> ope, Deque<Character> opr){
        char op = opr.pop();
        String val2 = ope.pop();
        String val1 = ope.pop();
        // Postfix order: operand1 + operand2 + operator
        ope.push(val1 + val2 + op);
    }
}