class Solution {
    public int totalFruit(int[] fruits) {
        //your code goes here
        //we neeed to find a longest subarray wth two distinct vlaues.
        int start = 0;
        int end = 0;
        HashMap<Integer,Integer> hm = new HashMap<>();
        int maxlen = 0;
        for(end = 0;end<fruits.length;end++){
            hm.put(fruits[end],hm.getOrDefault(fruits[end],0)+1);
            //when ever the window becomes invalid make it valid.
            while(hm.size()>2){
                int sValue = fruits[start];
                hm.put(sValue,hm.get(sValue)-1);
                if(hm.get(sValue)==0) hm.remove(sValue);
                start++;
            }
            maxlen = Math.max(end-start+1,maxlen);
        }
        return maxlen;
    }
}


// Using Non Shrinking Window and Freq Map array
class Solution {
    public int totalFruit(int[] fruits) {
    //    HashMap<Integer,Integer> hm = new HashMap<>();
       int [] freqMap = new int[1_000_01];
       int start = 0,end;
       int n = fruits.length;
       int unique = 0;
       for(end=0;end<n;end++){
        // hm.put(fruits[end],hm.getOrDefault(fruits[end],0)+1);
        if(freqMap[fruits[end]]==0) unique ++;
        freqMap[fruits[end]]++;
        if(unique>2){
            // hm.put(fruits[start],hm.get(fruits[start])-1);
            freqMap[fruits[start]]--;
            // if(hm.get(fruits[start])==0) hm.remove(fruits[start]);
            if(freqMap[fruits[start]]==0) unique--;
            start++;
        }
       }
       return end-start;
    }
}