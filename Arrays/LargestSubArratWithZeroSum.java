import java.util.HashMap;

public class LargestSubArratWithZeroSum {
    class Solution {
    int maxLength(int arr[]) {
        // code here
        /*
        we need to find i...j such that j-i+1 is maximum.
        that sum == 0;
        psum[j]-psum[i-1] = 0;
        psum[j] = psum[i-1]
        so  we need to in the psum[i-1] == psum[j]
        wehn we find it we just get the len and update len
        */
        HashMap<Integer,Integer> psumIdx = new HashMap<>();
        psumIdx.put(0,-1);
        int psum = 0;
        int len = 0;
        for(int i=0;i<arr.length;i++){
            int num = arr[i];
            psum+=num;
            if(psumIdx.containsKey(psum)){
                len = Math.max(len,i-psumIdx.get(psum));
            }
            //only update it if the psum is not there
            // if already there we don't do anything
            //becase we want the longest subarray.
            psumIdx.putIfAbsent(psum,i);
        }
        return len;
    }
}
}
