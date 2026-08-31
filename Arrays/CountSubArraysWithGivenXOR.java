import java.util.HashMap;

public class CountSubArraysWithGivenXOR {
    class Solution {
    public long subarrayXor(int arr[], int k) {
        // code here
        //pxor[ep]^pxor[sp] = k
        //taking xor of k and pxor[sp] on both sides
        //pxor[ep]^k=pxor[sp];
        //known property k^k=0;
        //if pxor[ep]^k=pxor[sp], meanig here if we got
        //the same xor values as previous ones. then pxor[ep] here
        //is k.
        HashMap<Integer,Integer> prefixXorCounts = new HashMap<>();
        prefixXorCounts.put(0,1);
        int runningPrefixXor = 0;
        long count = 0;
        for(int i=0;i<arr.length;i++){
            runningPrefixXor^=arr[i];
            if(prefixXorCounts.containsKey(runningPrefixXor^k)){
                count+=prefixXorCounts.get(runningPrefixXor^k);
            }
            prefixXorCounts.put(runningPrefixXor,prefixXorCounts.getOrDefault(runningPrefixXor,0)+1);
        }
        return count;
    }
}
}
