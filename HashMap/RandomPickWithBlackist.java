class Solution {
    int valid;
    HashMap<Integer,Integer> inValidToValid;
    Random r;
    public Solution(int n, int[] blacklist) {
        inValidToValid = new HashMap<>();
        valid = n - blacklist.length;
        for(int black:blacklist){
            inValidToValid.put(black,-1);
        }
        for(int i=0;i<blacklist.length;i++){
            if(blacklist[i] < valid){
                //the input is valid number is only untill n-1 not n.
                while(inValidToValid.containsKey(n-1)){
                    n--;
                }
                inValidToValid.put(blacklist[i],n-1);
                n--;
            }
        }
    }
    
    public int pick() {
        r = new Random();
        int val = r.nextInt(valid);
        if(inValidToValid.containsKey(val)){
            return inValidToValid.get(val);
        }
        return val;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(n, blacklist);
 * int param_1 = obj.pick();
 */