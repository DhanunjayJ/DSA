class Solution {
    public String infixToPrefix(String s) {
        // code here
        Deque<Character> opr = new ArrayDeque<>();
        Deque<String> ope = new ArrayDeque<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c==' ')continue;
            if(Character.isLetterOrDigit(c)){
                ope.push(c+"");
            }else if(c==')'){
                while(!opr.isEmpty() && opr.peek()!='('){
                    process(ope,opr);
                }
                opr.pop();
            }else if(c=='('){
                opr.push(c);
            }else{
                while(!opr.isEmpty() && opr.peek()!='(' &&
                precedence(c)<=precedence(opr.peek())){
                    if(c=='^' && opr.peek()=='^') break;
                    process(ope,opr);
                }
                opr.push(c);
            }
        }
        while(!opr.isEmpty()){
            process(ope,opr);
        }
        return ope.pop();
    }
        public static int precedence(char c){
            if(c=='^') return 3;
            else if(c=='*' || c=='/') return 2;
            else if(c=='+' || c=='-') return 1;
            else return 0;
        }
        
        public static void process(Deque<String> ope,Deque<Character>opr){
            String val2 = ope.pop();
            String val1 = ope.pop();
            Character op = opr.pop();
            ope.push(op+val1+val2);
        }
}