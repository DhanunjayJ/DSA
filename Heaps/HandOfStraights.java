// O(nlogn) approach

class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        /* 
        Since we need to rearange int he group of consequtive numbers , those need to be sorted
        so we sort the whole array. and for each start point check if there is x+1 value or not
        then the next x+2 untill x+n=gouptize, then found we foind out then that is the size.
        if there is not x+1 then we can't do it. to get if the x+1 value is presnet of not 
        int O(1) we use HashMap
        Insetead of sorting and puuting in hash map we can dirclty use Treemap
        */

        TreeMap <Integer,Integer> eleToFreq = new TreeMap<>();
        for(int card:hand){
            eleToFreq.put(card,eleToFreq.getOrDefault(card,0)+1);
        }

        for(int val:eleToFreq.keySet()){
            int start = val;
            // do the grouoing until all the ducplicates of the number is gone. 
            while(eleToFreq.get(start)>0){
            for(int i=0;i<groupSize;i++){
                int nextVal = start+i;
                if(eleToFreq.getOrDefault(nextVal,0)>0){
                    eleToFreq.put(nextVal,eleToFreq.get(nextVal)-1);
                }else{
                    return false;
                }
            }
            }
        }
        return true;
    }
}



//o(n)

class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        /*
        First put all the elements on to the hashmap.

        for each element on the arrray. go to the smallest element possible. by 
        doing -1 untill we have that smallest value in the hasmap. 

        then from there check for the group size of length given size. 

        if there is one then we don't need to do anyting if not we return false.

         */
        HashMap<Integer,Integer> hm = new HashMap<>();
        
        for(int h:hand){
            hm.put(h,hm.getOrDefault(h,0)+1);
        }

        // for( int i = 0; i < hand.length ; i++){
            int i = 0;
            while(i<hand.length){
            // if the current element is already processed or groped we don't 
            //consider it we just move to the next element. 
            if(hm.get(hand[i])<1) { i++ ; continue;}

            //go to the smallest element poosible untill the count >0 is there
            int curr = hand[i];

            while(hm.getOrDefault(curr-1,0)>0){
                curr = curr-1;
            }

            //from the current val check if it it there a group size of given groupsize
            int count = hm.get(curr);

            for(int j = 0; j < groupSize ; j++){

                int nextVal = curr+j; 

                if(hm.getOrDefault(nextVal,0)<count){

                    return false;

                }

                hm.put(nextVal,hm.get(nextVal)-count);

            }

            if(hm.get(hand[i])<1){
                i++;
            }
        }

        return true;
    }
}