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