/*

Vivek has taught the question on printing all the valid balanced parentheses sequences given N pairs of parentheses in the webinar on recursion.

He wants to test his students, so, he asks them a similar related question. He asks them to print all the valid balanced bracket sequence given N pairs of parantheses () and M pairs of braces {}.

Can you solve this variation of his problem?

Remember a balanced parentheses has following properties:

For any type of bracket (parentheses/brace), the number of opening and closing brackets should be equal. The following are invalid: (){}} ({({})}
For any type of bracket, and any index of the string, the number of opening bracket before that index should be greater or equal to the number of closing brackets. The following are invalid: ()}{ ({}))(
For any two matching pairs of different types of brackets, it should not happen that one type of bracket has opening before the opening of other bracket but has closing after the second type of closing. This is invalid: {(}) but these are valid: {()} ({}).
Input Format

The first line of input consists of two integers, N and M, which denote the number of parentheses and the number of braces pair.

Constraints

1 <= N + M <= 10

Output Format

Output all the valid balanced bracket sequences for given number of parantheses and braces pairs, one in each newline.

Sample Input 0

1 2
Sample Output 0

(){}{}
{}(){}
{}{}()
(){{}}
{{}}()
{}{()}
{}({})
{()}{}
({}){}
(){{}}
{{}}()
{{()}}
{({})}
({{}})
({}{})
{(){}}
{{}()}


*/


import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int roundCount =  sc.nextInt();
        int flowerCount = sc.nextInt();
        List<String> ans = new ArrayList<>();
        //using stack for the order of the values.
        helper(roundCount,flowerCount,new StringBuilder(),0,0,ans,new Stack<>());
        for(String st: ans){
            System.out.println(st);
        }
    }
    public static void helper(int rc,int fc,StringBuilder st,int rob,int fob,List<String> ans,Stack<Character> stack){
        
        if(rob==rc && fc==fob && stack.size()==0){
            ans.add(st.toString());
            return;
        }
        
        if(rob<rc){
            st.append('(');
            stack.push('(');
            helper(rc,fc,st,rob+1,fob,ans,stack);
            stack.pop();
            st.deleteCharAt(st.length()-1);
        }
        
        if(fob<fc){
            st.append('{');
            stack.push('{');
            helper(rc,fc,st,rob,fob+1,ans,stack);
            stack.pop();
            st.deleteCharAt(st.length()-1);
        }
        
        //only cloase when the top of the stack is the '('
        if(!stack.isEmpty() && stack.peek()=='('){
            // if(st.charAt(st.length()-1)!='{'){
                st.append(')');
                char popped = stack.pop();
                helper(rc,fc,st,rob,fob,ans,stack);
                stack.push(popped);
                st.deleteCharAt(st.length()-1);
            // }
        }
        
        if(!stack.isEmpty() && stack.peek()=='{'){
            // if(st.charAt(st.length()-1)!='('){
                st.append('}');
                char popped = stack.pop();
                helper(rc,fc,st,rob,fob,ans,stack);
                stack.push(popped);
                st.deleteCharAt(st.length()-1);
            // }
        }
    }
}