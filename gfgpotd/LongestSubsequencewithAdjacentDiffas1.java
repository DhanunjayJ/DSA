class Solution {
    public int longestSubseq(int[] arr) {
        // code here
        // we could do this in o(n2) for each index we go from
        //i to j and check for the next element that had diff == 1.
        // that to the largest.
        /*
        but to do this in the o(n) we need to change the approach
        to get to know which one is present and which not we need to use
        hashmap here. 
        so when we are iteratiing we update the map with the new length
        value so if the value is already there it will remove and get updated
        with the new one. 
        now, we have value x now we check x-1 and x+1 is presnet 
        in the hashamp.
        if both present we take the max. or take what ever is present. 
        and then, updat ethe current max length and also put it int he hasMap.
        global max will be ipdated when ever the length updates.
        and return the global max at the end.
        */
        
        HashMap<Integer,Integer> numLenMap = new HashMap<>();
        
        int max = 1;
        
        for(int num : arr){
            
            int left = numLenMap.getOrDefault(num-1,0);
            int right = numLenMap.getOrDefault(num+1,0);
            
            int cMax = Math.max(left,right);
            int currentLen = cMax+1;
            
            numLenMap.put(num,currentLen);
            max = Math.max(currentLen,max);
        }
        return max;
    }
}