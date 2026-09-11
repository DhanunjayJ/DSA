//Apraoch 1 checking all 3 digits nums and maintainging count of digits
//and check if they are valid

class Solution {
    public int totalNumbers(int[] digits) {
        int count = 0;
        int [] dCount = new int[10];
        for(int digit: digits){
            dCount[digit]++;
        }
        for(int i=100;i<=999;i+=2){
            int fN = i%10;
            int sN = (i/10)%10;
            int tN = (i/100);
            dCount[fN]--;dCount[sN]--;dCount[tN]--;
            if(dCount[fN]>=0 && dCount[sN]>=0 && dCount[tN]>=0){
                count++;
            }
            dCount[fN]++;dCount[sN]++;dCount[tN]++;
        }
        return count;
    }

}

///appraoch 2 uing backtracking
class Solution {
    public int totalNumbers(int[] digits) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        //length,uniqueNumbers,digits,currNum,visitedArray
        helper(0,uniqueNumbers,digits,0,new boolean[digits.length]);
        return uniqueNumbers.size();
    }
    public void helper(int len,Set<Integer> uq ,int [] digits,int currNum,boolean [] vis){
        if(len==3){
            if(currNum>=100 && currNum%2==0){
                uq.add(currNum);
            }
            return;
        }
        for(int i=0;i<digits.length;i++){
            if(vis[i]) continue;
            if(len==0 && digits[i]==0) continue;
            vis[i] = true;
            helper(len+1,uq,digits,currNum*10+digits[i],vis);
            vis[i] = false;
        }
    }
}

//appraoch 3
//bruteforce using 3 loops.
class Solution {
    public int totalNumbers(int[] digits) {
        HashSet<Integer> unique = new HashSet<>();
        int n = digits.length;
        for(int i=0;i<n;i++){
            if(digits[i]==0) continue;
            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    if(digits[k]%2!=0) continue;
                    if(i!=j && j!=k && k!=i){
                        int num = digits[i]*100 + digits[j]*10+digits[k];
                        unique.add(num);
                    }
                }
            }
        }
        return unique.size();
    }
}