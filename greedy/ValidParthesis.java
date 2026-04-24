class Solution {
    public boolean checkValidString(String s) {
        /*
        First we have to check if the parahtesis valid or not.
        so, first to be vlaid we need to have for every ) there should be correspnding ( braket. and * can act as anyhting. baed on the condition.
        we could try out all the possible ways for the * to be like ( , *, and ) and check if  it is giving the valid parathesis.
        so if we just use one stack it will be diffcult to track the ( and *. becaue we don't know which one to pop ( or * when we get ). so to avoid confusion we take two stacks. we push the (  and * 

        First we pop the ( when we get ) and if there is not ( then we pop * and if there are stilll remaining * after ) then we have to check if the stars indexes are after ( indexes. then we go else return false;*/

        Deque<Integer> open = new ArrayDeque<>();
        Deque<Integer> stars = new ArrayDeque<>();

        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('){
                open.push(i);
            }else if(c=='*'){
                stars.push(i);
            }else{
                if(!open.isEmpty()){
                    open.pop();
                }else if(!stars.isEmpty()){
                    stars.pop();
                }else{
                    return false;
                }
            }
        }

        while(!open.isEmpty() && !stars.isEmpty()){
            if(open.peek()>stars.peek()){
                return false;
            }
            open.pop();
            stars.pop();
        }


        return open.isEmpty();
    }
}


// optimized approach in O(1) space

class Solution {
    public boolean checkValidString(String s) {
        /*
        the Maintaining Possibilty Concepts
        Since the * can be (,) or "". we try to maintina the range of the 
        the open brackets possibilties. 
        low - how many open brackets that are less possbile
        high - how many open brakcers that we can keep max at the point of time.
        if low is <0 menaing that are have condieign the too many * as ). we cna treet them as
        empty ones. and 
        when high <0 this means that even after consideirng all the * as (.ew can't satisify 
        the number of closing brackets. because of that we got -ve.
        at any points we should have eqaul number of () not negative. so we just return false.
        if low is still posibtive after the string. which means that even after 
        treating every possible * as ) we can't satisfy the current ( brackets so return fasle;
         */

        int low = 0;
        int high = 0;
        for(int i=0;i<s.length();i++){
            char c= s.charAt(i);
            if(c=='('){
                low++;
                high++;
            }else if(c==')'){
                low--;
                high--;
            }else{ //c=='*'
                low--; //treating it as )
                high ++; //treating it as (
            }
            if(high<0) return false;
            if(low<0) low = 0; // this means we have more than enough * we can treat them as empty
            // if more )) then high becomes negative.
        }
        //check is is possible to reach the balance. after treating all the * as ).
        return low==0;
    }
}