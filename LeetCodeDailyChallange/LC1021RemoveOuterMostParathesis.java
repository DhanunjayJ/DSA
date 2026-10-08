class Solution {
    public String removeOuterParentheses(String s) {
      int count = 0;
        StringBuilder str = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c==')') count--;
            if(count>0) str.append(c);
            if(c=='(') count++;
        }
        return str.toString();
    }
}

///
class Solution {
    public String removeOuterParentheses(String s) {
        Stack<Integer> st = new Stack<>();
        StringBuilder str = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c==')') st.pop();
            if(!st.isEmpty()) str.append(c);
            if(c=='(') st.push(i);
        }
        return str.toString();
    }
}

class Solution {
    public String removeOuterParentheses(String s) {
        Stack<Integer> st = new Stack<>();
        StringBuilder str = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('){
                st.push(i);
            }
            else if(c==')'){
                if(st.size()==1){
                    str.append(s.substring(st.peek()+1,i));
                }
                st.pop();
            }
        }
        return str.toString();
    }
}

class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder str = new StringBuilder();
        int oc = 0;
        int start = 0;
        for(int end=0;end<s.length();end++){
            char c= s.charAt(end);
            if(c=='(') oc++;
            else if(c==')') oc--;
            if(oc==0 && end-start+1>0){
                str.append(s.substring(start+1,end));
                start = end+1;
            }
        }
        return str.toString();
    }
}

class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder st = new StringBuilder();
        int n = s.length();
        int oBCount = 0;
        int startIndex = 0;
        for(int i=0;i<n;i++){
            char c = s.charAt(i);
            if(c=='(' && oBCount==0){
                startIndex=i;
                oBCount++; 
            }else if(c=='('){
                oBCount++;
            }else {
                oBCount--;
            }
            if(oBCount==0){
                st.append(s.substring(startIndex+1,i));
            }
        }
        return st.toString();
    }
}