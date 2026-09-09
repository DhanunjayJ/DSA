class Solution {
    public long countCommas(long n) {
        long threshold = 1000;
        long count = 0;
        // we count the values all values in the range of 1000, since it has one comma.
        //then count the second comma, by multiplying it with 1000. we do this untill we 
        //reach the value n>=treshold once they are we stop.
        while(n>=threshold){
            count += (n-threshold+1);
            threshold *=1000;
        }
        return count;
    }
}