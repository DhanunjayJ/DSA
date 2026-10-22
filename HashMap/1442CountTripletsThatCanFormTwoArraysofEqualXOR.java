//O(n)^2 appraoch

class Solution {
    public int countTriplets(int[] arr) {
        int n = arr.length;
        int count = 0;
        for(int i=0;i<n;i++){
            int currentXor = 0;
            for(int k=i;k<n;k++){
                currentXor ^= arr[k];
                //if the subarray from i to k gives the 
                if(currentXor==0 && k>i){
                    count += (k-i);
                }
            }
        }
        return count;
    }
}

//O(n)
class Solution {
    public int countTriplets(int[] arr) {

        int n = arr.length;
        
        HashMap<Integer,Integer> countMap = new HashMap<>();
        HashMap<Integer,Integer> sumMap = new HashMap<>();

        int prefixXor = 0;
        int totalTriplets = 0;

        countMap.put(0,1);
        sumMap.put(0,0);

        for(int k=0;k<n;k++){
            prefixXor ^= arr[k];

            if(countMap.containsKey(prefixXor)){
                int count = countMap.get(prefixXor);
                int indexSum = sumMap.get(prefixXor);
                //if count is 3
                //Total = (k - i1) + (k - i2) + (k - i3)
                totalTriplets += (count*k) - indexSum;
            }

            countMap.put(prefixXor,countMap.getOrDefault(prefixXor,0)+1);
            //for one based index we add k+1
            sumMap.put(prefixXor,sumMap.getOrDefault(prefixXor,0)+k+1);
        }

        return totalTriplets;
    }
}