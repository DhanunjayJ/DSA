class Solution {
    public String minWindow(String s1, String s2) {
        // User code goes here
        /*use two pointers. one on the s1 and one on the s2 
        on the s1 we have anohter two pointers start and end. 
        incremnt the end pointer and check if that is equal to the pointer of s2. if it then increment the pointer on s2 and end too. and do this untill we find the whole stirng the s2
        we know that when we reach the end of s2. 
        once we reach it. and if end!=s1.length -> we need to put the pointer on the s2 to the starting. 
        and we need to shrink the s1 window start and end pointer. we use the start pointer to check if start == s2.charAt(0) if it is then check if the length of the stirng we alreayd have is greater than the string we found if it is the case then we update the lengght and starponinter. and start pointer moves forward intil it is eqauls to end. once it is then end will again check for the first pointer of the s2.  and chekc if there any other stirng with the smaller length. 
        */

        /* Now the final logic 

        First We just need one pointer on the first one and one poitner on s2. viz., p1 and p2
        p1 will move forward irrespective of if it is equalt to th p2. but p2 will only move forward if the char at p1 == p2. 

        When ever p2 == s2.length, then we fond the potential subsequence. 
        from that point make another pointer called start that will start for the p1 current poisition. 

        now we need to iterate on the s2 and s1 untill we reach the first character of the s2 that is equal in the s1. 

        once we found it. check for the length if is the lenght < the current min. if it is then 
        update the length and sp and endpointer. else. 

        make the p1 pointer eqauls to start+1. once it is done. repeate the same thing again. 
        */

        int p1 = 0;
        int p2 = 0;

        int n1 = s1.length();
        int n2 = s2.length();

        int ansspidx = -1;

        int minLen = Integer.MAX_VALUE;

        while(p1<n1){

            if(s1.charAt(p1)==s2.charAt(p2)){
                p2++;
            }

            // p2 == n2-1 now the p1 and p2 are at the same character

            if(p2==n2){
                // change to trigger at when p2 == n2 for the edge case of n==1.
                int back = p1;
                //move back
                p2--;
                while(p2>=0){
                    //while p2 is greater than equal to zero. we need to walk back
                    if(s1.charAt(back)==s2.charAt(p2)){
                        p2--;
                    }
                    back--;
                }
                //loop will go untill p2==-1 so we nee up udpate the back = +1
                //once p2 is equals to zero we found the min subarray that is in the same order as p2
                //update the minLen
                int currentStart = back+1;
                int currentLen = p1-currentStart+1;

                if(minLen>currentLen){
                    ansspidx = currentStart;
                    minLen = currentLen;
                }

                p1 = currentStart;
                p2 = 0;
            }
            p1++;
        }

        return ansspidx == -1 ? "" : s1.substring(ansspidx,ansspidx+minLen);
        
    }
}